package com.chiruhas.android.zerodha.HelperClasses;

import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;

public class ObjectConverter {

    public static GodModel commodity2God(Commodity commodity) {
        return new GodModel(commodity.getMargin(), commodity.getCo_lower(), commodity.getMis_multiplier(), commodity.getScrip(), Double.parseDouble(commodity.getPrice()), commodity.getCo_upper(), Integer.parseInt(commodity.getNrml()), Integer.parseInt(commodity.getMis()), "", commodity.getLot());
    }

    public static GodModel future2God(Futures commodity) {
        return new GodModel(commodity.getMargin(), commodity.getCo_lower(), commodity.getMis_multiplier(), commodity.getScrip(), Double.parseDouble(commodity.getPrice()), commodity.getCo_upper(), Integer.parseInt(commodity.getNrml()), Integer.parseInt(commodity.getMis()), commodity.getExpiry(), commodity.getLot());

    }

    public static GodModel currency2God(Currency commodity) {
        return new GodModel(commodity.getMargin(), commodity.getCo_lower(), commodity.getMis_multiplier(), commodity.getScrip(), commodity.getPrice(), commodity.getCo_upper(), commodity.getNrml(), commodity.getMis(), commodity.getExpiry(), commodity.getLot());
    }









}
