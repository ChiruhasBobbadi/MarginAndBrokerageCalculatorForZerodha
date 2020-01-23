package com.chiruhas.android.zerodha.HelperClasses;

import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;

import java.util.Collections;
import java.util.List;

public class SortHelper {


    public static List<GodModel> equitySort(String s, List<GodModel> equity) {
        if (s.equals("mis_l2h")) {
            Collections.sort(equity, (g1, g2) -> {
                return (int) (g1.getMis_multiplier() - g2.getMis_multiplier()); // Ascending
            });

        } else if (s.equals("mis_h2l")) {
            Collections.sort(equity, (g1, g2) -> {
                return (int) (g2.getMis_multiplier() - g1.getMis_multiplier()); // descending
            });

        } else if (s.equals("nrml_h2l")) {
            Collections.sort(equity, (g1, g2) -> {
                return (int) (g2.getNrml_multiplier() - g1.getNrml_multiplier()); // descending
            });

        } else if (s.equals("nrml_l2h")) {
            Collections.sort(equity, (g1, g2) -> {
                return (int) (g1.getNrml_multiplier() - g2.getNrml_multiplier()); // desecneding
            });

        }

        return equity;

    }

    public static List<Commodity> commoditySort(String s, List<Commodity> commodity) {
        if (s.equals("mis_l2h")) {

            Collections.sort(commodity, (g1, g2) -> {
                if (g1.getMis().equals("N/A"))
                    g1.setMis("0");
                int n1 = Integer.parseInt(g1.getMis());
                int n2 = Integer.parseInt(g2.getMis());
                return (n1 - n2); // Ascending
            });

        } else if (s.equals("mis_h2l")) {
            Collections.sort(commodity, (g1, g2) -> {
                if (g1.getMis().equals("N/A"))
                    g1.setMis("0");
                int n1 = Integer.parseInt(g1.getMis());
                int n2 = Integer.parseInt(g2.getMis());
                return (n2 - n1); // Ascending
            });

        } else if (s.equals("nrml_h2l")) {
            Collections.sort(commodity, (g1, g2) -> {
                int n1 = Integer.parseInt(g1.getNrml());
                int n2 = Integer.parseInt(g2.getNrml());
                return (n2 - n1); // descending
            });

        } else if (s.equals("nrml_l2h")) {
            Collections.sort(commodity, (g1, g2) -> {
                int n1 = Integer.parseInt(g1.getNrml());
                int n2 = Integer.parseInt(g2.getNrml());
                return (n1 - n2); // descending
            });

        }

        return commodity;

    }

    public static List<Futures> futureSort(String s, List<Futures> futures) {
        if (s.equals("mis_l2h")) {

            Collections.sort(futures, (g1, g2) -> {
                if (g1.getMis().equals("N/A"))
                    g1.setMis("0");
                int n1 = Integer.parseInt(g1.getMis());
                int n2 = Integer.parseInt(g2.getMis());
                return (n1 - n2); // Ascending
            });

        } else if (s.equals("mis_h2l")) {
            Collections.sort(futures, (g1, g2) -> {
                if (g1.getMis().equals("N/A"))
                    g1.setMis("0");
                int n1 = Integer.parseInt(g1.getMis());
                int n2 = Integer.parseInt(g2.getMis());
                return (n2 - n1); // Ascending
            });

        } else if (s.equals("nrml_h2l")) {
            Collections.sort(futures, (g1, g2) -> {
                int n1 = Integer.parseInt(g1.getNrml());
                int n2 = Integer.parseInt(g2.getNrml());
                return (n2 - n1); // descending
            });

        } else if (s.equals("nrml_l2h")) {
            Collections.sort(futures, (g1, g2) -> {
                int n1 = Integer.parseInt(g1.getNrml());
                int n2 = Integer.parseInt(g2.getNrml());
                return (n1 - n2); // descending
            });

        }

        return futures;

    }


    public static List<Currency> currencySort(String s, List<Currency> currency) {
        if (s.equals("mis_l2h")) {

            Collections.sort(currency, (g1, g2) -> {

//                int n1 = Integer.parseInt(g1.getMis());
//                int n2 = Integer.parseInt(g2.getMis());
                return (g1.getMis() - g2.getMis()); // Ascending
            });

        } else if (s.equals("mis_h2l")) {
            Collections.sort(currency, (g1, g2) -> {


                return (g2.getMis() - g1.getMis()); // Ascending
            });

        } else if (s.equals("nrml_h2l")) {
            Collections.sort(currency, (g1, g2) -> {

                return (g2.getNrml() - g1.getNrml()); // descending
            });

        } else if (s.equals("nrml_l2h")) {
            Collections.sort(currency, (g1, g2) -> {

                return (g1.getNrml() - g2.getNrml()); // descending
            });

        }

        return currency;

    }


}

