package com.chiruhas.android.zerodha.HelperClasses;

import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.Model.Equity.RoomModels.GodCommodity;
import com.chiruhas.android.zerodha.Model.Equity.RoomModels.GodEquity;

import java.util.ArrayList;
import java.util.List;

public class ObjectConverter {
    public static GodModel GodEquitytoGodModel(GodEquity godEquity){

        return new GodModel(godEquity.getMargin(),godEquity.getCo_lower(),godEquity.getMis_margin(),godEquity.getTradingsymbol()
        ,godEquity.getCo_upper(),godEquity.getNrml_margin(),godEquity.getMis_margin());
    }

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
    public static List<GodModel> godCommtoGodModel(List<GodCommodity> godCommodities){

        List<GodModel> godModels=new ArrayList<>();

        for(GodCommodity godEquity : godCommodities){

           GodModel godModel =  new GodModel(godEquity.getMargin(),godEquity.getCo_lower(),godEquity.getMis_multiplier()
                    ,godEquity.getTradingsymbol(),godEquity.getCo_upper(),godEquity.getNrml_margin(),godEquity.getMis_margin());

            godModel.setLotsize(godEquity.getLotsize());
            godModels.add(godModel);

        }
        return godModels;
    }

    public static List<GodModel> godEquitytoGodModel(List<GodEquity> godEquities){
        List<GodModel> godModels = new ArrayList<>();

        for(GodEquity godEquity : godEquities){
            // God Equity to God Model
            godModels.add(new GodModel(godEquity.getMargin(),godEquity.getCo_lower(),godEquity.getMis_multiplier()
                    ,godEquity.getTradingsymbol(),godEquity.getCo_upper(),godEquity.getNrml_margin(),godEquity.getMis_margin()));
        }
        return godModels;
    }


}
