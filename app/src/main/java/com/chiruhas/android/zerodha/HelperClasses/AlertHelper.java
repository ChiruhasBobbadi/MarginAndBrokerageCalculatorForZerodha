package com.chiruhas.android.zerodha.HelperClasses;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.View.Activities.CommodityActivity;

public class AlertHelper {
    Context context;
    Dialog myDialog;

     float cnc1,mis1;

    public AlertHelper(Context context){
        this.context=context;
        myDialog = new Dialog(context);
        cnc1=0.0f;
        mis1=0.0f;
    }
    public  void loadEquityPopup(GodModel item){
        final View view = LayoutInflater.from(context).inflate(R.layout.equitycalculate, null);

        myDialog.setContentView(R.layout.equitycalculate);


        TextView scrip;
        final TextView mis_mux,nrml;
        Button cal;
        final EditText price,amt;
        String am="";
        scrip = myDialog.findViewById(R.id.scrip);
        mis_mux = myDialog.findViewById(R.id.mis_mux);
        nrml = myDialog.findViewById(R.id.mis_mux2);
        cal = myDialog.findViewById(R.id.calculate);
        price = myDialog.findViewById(R.id.price);
        amt = myDialog.findViewById(R.id.amount);

        scrip.setText(item.getTradingsymbol());
        mis_mux.setText("MIS : " + item.getMis_multiplier() + "X");
        nrml.setText("CNC : "+1.0+"X");

        mis1 = item.getMis_multiplier();
        cnc1 = 1.0f;

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(price.getText().toString().isEmpty() || amt.getText().toString().isEmpty())
                    Toast.makeText(context, "Fields Can't be empty", Toast.LENGTH_SHORT).show();

                else{
                    TextView misqty = myDialog.findViewById(R.id.misqty);
                    TextView cncqty = myDialog.findViewById(R.id.cncqty);
                    double am = Double.parseDouble(amt.getText().toString());

                    double prc = Double.parseDouble(price.getText().toString());
                    misqty.setText(Math.floor(((am * mis1) / prc)) + "");
                    cncqty.setText(Math.floor(((am*cnc1) / prc)) + "");

                }
            }
        });

        // closing

        TextView close = myDialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });





        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setCancelable(false);
        myDialog.show();
    }

    public void nrml_bo(String name,String margin,String avalue, String leverage){
       // final View view = LayoutInflater.from(context).inflate(R.layout.bo_nrml_popup, null);

        myDialog.setContentView(R.layout.bo_nrml_popup);


        TextView sc = myDialog.findViewById(R.id.scrip);
        sc.setText(name);
        TextView mar = myDialog.findViewById(R.id.margin);
        mar.setText(" RS "+Math.round(Double.parseDouble(margin) * 100.0) / 100.0+" /-");
        TextView av = myDialog.findViewById(R.id.values);
        av.setText(" RS "+Math.round(Double.parseDouble(avalue) * 100.0) / 100.0+" /-");
        TextView lev = myDialog.findViewById(R.id.leverage);
        lev.setText(Math.round(Double.parseDouble(leverage) * 100.0) / 100.0+" X");
        TextView close = myDialog.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setCancelable(false);
        myDialog.show();
    }

}
