
package com.chiruhas.android.zerodha.CustomAdapters.Equity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chiruhas.android.zerodha.HelperClasses.BookmarkHelper;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;


import com.chiruhas.android.zerodha.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class CommodityAdapter extends RecyclerView.Adapter<CommodityAdapter.ViewHolder> {

    Context context;
    private List<Commodity> Commoditys = new ArrayList<>();
   // private List<GodCommodity> bookmarks = new ArrayList<>();
    private ItemListener myListener;
    private List<Commodity> cacheList = new ArrayList<>();
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

    public void updateData(List<Commodity> list) {
        Commoditys = list;
        notifyDataSetChanged();
    }

    public void setCache(List<Commodity> cache) {
    cacheList=cache;
    }

    @Override
    public int getItemCount() {
        return Commoditys.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ViewHolder h = holder;
        final Commodity Commodity = Commoditys.get(position);
        holder.scrip.setText(Commodity.getScrip());
        holder.mis.setText("MIS Margin : "+Commodity.getMis());
        holder.cnc.setText("NRML Magin : "+Commodity.getNrml());
        holder.lot.setText("Lot size : "+Commodity.getLot());
        holder.price.setText("Price : "+Commodity.getPrice());
        holder.cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Coming soon...", Toast.LENGTH_SHORT).show();
                myListener.onItemClick(Commodity);
            }
        });

        //BookmarkHelper.checkBookmark(cacheList,Commodity,holder.bookmark);

        //cahnging the image of star based on conditions
        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((int)h.bookmark.getTag() == R.drawable.ic_star_border){
                    h.bookmark.setBackgroundResource(R.drawable.ic_star);
                    h.bookmark.setTag(R.drawable.ic_star);
                    myListener.onBookmarkClick(Commodity);
                }
                else{
                    h.bookmark.setBackgroundResource(R.drawable.ic_star_border);
                    h.bookmark.setTag(R.drawable.ic_star_border);
                    myListener.onBookmarkUnClick(Commodity);
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
        void onItemClick(Commodity item);
        void onBookmarkClick(Commodity model);
        void onBookmarkUnClick(Commodity model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // TODO - Your view members
        TextView scrip, mis, cnc,lot,price;
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
            price = itemView.findViewById(R.id.price);
            bookmark = itemView.findViewById(R.id.bookmark);
            bookmark.setTag(R.drawable.ic_star_border);
            lot = itemView.findViewById(R.id.lot);
        }
    }


}
                                