package com.chiruhas.android.zerodha.HelperClasses;


import java.text.DecimalFormat;

/**
 * This class handles state wise stamp duty costs
 * <p>
 * it contains methods which take in the the type of order as parameter and returns the value
 */
public class StateTaxHelper {


    /**
     * @param type to specify the type for which brokerage is need to be calculated
     *             *             E0 indicates Equity intraday
     *             *             E1 indicates Equity Delivery
     *             *             E2 indicates Equity Futures
     *             *             E3 indicate Equity Options
     *             *             CU0 indicates Currency Futures
     *             *             CU1 indicates Currency Options
     *             *             c0 indicates Commodity Futures
     *             *             c1 indicates Commodity Options
     * @return
     */
    public static double stampDuty(String type, double buy) {
        //initialising data
        DecimalFormat df = new DecimalFormat("0.00");

        double per = 0;
        double tax = 0;

        if (type.startsWith("E")) {
            if (type.equals("E0"))
                per = Double.parseDouble(df.format(Math.min((0.003f * buy / 100f), ((3f / 100000f) * buy))));
            else if (type.equals("E1"))
                per = Double.parseDouble(df.format(Math.min((0.015f * buy / 100f), (15f / 100000f) * buy)));
            else if (type.equals("E2"))
                per = Double.parseDouble(df.format(Math.min((0.002f * buy / 100f), (2f / 100000f) * buy)));
            else if (type.equals("E3"))
                per = Double.parseDouble(df.format(Math.min((0.003f * buy / 100f), (3f / 100000f) * buy)));
        } else if (type.startsWith("C")) {
            if (type.equals("CU0"))
                per = Double.parseDouble(df.format(Math.min((0.002f * buy / 100f), (2f / 100000f) * buy)));
            else if (type.equals("C0") || type.equals("C1"))
                per = Double.parseDouble(df.format(Math.min((0.003f * buy / 100f), (3f / 100000f) * buy)));
        }
        /**
         * Currency OPTIONS Still under Development
         */
//            } else if (type.equals("CU1")) {
//                per = (turnover * m.getCur2()) / 100;
//                if (per > m.getCur2()) {
//                    if (m.getCur2() == 0)
//                        tax = per;
//                    else tax = m.getCur2();
//
//                    per = tax;
//                }


        return per;
    }

}

