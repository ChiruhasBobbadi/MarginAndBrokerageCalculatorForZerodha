package com.chiruhas.android.zerodha.HelperClasses;

import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;

import java.util.Collections;
import java.util.List;

public class SortHelper {


    public static List<GodModel> equitySort(List<GodModel> equity, boolean mish2l, boolean nrmlh2l) {

        Collections.sort(equity, (g1, g2) -> {
            int value = (int) (g2.getMis_multiplier() - g1.getMis_multiplier());
            if (nrmlh2l && value == 0) {
                return (int) (g2.getNrml_multiplier() - g1.getNrml_multiplier());
            }
            return value;


        });


        return equity;

    }

    public static List<GodModel> equityDefaultSort(List<GodModel> equity) {
        Collections.sort(equity, (g1, g2) -> (g1.getTradingsymbol().compareTo(g2.getTradingsymbol())));
        return equity;
    }


    public static List<Commodity> commoditySort(List<Commodity> commodity, boolean mish2l, boolean nrmlh2l, boolean priceh2l) {


        Collections.sort(commodity, (g1, g2) -> {
            if (g1.getMis().equals("N/A"))
                g1.setMis("0");
            int n1 = Integer.parseInt(g1.getMis());
            int n2 = Integer.parseInt(g2.getMis());
            int value = (n2 - n1); // Descending

            if (nrmlh2l && value == 0) {
                int n11 = Integer.parseInt(g1.getNrml());
                int n12 = Integer.parseInt(g2.getNrml());
                int value2 = (n12 - n11);

                if (priceh2l && value2 == 0) {
                    int n21 = Integer.parseInt(g1.getPrice());
                    int n22 = Integer.parseInt(g2.getPrice());
                    return (n22 - n21);
                }
                return value2;
            }
            return value;

        });


        return commodity;

    }

    public static List<Commodity> commodityDefaultSort(List<Commodity> commodity) {
        Collections.sort(commodity, (g1, g2) -> (g1.getScrip().compareTo(g2.getScrip())));
        return commodity;
    }

    public static List<Futures> futureSort(List<Futures> futures, boolean mish2l, boolean nrmlh2l, boolean priceh2l) {
        //code
        return futures;

    }

    public static List<Futures> futuresDefaultSort(List<Futures> futures) {
        Collections.sort(futures, (g1, g2) -> (g1.getScrip().compareTo(g2.getScrip())));
        return futures;
    }

    public static List<Currency> currencySort(List<Currency> currency, boolean mish2l, boolean nrmlh2l, boolean priceh2l) {


        return currency;

    }

    public static List<Currency> currencyDefaultSort(List<Currency> currency) {
        Collections.sort(currency, (g1, g2) -> (g1.getScrip().compareTo(g2.getScrip())));
        return currency;
    }

}

