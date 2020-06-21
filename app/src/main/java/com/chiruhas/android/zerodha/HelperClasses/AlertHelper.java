package com.chiruhas.android.zerodha.HelperClasses;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.R;

public class AlertHelper {
    Context context;
    Dialog myDialog;

    float cnc1, mis1;

    public AlertHelper(Context context) {
        this.context = context;
        myDialog = new Dialog(context);
        cnc1 = 0.0f;
        mis1 = 0.0f;
    }

    public void loadEquityPopup(GodModel item, double _eq) {

        //final View view = LayoutInflater.from(context).inflate(R.layout.equitycalculate, null);

        myDialog.setContentView(R.layout.equitycalculate);


        TextView scrip;
        final TextView mis_mux, nrml;
        Button cal;
        final EditText price, amt;

        scrip = myDialog.findViewById(R.id.scrip);
        mis_mux = myDialog.findViewById(R.id.mis_mux);
        nrml = myDialog.findViewById(R.id.mis_mux2);
        cal = myDialog.findViewById(R.id.calculate);
        price = myDialog.findViewById(R.id.price);
        amt = myDialog.findViewById(R.id.amount);

        if (_eq != 0)
            amt.setText(_eq + "");

        scrip.setText(item.getTradingsymbol());
        mis_mux.setText("MIS : " + item.getMis_multiplier() + "X");
        nrml.setText("CNC : " + item.getNrml_multiplier() + "X");

        mis1 = item.getMis_multiplier();
        cnc1 = item.getNrml_multiplier();

        cal.setOnClickListener(v -> {

            if (price.getText().toString().isEmpty() || amt.getText().toString().isEmpty())
                Toast.makeText(context, "Fields Can't be empty", Toast.LENGTH_SHORT).show();

            else {
                TextView misqty = myDialog.findViewById(R.id.misqty);
                TextView cncqty = myDialog.findViewById(R.id.cncqty);
                double am = 0, prc = 0;
                if (!amt.getText().toString().startsWith(".") && !price.getText().toString().startsWith(".")) {
                    am = Double.parseDouble(amt.getText().toString());

                    prc = Double.parseDouble(price.getText().toString());
                    misqty.setText(Math.floor(((am * mis1) / prc)) + "");
                    cncqty.setText(Math.floor(((am * cnc1) / prc)) + "");
                } else
                    Toast.makeText(context, "Please enter proper values..", Toast.LENGTH_LONG).show();


            }
        });

        // closing

        TextView close = myDialog.findViewById(R.id.close);
        close.setOnClickListener(v -> myDialog.dismiss());


        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //myDialog.setCancelable(false);

        myDialog.show();
    }

    public void nrml_bo(String name, String margin, String avalue, String leverage) {

        myDialog.setContentView(R.layout.bo_nrml_popup);


        TextView sc = myDialog.findViewById(R.id.scrip);
        sc.setText(name);
        TextView mar = myDialog.findViewById(R.id.margin);
        mar.setText(" RS " + Math.round(Double.parseDouble(margin) * 100.0) / 100.0 + " /-");
        TextView av = myDialog.findViewById(R.id.values);
        av.setText(" RS " + Math.round(Double.parseDouble(avalue) * 100.0) / 100.0 + " /-");
        TextView lev = myDialog.findViewById(R.id.leverage);
        if (leverage.equals("Infinity"))
            lev.setText("Infinity");
        else
            lev.setText(Math.round(Double.parseDouble(leverage) * 100.0) / 100.0 + " X");
        TextView close = myDialog.findViewById(R.id.close);

        close.setOnClickListener(view -> myDialog.dismiss());

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myDialog.setCancelable(false);
        myDialog.show();
    }


