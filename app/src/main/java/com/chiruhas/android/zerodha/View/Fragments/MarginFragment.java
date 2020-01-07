package com.chiruhas.android.zerodha.View.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chiruhas.android.zerodha.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


public class MarginFragment extends Fragment  {


    private OnFragmentInteractionListener mListener;
CardView card,card2,card3,card4,card5;
    public MarginFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MarginFragment newInstance() {
        MarginFragment fragment = new MarginFragment();

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
        View v = inflater.inflate(R.layout.fragment_margin, container, false);

        card = v.findViewById(R.id.equity);
        card2 =  v.findViewById(R.id.commodity);
        card3 = v.findViewById(R.id.futures);
        card4= v.findViewById(R.id.currency);
        card5= v.findViewById(R.id.bracket);
        YoYo.with(Techniques.FadeIn)
                .duration(1200)
                .repeat(0)
                .playOn(card);
        YoYo.with(Techniques.FadeIn)
                .duration(1200)
                .repeat(0)
                .playOn(card2);
        YoYo.with(Techniques.FadeIn)
                .duration(1200)
                .repeat(0)
                .playOn(card3);
        YoYo.with(Techniques.FadeIn)
                .duration(1200)
                .repeat(0)
                .playOn(card4);
        YoYo.with(Techniques.FadeIn)
                .duration(1200)
                .repeat(0)
                .playOn(card5);



        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(View v) {
        if (mListener != null) {
            mListener.onMarginFragment(v);
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




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
         void onMarginFragment(View v);
    }
}
