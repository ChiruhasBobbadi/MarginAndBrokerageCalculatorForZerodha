
package com.chiruhas.android.zerodhamargincalculator.CustomAdapters.Equity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chiruhas.android.zerodhamargincalculator.HelperClasses.BookmarkHelper;
import com.chiruhas.android.zerodhamargincalculator.Model.Equity.GodModel;


import com.chiruhas.android.zerodhamargincalculator.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class CommodityAdapter extends RecyclerView.Adapter<CommodityAdapter.ViewHolder> {

    Context context;
    private List<GodModel> GodModels = new ArrayList<>();
   // private List<GodCommodity> bookmarks = new ArrayList<>();
    private ItemListener myListener;
    private List<GodModel> cacheList = new ArrayList<>();
    public CommodityAdapter(ItemListener listener, Context context) {

        myListener = listener;
        this.context = context;
    }

    public void setListener(ItemListener listener) {
        myListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commodity_card, parent, false)); // TODO
    }

    public void updateData(List<GodModel> list) {
        GodModels = list;
        notifyDataSetChanged();
    }

    public void setCache(List<GodModel> cache) {
    cacheList=cache;
    }

    @Override
    public int getItemCount() {
        return GodModels.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ViewHolder h = holder;
        final GodModel GodModel = GodModels.get(position);
        holder.scrip.setText(GodModel.getTradingsymbol());
        holder.mis.setText("MIS Margin : "+GodModel.getMis_margin());
        holder.cnc.setText("NRML Magin : "+GodModel.getNrml_margin());
        holder.lot.setText("Lot size : "+GodModel.getLotsize());
        holder.cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Coming soon...", Toast.LENGTH_SHORT).show();
                //myListener.onItemClick(GodModel);
            }
        });

        BookmarkHelper.checkBookmark(cacheList,GodModel,holder.bookmark);

        //cahnging the image of star based on conditions
        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((int)h.bookmark.getTag() == R.drawable.ic_star_border){
                    h.bookmark.setBackgroundResource(R.drawable.ic_star);
                    h.bookmark.setTag(R.drawable.ic_star);
                    myListener.onBookmarkClick(GodModel);
                }
                else{
                    h.bookmark.setBackgroundResource(R.drawable.ic_star_border);
                    h.bookmark.setTag(R.drawable.ic_star_border);
                    myListener.onBookmarkUnClick(GodModel);
                }
            }
        });

        YoYo.with(Techniques.FadeIn)
                .duration(1200)
                .repeat(0)
                .playOn(holder.card);
        YoYo.with(Techniques.FadeIn)
                .duration(1200)
                .repeat(0)
                .playOn(holder.cal);

    }

    public interface ItemListener {
        void onItemClick(GodModel item);
        void onBookmarkClick(GodModel model);
        void onBookmarkUnClick(GodModel model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // TODO - Your view members
        TextView scrip, mis, cnc,lot;
        Button cal;
        CardView card;
        public ImageView bookmark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            scrip = itemView.findViewById(R.id.scrip);
            mis = itemView.findViewById(R.id.mis_mul);
            cnc = itemView.findViewById(R.id.cnc_mul);
            cal = itemView.findViewById(R.id.cal);
            card = itemView.findViewById(R.id.card);
            bookmark = itemView.findViewById(R.id.bookmark);
            bookmark.setTag(R.drawable.ic_star_border);
            lot = itemView.findViewById(R.id.lot);
        }
    }


}
                                