package com.chiruhas.android.zerodha.HelperClasses;

import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;

import java.util.Collections;
import java.util.List;

public class SortHelper {


    public static List<GodModel> equitySort(List<GodModel> equity, boolean mish2l, boolean nrmlh2l) {
        if (!mish2l && !nrmlh2l) {
            return equityDefaultSort(equity);
        } else {

            Collections.sort(equity, (g1, g2) -> {
                if (mish2l)
                    return (int) (g2.getMis_multiplier() - g1.getMis_multiplier());

                else {

                    return (int) (g2.getNrml_multiplier() - g1.getNrml_multiplier());

                }


            });


        }


        return equity;

    }

    public static List<GodModel> equityDefaultSort(List<GodModel> equity) {
        Collections.sort(equity, (g1, g2) -> (g1.getTradingsymbol().compareTo(g2.getTradingsymbol())));
        return equity;
    }


    public static List<Commodity> commoditySort(List<Commodity> commodity, boolean mish2l, boolean nrmlh2l, boolean priceh2l) {

        if (!mish2l && !nrmlh2l && !priceh2l) {
            return commodityDefaultSort(commodity);
        } else {
            Collections.sort(commodity, (g1, g2) -> {

                if (mish2l) {
                    if (g1.getMis().equals("N/A"))
                        g1.setMis("0");
                    int n1 = Integer.parseInt(g1.getMis());
                    int n2 = Integer.parseInt(g2.getMis());
                    return (n2 - n1); // Descending
                } else if (nrmlh2l) {
                    int n1 = Integer.parseInt(g1.getNrml());
                    int n2 = Integer.parseInt(g2.getNrml());
                    return (n2 - n1);
                } else {
                    int n1 = (int) Double.parseDouble(g1.getPrice());
                    int n2 = (int) Double.parseDouble(g2.getPrice());
                    return (n2 - n1);
                }

            });


        }


        return commodity;

    }

    public static List<Commodity> commodityDefaultSort(List<Commodity> commodity) {
        Collections.sort(commodity, (g1, g2) -> (g1.getScrip().compareTo(g2.getScrip())));
        return commodity;
    }

    public static List<Futures> futureSort(List<Futures> futures, boolean mish2l, boolean nrmlh2l, boolean priceh2l) {
        if (!mish2l && !nrmlh2l && !priceh2l) {
            return futuresDefaultSort(futures);
        } else {
            Collections.sort(futures, (g1, g2) -> {

                if (mish2l) {
                    if (g1.getMis().equals("N/A"))
                        g1.setMis("0");
                    int n1 = Integer.parseInt(g1.getMis());
                    int n2 = Integer.parseInt(g2.getMis());
                    return (n2 - n1); // Descending
                } else if (nrmlh2l) {
                    int n1 = Integer.parseInt(g1.getNrml());
                    int n2 = Integer.parseInt(g2.getNrml());
                    return (n2 - n1);
                } else {
                    int n1 = (int) Double.parseDouble(g1.getPrice());
                    int n2 = (int) Double.parseDouble(g2.getPrice());
                    return (n2 - n1);
                }

            });


        }

        return futures;

    }

    public static List<Futures> futuresDefaultSort(List<Futures> futures) {
        Collections.sort(futures, (g1, g2) -> (g1.getScrip().compareTo(g2.getScrip())));
        return futures;
    }

    public static List<Currency> currencySort(List<Currency> currency, boolean mish2l, boolean nrmlh2l, boolean priceh2l) {

        if (!mish2l && !nrmlh2l && !priceh2l) {
            return currencyDefaultSort(currency);
        } else {
            Collections.sort(currency, (g1, g2) -> {

                if (mish2l) {

                    return (g2.getMis() - g1.getMis()); // Descending
                } else if (nrmlh2l) {

                    return (g2.getNrml() - g1.getNrml());
                } else {

                    return (int) (g2.getPrice() - g1.getPrice());
                }

            });


        }

        return currency;

    }

    public static List<Currency> currencyDefaultSort(List<Currency> currency) {
        Collections.sort(currency, (g1, g2) -> (g1.getScrip().compareTo(g2.getScrip())));
        return currency;
    }

}

