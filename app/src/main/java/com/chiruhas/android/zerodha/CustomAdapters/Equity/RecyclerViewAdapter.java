
package com.chiruhas.android.zerodha.CustomAdapters.Equity;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiruhas.android.zerodha.HelperClasses.BookmarkHelper;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<GodModel> GodModels = new ArrayList<>();
    private ItemListener myListener;
    List<GodModel> equitycache = new ArrayList<>();
    public RecyclerViewAdapter( ItemListener listener) {

        myListener = listener;
    }

    public void setListener(ItemListener listener) {
        myListener = listener;
    }

    public void updateData(List<GodModel> GodModels)
    {
        this.GodModels= GodModels;
        notifyDataSetChanged();
    }

    public void setCache(List<GodModel> equity){
        equitycache=equity;
       // notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.equity_card,parent, false)); // TODO
    }

    @Override
    public int getItemCount() {
        return GodModels.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ViewHolder h=holder;
      final GodModel model = GodModels.get(position);

         // bookmark helper class
        //BookmarkHelper.checkBookmark(equitycache,model,holder.bookmark);

        holder.scrip.setText(model.getTradingsymbol());
        holder.mis.setText("MIS Multiplier : "+model.getMis_multiplier()+"X");
        holder.cnc.setText("CNC Multiplier : 1X");

        holder.cal.setOnClickListener(view -> myListener.onItemClick(model));


        //cahnging the image of star based on conditions
//        holder.bookmark.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if((int)h.bookmark.getTag() == R.drawable.ic_star_border){
//                    h.bookmark.setBackgroundResource(R.drawable.ic_star);
//                    h.bookmark.setTag(R.drawable.ic_star);
//                    myListener.onBookmarkClick(model);
//                }
//                else{
//                    h.bookmark.setBackgroundResource(R.drawable.ic_star_border);
//                    h.bookmark.setTag(R.drawable.ic_star_border);
//                    myListener.onBookmarkUnClick(model);
//                }
//            }
//        });

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
        void onBookmarkClick(GodModel GodModel);
        void onBookmarkUnClick(GodModel GodModel);
    }

     class ViewHolder extends RecyclerView.ViewHolder {

        TextView scrip,mis,cnc;
        Button cal;
        CardView card;
      //public  ImageView bookmark;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            scrip = itemView.findViewById(R.id.scrip);
            mis=itemView.findViewById(R.id.mis_mul);
            cnc = itemView.findViewById(R.id.cnc_mul);
            cal=itemView.findViewById(R.id.cal);
            card =itemView.findViewById(R.id.card);
            //bookmark = itemView.findViewById(R.id.bookmark);
            //bookmark.setTag(R.drawable.ic_star_border);
        }
    }


}
                                