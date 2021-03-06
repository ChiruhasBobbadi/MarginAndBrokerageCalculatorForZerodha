package com.chiruhas.android.zerodha.CustomAdapters.Equity;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;


public class FutureAdapter extends RecyclerView.Adapter<FutureAdapter.ViewHolder> {

    private List<Futures> myItems = new ArrayList<>();
    private ItemListener myListener;

    public FutureAdapter(ItemListener listener) {

        myListener = listener;
    }

    public void updateData(List<Futures> GodModels) {
        myItems = GodModels;
        notifyDataSetChanged();
    }

    public List<Futures> getData() {
        return myItems;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.future_card, parent, false)); // TODO
    }

    @Override
    public int getItemCount() {
        return myItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Futures Futures = myItems.get(position);

        holder.mis.setText("MIS : " + Futures.getMis());
        holder.cnc.setText("NRML : " + Futures.getNrml());
        holder.lot.setText("Lot size : " + Futures.getLot());
        holder.price.setText("Price : " + Futures.getPrice());
        holder.scrip.setText(Futures.getScrip());
        holder.expiry.setText(Futures.getExpiry());
        holder.cal.setOnClickListener(view -> myListener.onItemClick(Futures));
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
        void onItemClick(Futures item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView scrip, mis, cnc, lot, price, expiry;
        Button cal;
        CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            scrip = itemView.findViewById(R.id.scrip);
            mis = itemView.findViewById(R.id.mis_mul);
            cnc = itemView.findViewById(R.id.cnc_mul);
            cal = itemView.findViewById(R.id.cal);
            card = itemView.findViewById(R.id.card);
            price = itemView.findViewById(R.id.price);
            lot = itemView.findViewById(R.id.lot);
            expiry = itemView.findViewById(R.id.ex);
        }


    }


}
                                