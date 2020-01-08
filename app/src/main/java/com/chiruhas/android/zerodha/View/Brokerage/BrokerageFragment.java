package com.chiruhas.android.zerodha.View.Brokerage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.chiruhas.android.zerodha.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


public class
BrokerageFragment extends Fragment  {


    private OnFragmentInteractionListener mListener;
    private CardView equity, commodity, currency;
    public BrokerageFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BrokerageFragment newInstance() {
        BrokerageFragment fragment = new BrokerageFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_brokerage, container, false);

        equity = v.findViewById(R.id.equity);
        commodity = v.findViewById(R.id.commodity);
        currency = v.findViewById(R.id.currency);
        equity.setOnClickListener(v1 -> mListener.onBrokerageFragment(v1));

        YoYo.with(Techniques.FadeIn)
                .duration(1200)
                .repeat(0)
                .playOn(equity);
        YoYo.with(Techniques.FadeIn)
                .duration(1200)
                .repeat(0)
                .playOn(commodity);
        YoYo.with(Techniques.FadeIn)
                .duration(1200)
                .repeat(0)
                .playOn(currency);




        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(View v) {
        if (mListener != null) {
            mListener.onBrokerageFragment(v);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onclick(View v){
        mListener.onBrokerageFragment(v);
    }






    public interface OnFragmentInteractionListener {

        void onBrokerageFragment(View v);
    }
}
