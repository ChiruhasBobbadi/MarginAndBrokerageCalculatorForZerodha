package com.chiruhas.android.zerodha.HelperClasses;

import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.Model.Equity.RoomModels.GodCommodity;
import com.chiruhas.android.zerodha.Model.Equity.RoomModels.GodEquity;

import java.util.ArrayList;
import java.util.List;

public class ObjectConverter {

    public static GodModel  commodity2God(Commodity commodity){
        return new GodModel(commodity.getMargin(),commodity.getCo_lower(),commodity.getMis_multiplier(),commodity.getScrip(),Double.parseDouble(commodity.getPrice()),commodity.getCo_upper(),Integer.parseInt(commodity.getNrml()),Integer.parseInt(commodity.getMis()),"",commodity.getLot());
    }

    public  static  GodModel future2God(Futures commodity){
        return new GodModel(commodity.getMargin(),commodity.getCo_lower(),commodity.getMis_multiplier(),commodity.getScrip(),Double.parseDouble(commodity.getPrice()),commodity.getCo_upper(),Integer.parseInt(commodity.getNrml()),Integer.parseInt(commodity.getMis()),commodity.getExpiry(),commodity.getLot());

    }

    public static GodModel currency2God(Currency commodity){
        return new GodModel(commodity.getMargin(),commodity.getCo_lower(),commodity.getMis_multiplier(),commodity.getScrip(),commodity.getPrice(),commodity.getCo_upper(),commodity.getNrml(),commodity.getMis(),commodity.getExpiry(),commodity.getLot());
    }


    // legacy code
//    public static GodModel GodEquitytoGodModel(GodEquity godEquity){
//
//        return new GodModel(godEquity.getMargin(),godEquity.getCo_lower(),godEquity.getMis_margin(),godEquity.getTradingsymbol()
//                ,godEquity.getCo_upper(),godEquity.getNrml_margin(),godEquity.getMis_margin());
//    }

    public static List<GodModel> GodEquitytoGodModel(List<GodEquity> godEquity){

        return null;
    }

    public static GodEquity GodModeltoGodEquity(GodModel godEquity){

        return new GodEquity(godEquity.getMargin(),godEquity.getCo_lower(),godEquity.getMis_multiplier(),godEquity.getTradingsymbol()
                ,godEquity.getCo_upper(),godEquity.getNrml_margin(),godEquity.getMis_margin());
    }

    public static GodCommodity GodModeltoGodCommodity(GodModel godEquity){

        GodCommodity godCommodity =  new GodCommodity(godEquity.getMargin(),godEquity.getCo_lower(),godEquity.getMis_multiplier(),godEquity.getTradingsymbol()
                ,godEquity.getCo_upper(),godEquity.getNrml_margin(),godEquity.getMis_margin());

        godCommodity.setLotsize(godEquity.getLotsize());
        return godCommodity;
    }

    public static List<GodModel> godEquitytoGodModel(List<GodEquity> godEquities){
        List<GodModel> godModels = new ArrayList<>();

        for(GodEquity godEquity : godEquities){
            // God Equity to God Model
            godModels.add(new GodModel(godEquity.getMargin(),godEquity.getCo_lower(),godEquity.getMis_multiplier()
                    ,godEquity.getTradingsymbol(),0.0,godEquity.getCo_upper(),godEquity.getNrml_margin(),godEquity.getMis_margin(),"",""));
        }
        return godModels;
    }
}
