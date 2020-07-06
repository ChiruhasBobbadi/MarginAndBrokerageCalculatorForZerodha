package com.chiruhas.android.zerodha.CustomAdapters.Equity;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;


public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    private List<Currency> myItems = new ArrayList<>();
    private ItemListener myListener;

    public CurrencyAdapter(ItemListener listener) {

        myListener = listener;
    }

    public void updateData(List<Currency> GodModels) {
        myItems = GodModels;
        notifyDataSetChanged();
    }

    public List<Currency> getData() {
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


        Currency Currency = myItems.get(position);

        holder.mis.setText("MIS : " + Currency.getMis());
        holder.cnc.setText("NRML : " + Currency.getNrml());
        holder.lot.setText("Lot size : " + Currency.getLot());
        holder.price.setText("Price : " + Currency.getPrice());
        holder.scrip.setText(Currency.getScrip());
        holder.expiry.setText(Currency.getExpiry());
        holder.cal.setOnClickListener(view -> myListener.onItemClick(Currency));
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
        void onItemClick(Currency item);
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
                                