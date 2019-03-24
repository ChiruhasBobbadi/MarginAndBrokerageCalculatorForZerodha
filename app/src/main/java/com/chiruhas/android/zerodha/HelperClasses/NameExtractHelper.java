package com.chiruhas.android.zerodha.HelperClasses;

import com.chiruhas.android.zerodha.Model.Equity.GodModel;

import java.util.ArrayList;
import java.util.List;

public class NameExtractHelper {

    public static String[] EquityNames(List<GodModel> lst){

        String[] str = new String[lst.size()];

        for (int i = 0; i < lst.size(); i++) {
            str[i] =lst.get(i).getTradingsymbol();
        }


        return str;
    }
}
