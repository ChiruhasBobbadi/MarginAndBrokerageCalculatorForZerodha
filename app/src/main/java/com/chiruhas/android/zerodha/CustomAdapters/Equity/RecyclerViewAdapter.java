
package com.chiruhas.android.zerodha.CustomAdapters.Equity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<GodModel> GodModels = new ArrayList<>();
    private ItemListener myListener;

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

    public List<GodModel> getData() {
        return GodModels;
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
        if (model.getNrml_multiplier() == 0)
            model.setNrml_multiplier(1);
        holder.cnc.setText("CNC Multiplier : " + model.getNrml_multiplier() + "X");

        holder.cal.setOnClickListener(view -> myListener.onItemClick(model));




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
                                