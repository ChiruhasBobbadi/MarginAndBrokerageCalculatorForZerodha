package com.chiruhas.android.zerodha.View.Brokerage.currency;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodha.HelperClasses.BrokerageHelper;
import com.chiruhas.android.zerodha.R;


public class CurrencyBrokerageFrag1 extends Fragment {


    private OnFragmentInteractionListener mListener;

    public CurrencyBrokerageFrag1() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CurrencyBrokerageFrag1 newInstance(String param1, String param2) {
        CurrencyBrokerageFrag1 fragment = new CurrencyBrokerageFrag1();

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
        View view= inflater.inflate(R.layout.fragment_currency_brokerage_frag1, container, false);

        final View tview= view;


//        buy = view.findViewById(R.id.buy);
//        sell = view.findViewById(R.id.sell);
//        qty = view.findViewById(R.id.lot);

//        Button cal = view.findViewById(R.id.calculate);
//        cal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(buy.getText().toString().isEmpty() || sell.getText().toString().isEmpty() || qty.getText().toString().isEmpty())
//                    Toast.makeText(getContext(), "Fields can't be empty", Toast.LENGTH_LONG).show();
//                else
//                    new BrokerageHelper().brokerageCalculate(getContext(),tview,pos,'C');
//
//
//            }
//        });
//        AdViewHelper.loadBanner(view);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
