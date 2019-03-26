package com.chiruhas.android.zerodha.HelperClasses;

import android.content.Context;

import com.chiruhas.android.zerodha.Model.Equity.GodModel;

public class BracketOrder {

    /**
     *
     * @param symbol
     * @param price
     * @param qty
     * @param sl
     * @param type type of order (Equity || commodity || currency)
     * @param trade_type ( Buy || Sell)
     * @param g
     */
    public static void calculate(Context context, String symbol , String price , String qty, String sl, String type, String trade_type, GodModel g){


        if(type=="equity"){
             double a[] = equity(g,Double.parseDouble(price),Double.parseDouble(sl),Integer.parseInt(qty),trade_type);
            new AlertHelper(context).nrml_bo(g.getTradingsymbol(),a[0]+"",a[1]+"",a[2]+"");
        }
        else if(type=="mcx"){

        }
        else if(type=="nfo"){

        }
        else if(type=="cds"){

        }




    }

    static double[] equity(GodModel g,double price,double stoploss,int quantity,String transaction_type){

        double co_lower =g.getCo_lower();
        double co_upper = g.getCo_upper();
        co_lower = co_lower/100;
        co_upper = co_upper/100;

        double trigger = price - (co_upper * price);

        if(transaction_type.equals("buy"))
        {    stoploss = price-stoploss;
            if (stoploss < trigger)
            stoploss = trigger;
        else
            trigger = stoploss;

        }
        else{
            stoploss = price+stoploss;
            if (stoploss > trigger)
                stoploss = trigger;
            else
                trigger = stoploss;
        }


        double x = 0;

        if(transaction_type.equals("buy"))
        x = (price - trigger) * quantity;
        else
        x = (trigger - price) * quantity;

        double y = co_lower * price * quantity;

        double margin = x > y ? x : y;
        margin = margin + (margin * 0.2);

        double leverage = (price*quantity)/margin;

        double a[] = {margin,price*quantity,leverage};


        return a;


    }

}
