package com.chiruhas.android.zerodha.HelperClasses;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chiruhas.android.zerodha.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BrokerageHelper {


    EditText buy, sell, qty;
    RadioGroup rg;
    RadioButton nse, bse;
    TextView pl;
    ListView list;
    // Brokerage function here

    public void brokerageCalculate(Context context, View view, int position, char type) {


        buy = view.findViewById(R.id.buy);
        sell = view.findViewById(R.id.sell);
        qty = view.findViewById(R.id.lot);
        rg = view.findViewById(R.id.rgroup);
        nse = view.findViewById(R.id.nse);
        bse = view.findViewById(R.id.bse);
        pl = view.findViewById(R.id.pl);
        list = view.findViewById(R.id.list);
        ArrayList<String> a = new ArrayList<>();


        // start of cal code
        double b = Double.parseDouble(buy.getText().toString());
        double s = Double.parseDouble(sell.getText().toString());
        int q = Integer.parseInt(qty.getText().toString());

        String msg[] = {"Turnover : ", "Brokerage : ", "STT Total : ", "Exchange txn charge : ", "Clearing charge : ", "GST : ", "SEBI charges : "};


        // flag for checking nse or bse
        boolean flag = false;
        double data[] = calculate(b, s, q, findType(position, type));
        double total_tax = 0;
        List lst = new ArrayList();

        int id = rg.getCheckedRadioButtonId();

        switch (id) {
            case R.id.nse:
                flag = true;
                break;
            case R.id.bse:
                flag = false;
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

        pl.setText(net + "");
        // changing text color
        if (!profit) {
            pl.setTextColor(Color.RED);
        } else {
            pl.setTextColor(Color.GREEN);
        }

    }


    /**
     * @param buy
     * @param sell
     * @param qty
     * @param type to specify the type for which brokerage is need to be calculated
     *             E0 indicates Equity intraday
     *             E1 indicates Equity Delivery
     *             E2 indicates Equity Futures
     *             E3 indicate Equity Options
     *             CU0 indicates Currenxy Futures
     *             CU1 indicates Currency Options
     *             C0 indicates Commodity Futures
     *             C1 indicates Commodity Options
     */
    public double[] details(double buy, double sell, int qty, String type) {


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

                c[0] = 0.01;
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
                c[0] = 0.01;
                c[1] = 0.01;
                c[2] = 0.0019;
                c[3] = 0.003;
                c[4] = 0.0002;
                c[5] = 18;
            } else if (type.equals("E3")) {
                c[0] = 0;
                c[1] = 0.05;
                c[2] = 0.05;
                c[3] = 0.003;
                c[4] = 0.002;
                c[5] = 18;
            }
        }
        else if(type.startsWith("C")){
            if(type.equals("CU0")){
                c[0] = 0.01;
                c[1] = 0;
                c[2] =0.0009 ;
                c[3] = 0.00022;
                c[4] = 0.0002;
                c[5] = 18;

            }
            else if(type.equals("CU1")){
                c[0] = 0.01;
                c[1] = 0;
                c[2] =0.04 ;
                c[3] = 0.001;
                c[4] = 0.002;
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

        double per[] = details(buy, sell, qty, type);

        double buy_amt = buy * qty;
        double sell_amt = sell * qty;
        double tax[] = new double[10];
        double brokerage = (buy_amt * per[0]) / 100 > 20 ? 20 : (buy_amt * per[0]) / 100;
        brokerage += (sell_amt * per[0]) / 100 > 20 ? 20 : (sell_amt * per[0]) / 100;
        tax[0] = buy_amt + sell_amt;

        if (type.equals("E3"))
            brokerage = 40;

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
        tax[5] = tax[0] * per[4] / 100;

        double gst = (((brokerage + tax[3]) * 18) / 100);
        tax[6] = gst;
        gst = (((brokerage + tax[4]) * 18) / 100);
        tax[7] = gst;

        double sebi = ((buy_amt * 15) + (sell_amt * 15)) / (10000000);

        tax[8] = sebi;


        return tax;
    }


    /**
     * @param pos
     * @param ty
     * @return string indicating the type of order
     */
    public String findType(int pos, char ty) {
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

        }


        return type;
    }
}
