package com.chiruhas.android.zerodhamargincalculator.View.Brokerage;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chiruhas.android.zerodhamargincalculator.R;


public class BrokerageFragment extends Fragment  {


    private OnFragmentInteractionListener mListener;

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
        CardView equity = v.findViewById(R.id.equity);
        equity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBrokerageFragment(v);
            }
        });

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