    public void loadCommodityPopUp(final Commodity commodity, double _co) {

        myDialog.setContentView(R.layout.equitycalculate);


        TextView scrip;
        final TextView mis_mux, nrml;
        Button cal;
        final EditText price, amt;


        scrip = myDialog.findViewById(R.id.scrip);
        mis_mux = myDialog.findViewById(R.id.mis_mux);
        nrml = myDialog.findViewById(R.id.mis_mux2);
        cal = myDialog.findViewById(R.id.calculate);
        price = myDialog.findViewById(R.id.price);
        amt = myDialog.findViewById(R.id.amount);
        if (_co != 0)
            amt.setText(_co + "");

        price.setText(commodity.getPrice());
        scrip.setText(commodity.getScrip());
        nrml.setText("CNC : " + commodity.getNrml());


        if (commodity.getMis().equals("N/A")) {
            mis_mux.setText("MIS : " + Integer.parseInt(commodity.getNrml()) / 2);
            commodity.setMis((Integer.parseInt(commodity.getNrml()) / 2) + "");
        } else {
            mis_mux.setText("MIS : " + commodity.getMis());
        }


        cal.setOnClickListener(v -> {

            if (price.getText().toString().isEmpty() || amt.getText().toString().isEmpty())
                Toast.makeText(context, "Fields Can't be empty", Toast.LENGTH_SHORT).show();

            else {
                TextView misqty = myDialog.findViewById(R.id.misqty);
                TextView cncqty = myDialog.findViewById(R.id.cncqty);

                double am = 0, prc = 0;
                if (!amt.getText().toString().startsWith(".") && !price.getText().toString().startsWith(".")) {
                    am = Double.parseDouble(amt.getText().toString());

                    prc = Double.parseDouble(price.getText().toString());
                    int res[] = changeParams(mis_mux, nrml, prc, commodity);
                    misqty.setText(Math.floor(((am / res[0]))) + "");
                    cncqty.setText(Math.floor(((am / res[1]))) + "");
                } else
                    Toast.makeText(context, "Please enter proper values..", Toast.LENGTH_LONG).show();


            }
        });

        // closing

        TextView close = myDialog.findViewById(R.id.close);
        close.setOnClickListener(view -> myDialog.dismiss());


        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //myDialog.setCancelable(false);


        myDialog.show();
    }

    public void loadFuturePopUp(Futures futures, double _fu) {

        myDialog.setContentView(R.layout.equitycalculate);
        TextView scrip;
        final TextView mis_mux, nrml;
        Button cal;
        final EditText price, amt;
        scrip = myDialog.findViewById(R.id.scrip);
        mis_mux = myDialog.findViewById(R.id.mis_mux);
        nrml = myDialog.findViewById(R.id.mis_mux2);
        cal = myDialog.findViewById(R.id.calculate);
        price = myDialog.findViewById(R.id.price);
        amt = myDialog.findViewById(R.id.amount);
        if (_fu != 0)
            amt.setText(_fu + "");

        scrip.setText(futures.getScrip());
        mis_mux.setText("MIS : " + futures.getMis());
        nrml.setText("NRML : " + futures.getNrml());
        price.setText(futures.getPrice() + "");


        cal.setOnClickListener(v -> {

            if (price.getText().toString().isEmpty() || amt.getText().toString().isEmpty())
                Toast.makeText(context, "Fields Can't be empty", Toast.LENGTH_SHORT).show();

            else {
                TextView misqty = myDialog.findViewById(R.id.misqty);
                TextView cncqty = myDialog.findViewById(R.id.cncqty);

                double am = 0, prc = 0;
                if (!amt.getText().toString().startsWith(".") && !price.getText().toString().startsWith(".")) {
                    am = Double.parseDouble(amt.getText().toString());
                    //TODO
                    prc = Double.parseDouble(price.getText().toString());
                    int res[] = changeParams(mis_mux, nrml, prc, futures);
                    misqty.setText(Math.floor(((am / res[0]))) + "");
                    cncqty.setText(Math.floor(((am / res[1]))) + "");
                } else
                    Toast.makeText(context, "Please enter proper values..", Toast.LENGTH_LONG).show();


            }
        });

        // closing

        TextView close = myDialog.findViewById(R.id.close);
        close.setOnClickListener(view -> myDialog.dismiss());


        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // myDialog.setCancelable(false);
        myDialog.show();
    }


