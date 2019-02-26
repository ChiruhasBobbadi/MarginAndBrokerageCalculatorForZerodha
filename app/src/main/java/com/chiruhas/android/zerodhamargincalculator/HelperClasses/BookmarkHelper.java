package com.chiruhas.android.zerodhamargincalculator.HelperClasses;

import android.util.Log;
import android.widget.ImageView;


import com.chiruhas.android.zerodhamargincalculator.Model.Equity.GodModel;
import com.chiruhas.android.zerodhamargincalculator.Model.Equity.RoomModels.GodEquity;
import com.chiruhas.android.zerodhamargincalculator.R;

import java.util.List;



import static com.airbnb.lottie.L.TAG;

public class BookmarkHelper {

    public static void checkBookmark(List<GodModel> equitycache, GodModel model , ImageView bookmark){

        if(!equitycache.isEmpty()){
            Log.d(TAG, "onBindViewHolder: check");

            boolean flag=false;
            for(GodModel  godEquity : equitycache){
                if(godEquity.getTradingsymbol().equals(model.getTradingsymbol())){
                    bookmark.setBackgroundResource(R.drawable.ic_star);
                    bookmark.setTag(R.drawable.ic_star);
                    flag=true;
                    break;
                }
            }
            if(!flag){
                bookmark.setBackgroundResource(R.drawable.ic_star_border);
                bookmark.setTag(R.drawable.ic_star_border);
            }
        }
    }


}
