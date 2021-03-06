package com.chiruhas.android.zerodha.HelperClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chiruhas.android.zerodha.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class BrokerageHelper {

    private EditText percent;

    private AutoCompleteTextView auto;

    private Map<String, Integer> map = new HashMap<>();

    private void setMap() {

        map.put("ALUMINI", 1000);
        map.put("ALUMINIUM", 5000);
        map.put("BRASSPHY", 1000);
        map.put("CARDAMOM", 100);
        map.put("CASTORSEED", 100);
        map.put("COPPER", 2500);
        map.put("COPPERM", 250);
        map.put("COTTON", 25);
        map.put("CPO", 1000);
        map.put("CRUDEOIL", 100);

        map.put("CRUDEOILM", 10);
        map.put("GOLD", 100);
        map.put("GOLDGUINEA", 1);
        map.put("GOLDM", 10);
        map.put("GOLDPETAL", 1);
        map.put("KAPAS", 200);
        map.put("LEAD", 5000);
        map.put("LEADMINI", 1000);
        map.put("MENTHAOIL", 360);
        map.put("NATURALGAS", 1250);
        map.put("NICKEL", 250);
        map.put("NICKELM", 100);
        map.put("PEPPER", 10);
        map.put("RBDPMOLEIN", 1000);
        map.put("SILVER", 30);
        map.put("SILVERM", 5);

        map.put("SILVERMIC", 1);
        map.put("ZINC", 5000);
        map.put("ZINCMINI", 1000);
    }


    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n", "DefaultLocale"})
    public void brokerageCalculate(Context context, View view, int position, char type) {


        EditText buy = view.findViewById(R.id.buy);
        EditText sell = view.findViewById(R.id.sell);
        EditText qty = view.findViewById(R.id.lot);
        RadioGroup rg = view.findViewById(R.id.rgroup);

        percent = view.findViewById(R.id.per);


        TextView pl = view.findViewById(R.id.pl);
        ListView list = view.findViewById(R.id.list);


        // Setting on Touch Listener for handling the touch inside ScrollView
        list.setOnTouchListener((v, event) -> {
            // Disallow the touch request for parent scroll on touch of child view
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });


        // start of cal code
        double b = Double.parseDouble(buy.getText().toString());
        double s = Double.parseDouble(sell.getText().toString());
        int q = Integer.parseInt(qty.getText().toString().trim());

        if (type == 'C')
            q = q * 1000;
        // calculating effective quantity for commodity

        if (type == 'c') {
            auto = view.findViewById(R.id.auto_text);
            setMap();
            try {
                int lot_size = map.get(auto.getText().toString().trim());
                q = Integer.parseInt((lot_size * Integer.parseInt(qty.getText().toString()) + ""));
            } catch (Exception e) {
                Toast.makeText(context, "Oops something happened, please try again... ", Toast.LENGTH_SHORT).show();
            }

        }


        String msg[] = {"Turnover : ", "Brokerage : ", "STT Total : ",
                "Exchange txn charge : ", "Clearing charge : ", "GST : ", "SEBI charges : "};


        // flag for checking nse or bse

        //considering commodity txn charges in nse place holder.
        //TODO
        boolean flag = true;
        String types = findType(position, type);
        double[] data;
        if (types.equals("C0") || types.equals("C1"))
            data = calculate(b, s, q, types, auto.getText().toString().trim());
        else
            data = calculate(b, s, q, types);


        double total_tax = 0;
        int id = -1;
        List<Object> lst = new ArrayList<>();

        if (type != 'c')
            id = rg.getCheckedRadioButtonId();

        switch (id) {
            case R.id.nse:
                flag = true;
                break;
            case R.id.bse:
                flag = false;
                break;
            case -1:
                break;
        }

        if (flag) {
            int k = 0;
            for (int i = 0; i < data.length - 1; i++) {

                if (i == 4 || i == 7)
                    continue;
                lst.add(msg[k] + "\t" + Math.round(data[i] * 100.0) / 100.0);
                //skipping i==0 case
                if (i != 0)
                    total_tax += data[i];
                k++;

            }
        } else {
            int k = 0;
            for (int i = 0; i < data.length - 1; i++) {

                if (i == 3 || i == 6)
                    continue;
                lst.add(msg[k] + "\t" + Math.round(data[i] * 100.0) / 100.0);
                //skipping i==0 case
                if (i != 0)
                    total_tax += data[i];
                k++;
            }
        }

        total_tax += data[9];
        lst.add("Stamp Duty : " + data[9]);
        lst.add("Total tax and charges : " + Math.round(total_tax * 100.0) / 100.0);

        double net = 0;
        boolean profit = false;

        net = (s * q) - (b * q) - total_tax;
        net = Math.round(net * 100.0) / 100.0;

        if (net > 0)
            profit = true;


        ArrayAdapter arrayAdapter1 = new ArrayAdapter(context, android.R.layout.simple_list_item_1, lst) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                TextView textView = (TextView) v.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);
                return v;
            }
        };

        list.setAdapter(arrayAdapter1);
        double percent = (net / (b * q)) * 100;
        pl.setText(net + "\n( " + String.format("%.2f", percent) + " % )");
        // changing text color
        if (!profit) {
            pl.setTextColor(Color.RED);
        } else {
            pl.setTextColor(Color.GREEN);
        }

    }


    private double[] details(String type) {


        double c[] = new double[6];
        /**
         * 0th index Brokerage
         * 1st index STT/CTT
         * 2nd index Transaction charge for NSE
         * 3rd index Transcation charge for BSE
         * 4th index Clearing charge
         * 5th index GST
         *
         */
        if (type.startsWith("E")) {
            if (type.equals("E0")) {

                c[0] = 0.03;
                c[1] = 0.025;
                c[2] = 0.00325;
                c[3] = 0.003;
                c[4] = 0;
                c[5] = 18;
            } else if (type.equals("E1")) {
                c[0] = 0;
                c[1] = 0.1;
                c[2] = 0.00325;
                c[3] = 0.003;
                c[4] = 0;
                c[5] = 18;
            } else if (type.equals("E2")) {
                c[0] = 0.03;
                c[1] = 0.01;
                c[2] = 0.0019;
                c[3] = 0.003;
                c[4] = 0;
                c[5] = 18;
            } else if (type.equals("E3")) {
                c[0] = 0;
                c[1] = 0.05;
                c[2] = 0.05;
                c[3] = 0.003;
                c[4] = 0;
                c[5] = 18;
            }
        } else if (type.startsWith("C")) {
            if (type.equals("CU0")) {
                c[0] = 0.03;
                c[1] = 0;
                c[2] = 0.0009;
                c[3] = 0.00022;
                c[4] = 0;
                c[5] = 18;

            } else if (type.equals("CU1")) {
                c[0] = 0.03;
                c[1] = 0.05;
                c[2] = 0.04;
                c[3] = 0.001;
                c[4] = 0;
                c[5] = 18;
            }
            /**
             * for commodity since there is no NSE and BSE consider it as nse to maintain the flow
             */
            else if (type.equals("C0")) {
                c[0] = 0.03;
                c[1] = 0.01;
                c[2] = 0.0026;
                c[3] = 0;
                c[4] = 0;
                c[5] = 18;
            } else if (type.equals("C1")) {
                c[0] = 0.03;
                c[1] = 0.05;
                c[2] = 0;
                c[3] = 0;
                c[4] = 0;
                c[5] = 18;
            }
        }


        return c;
    }


    /**
     * @param buy
     * @param sell
     * @param qty  0th index turnover
     *             1st index brokerage
     *             2nd index stt
     *             3rd index txn charge if nse
     *             4th index txn charge if bse
     *             5th index clearing charge
     *             6th index GST if nse
     *             7th index GST if bse
     *             8th index sebi
     *             9th index
     */

    public double[] calculate(double buy, double sell, int qty, String type) {


        double per[] = details(type);

        try {
            if (percent.getVisibility() == View.VISIBLE) {
                per[0] = Double.parseDouble(percent.getText().toString());
            }
        } catch (Exception e) {
            Log.d(TAG, "calculate: exception");
        }

        double buy_amt = buy * qty;
        double sell_amt = sell * qty;
        double tax[] = new double[10];

        // checking if it is currency futures

        double brokerage = (buy_amt * per[0]) / 100 > 20 ? 20 : (buy_amt * per[0]) / 100;
        brokerage += (sell_amt * per[0]) / 100 > 20 ? 20 : (sell_amt * per[0]) / 100;

        tax[0] = (buy_amt + sell_amt);


        //TODO
        tax[1] = brokerage;
        double stt = 0;


        if (type.equals("E1"))
            stt = ((per[1] * buy_amt) / 100) + ((per[1] * sell_amt) / 100);
        else
            stt = ((per[1]) * sell_amt) / 100;


        tax[2] = stt;


        tax[3] = ((per[2] * buy_amt) / 100) + ((per[2] * sell_amt) / 100);
        tax[4] = ((per[3] * buy_amt) / 100) + (((per[3]) * sell_amt) / 100);

        //TODO
        // clearing charge

        tax[5] = 0;


        double gst = (((brokerage + tax[3]) * 18) / 100);
        tax[6] = gst;
        gst = (((brokerage + tax[4]) * 18) / 100);
        tax[7] = gst;

        double sebi = ((buy_amt * 15) + (sell_amt * 15)) / (10000000);

        tax[8] = sebi;

        // fetching data
        tax[9] = StateTaxHelper.stampDuty(type, buy_amt);

        if (type.equals("E3"))
            brokerage = 40;
        else if (type.equals("E1"))
            brokerage = 13.5 + tax[6] + tax[7];

        tax[1] = brokerage;

        return tax;
    }

    // overloaded method for commodity
    public double[] calculate(double buy, double sell, int qty, String type, String scrip) {

        double per[] = details(type);

        double buy_amt = buy * qty;
        double sell_amt = sell * qty;
        double tax[] = new double[10];

        // checking if it is currency futures

        double brokerage = (buy_amt * per[0]) / 100 > 20 ? 20 : (buy_amt * per[0]) / 100;
        brokerage += (sell_amt * per[0]) / 100 > 20 ? 20 : (sell_amt * per[0]) / 100;

        tax[0] = (buy_amt + sell_amt);


        //TODO
        tax[1] = brokerage;
        double stt = 0;

        if (scrip.equalsIgnoreCase("kapas"))
            per[1] = 0.001;
        stt = ((per[1]) * sell_amt) / 100;


        tax[2] = stt;


        if (scrip.equalsIgnoreCase("KAPAS") || scrip.equalsIgnoreCase("CASTORSEED"))
            per[2] = 0.0005;
        else if (scrip.equalsIgnoreCase("PEPPER"))
            per[2] = 0.00005;

        tax[3] = ((per[2] * buy_amt) / 100) + ((per[2] * sell_amt) / 100);
        tax[4] = ((per[3] * buy_amt) / 100) + (((per[3]) * sell_amt) / 100);

        //TODO
        // clearing charge
        //tax[5] = tax[0] * per[4] / 100;
        tax[5] = 0;
//        if (type.equals("C1")) {
//            tax[5] = ((buy_amt + sell_amt) * 200) / 10000000;
//        }

        double gst = (((brokerage + tax[3]) * 18) / 100);
        tax[6] = gst;
        gst = (((brokerage + tax[4]) * 18) / 100);
        tax[7] = gst;

        double sebi = ((buy_amt * 15) + (sell_amt * 15)) / (10000000);

        tax[8] = sebi;

        // fetching data
        tax[9] = StateTaxHelper.stampDuty(type, buy_amt);

        return tax;
    }


    /**
     * @param pos
     * @param ty
     * @return string indicating the type of order
     */
    private String findType(int pos, char ty) {
        String type = "";
        if (ty == 'e') {
            if (pos == 0)
                type = "E0";
            else if (pos == 1)
                type = "E1";
            else if (pos == 2)
                type = "E2";
            else if (pos == 3)
                type = "E3";
        } else if (ty == 'C') {

            if (pos == 0)
                type = "CU0";
            else if (pos == 1)
                type = "CU1";
        } else if (ty == 'c') {
            if (pos == 0)
                type = "C0";
            else if (pos == 1)
                type = "C1";
        }


        return type;
    }
}
