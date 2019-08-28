package com.chiruhas.android.zerodha.HelperClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BrokerageHelper {


    EditText buy, sell, qty;
    RadioGroup rg;
    RadioButton nse, bse;
    TextView pl;
    ListView list;
    String state;
    int lot_size=0;
    AutoCompleteTextView auto;

    private Map<String, Integer> map = new HashMap<>();

    public void setMap() {

        map.put("ALUMINI", 1000);
        map.put("ALUMINIUM", 5000);
        map.put("BRASSPHY", 1000);
        map.put("CARDAMOM", 100);
        map.put("CASTORSEED", 1000);
        map.put("COPPER", 1000);
        map.put("COPPERM", 250);
        map.put("COTTON", 25);
        map.put("CPO", 1000);
        map.put("CRUDEOIL", 100);

        map.put("CRUDEOILM", 10);
        map.put("GOLD", 100);
        map.put("GOLDGUINEA", 1);
        map.put("GOLDM", 10);
        map.put("GOLDPETAL", 1);
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
        map.put("ZINCINI", 1000);
    }

    // Brokerage function here



    @SuppressLint("ClickableViewAccessibility")
    public void brokerageCalculate(Context context, View view, int position, char type, String states) {


        buy = view.findViewById(R.id.buy);
        sell = view.findViewById(R.id.sell);
        qty = view.findViewById(R.id.lot);
        rg = view.findViewById(R.id.rgroup);
        nse = view.findViewById(R.id.nse);
        bse = view.findViewById(R.id.bse);
        pl = view.findViewById(R.id.pl);
        list = view.findViewById(R.id.list);

        ArrayList<String> a = new ArrayList<>();

        list.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        state = states;
        // start of cal code
        double b = Double.parseDouble(buy.getText().toString());
        double s = Double.parseDouble(sell.getText().toString());
        int q = Integer.parseInt(qty.getText().toString().trim());

        if(type=='C')
            q=q*1000;
        // calculating effective quantity for commodity

        if(type=='c'){
            auto = view.findViewById(R.id.auto_text);
                setMap();
                lot_size = map.get(auto.getText().toString().trim());
                q= Integer.parseInt((lot_size*Integer.parseInt(qty.getText().toString())+""));

        }



        String msg[] = {"Turnover : ", "Brokerage : ", "STT Total : ", "Exchange txn charge : ", "Clearing charge : ", "GST : ", "SEBI charges : "};


        // flag for checking nse or bse

        //considering commodity txn charges in nse place holder.
        //TODO
        boolean flag = true;
        double data[] = calculate(b, s, q, findType(position, type));
        double total_tax = 0;
        int id=-1;
        List lst = new ArrayList();

        if(type!='c')
         id= rg.getCheckedRadioButtonId();

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

        pl.setText(net + "");
        // changing text color
        if (!profit) {
            pl.setTextColor(Color.RED);
        } else {
            pl.setTextColor(Color.GREEN);
        }

    }




    int getEffective(String qty, String metric) {

        int q = Integer.parseInt(qty);
        int effective;
        effective = q;
        if (metric.equals("MT"))
            effective = q * 1000;


        return effective;


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
     *             CU0 indicates Currency Futures
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
                c[0] = 0.01;
                c[1] = 0;
                c[2] = 0.0009;
                c[3] = 0.00022;
                c[4] = 0;
                c[5] = 18;

            } else if (type.equals("CU1")) {
                c[0] = 0.01;
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
                c[0] = 0.01;
                c[1] = 0.01;
                c[2] = 0.0026;
                c[3] = 0;
                c[4] = 0;
                c[5] = 18;
            } else if (type.equals("C1")) {
                c[0] = 0.01;
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

        double per[] = details(buy, sell, qty, type);

        double buy_amt = buy * qty;
        double sell_amt = sell * qty;
        double tax[] = new double[10];

        // checking if it is currency futures

        double brokerage = (buy_amt * per[0]) / 100 > 20 ? 20 : (buy_amt * per[0]) / 100;
        brokerage += (sell_amt * per[0]) / 100 > 20 ? 20 : (sell_amt * per[0]) / 100;

        tax[0] = (buy_amt + sell_amt);

        if (type.equals("E3"))
            brokerage = 40;

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
        tax[9] = StateTaxHelper.stampDuty(type, tax[0], state);

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
            if (pos == 0)
                type = "C0";
            else if (pos == 1)
                type = "C1";
        }


        return type;
    }
}