    public void loadCurrencyPopUp(Currency futures, double _cu) {

        myDialog.setContentView(R.layout.equitycalculate);


        TextView scrip;
        final TextView mis_mux, nrml;
        Button cal;
        final EditText price, amt;


        scrip = myDialog.findViewById(R.id.scrip);
        mis_mux = myDialog.findViewById(R.id.mis_mux);
        nrml = myDialog.findViewById(R.id.mis_mux2);
        cal = myDialog.findViewById(R.id.calculate);
        price = myDialog.findViewById(R.id.price);
        amt = myDialog.findViewById(R.id.amount);
        if (_cu != 0)
            amt.setText(_cu + "");

        scrip.setText(futures.getScrip());
        mis_mux.setText("MIS : " + futures.getMis());
        nrml.setText("NRML : " + futures.getNrml());

        price.setText(futures.getPrice() + "");

        cal.setOnClickListener(v -> {

            if (price.getText().toString().isEmpty() || amt.getText().toString().isEmpty())
                Toast.makeText(context, "Fields Can't be empty", Toast.LENGTH_SHORT).show();

            else {
                TextView misqty = myDialog.findViewById(R.id.misqty);
                TextView cncqty = myDialog.findViewById(R.id.cncqty);

                double am = 0, prc = 0;
                if (!amt.getText().toString().startsWith(".") && !price.getText().toString().startsWith(".")) {
                    am = Double.parseDouble(amt.getText().toString());

                    prc = Double.parseDouble(price.getText().toString());
                    int res[] = changeParams(mis_mux, nrml, prc, futures);
                    misqty.setText(Math.floor(((am / res[0]))) + "");
                    cncqty.setText(Math.floor(((am / res[1]))) + "");
                } else
                    Toast.makeText(context, "Please enter proper values..", Toast.LENGTH_LONG).show();


            }
        });

        // closing

        TextView close = myDialog.findViewById(R.id.close);
        close.setOnClickListener(view -> myDialog.dismiss());


        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //myDialog.setCancelable(false);
        myDialog.show();
    }


    public int[] changeParams(TextView mis, TextView cnc, double uprice, Commodity commodity) {
        int res[] = new int[2];
        int mval = Integer.parseInt(commodity.getMis());
        int nval = Integer.parseInt(commodity.getNrml());
        double aprice = Double.parseDouble(commodity.getPrice());
        int new_mval = 0, new_nval = 0;

        new_mval = (int) ((uprice * (mval)) / aprice);
        new_nval = (int) ((uprice * (nval)) / aprice);

        mis.setText("MIS : " + new_mval);
        cnc.setText("CNC : " + new_nval);
        res[0] = new_mval;
        res[1] = new_nval;


        return res;


    }

    public int[] changeParams(TextView mis, TextView cnc, double uprice, Futures futures) {
        int res[] = new int[2];
        int mval = Integer.parseInt(futures.getMis());
        int nval = Integer.parseInt(futures.getNrml());
        double aprice = Double.parseDouble(futures.getPrice());
        int new_mval = 0, new_nval = 0;

        new_mval = (int) ((uprice * (mval)) / aprice);
        new_nval = (int) ((uprice * (nval)) / aprice);

        mis.setText("MIS : " + new_mval);
        cnc.setText("CNC : " + new_nval);
        res[0] = new_mval;
        res[1] = new_nval;

        return res;


    }

    public int[] changeParams(TextView mis, TextView cnc, double uprice, Currency currency) {
        int res[] = new int[2];
        float mval = currency.getMis();
        float nval = currency.getNrml();
        double aprice = currency.getPrice();
        int new_mval = 0, new_nval = 0;

        new_mval = (int) ((uprice * (mval)) / aprice);
        new_nval = (int) ((uprice * (nval)) / aprice);

        mis.setText("MIS : " + new_mval);
        cnc.setText("CNC : " + new_nval);
        res[0] = new_mval;
        res[1] = new_nval;

        return res;


    }


}
