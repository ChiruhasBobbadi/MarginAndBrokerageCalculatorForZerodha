package com.chiruhas.android.zerodha.HelperClasses;

import com.chiruhas.android.zerodha.Model.Equity.GodModel;

public class BracketOrder {

    public static void calculate(String symbol , String price , String qty, String sl, String type, String trade_type, GodModel g){

        double margin=0;
        double req_amt=0;

        equity(g,Double.parseDouble(price),Double.parseDouble(sl),Integer.parseInt(qty),trade_type);



    }

    static void equity(GodModel g,double price,double stoploss,int quantity,String transaction_type){

        double co_lower =g.getCo_lower();
        double co_upper = g.getCo_upper();
        co_lower = co_lower/100;
        co_upper = co_upper/100;
        double trigger = price - (co_upper * price);


        if (stoploss < trigger)
        stoploss = trigger;
        else
        trigger = stoploss;

        double x = 0;

        if(transaction_type == "buy")
        x = (price - trigger) * quantity;
        else
        x = (trigger - price) * quantity;

        double y = co_lower * price * quantity;

        double margin = x > y ? x : y;
        margin = margin + (margin * 0.2);






    }

}
