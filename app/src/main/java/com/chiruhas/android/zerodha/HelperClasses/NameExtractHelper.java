package com.chiruhas.android.zerodha.HelperClasses;

import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;

import java.util.List;

public class NameExtractHelper {

    public static String[] EquityNames(List<GodModel> lst){

        String[] str = new String[lst.size()];

        for (int i = 0; i < lst.size(); i++) {
            str[i] =lst.get(i).getTradingsymbol();
        }


        return str;
    }

    public static String[] commodityName(List<Commodity> lst) {
        String[] str = new String[lst.size()];

        for (int i = 0; i < lst.size(); i++) {
            str[i] = lst.get(i).getScrip();
        }
        return str;
    }

    public static String[] futureNames(List<Futures> lst) {
        String[] str = new String[lst.size()];

        for (int i = 0; i < lst.size(); i++) {
            str[i] = lst.get(i).getScrip() + " " + lst.get(i).getExpiry();
        }
        return str;
    }

    public static String[] currencyNames(List<Currency> lst) {
        String[] str = new String[lst.size()];

        for (int i = 0; i < lst.size(); i++) {
            str[i] = lst.get(i).getScrip() + " " + lst.get(i).getExpiry();
        }
        return str;
    }
}
