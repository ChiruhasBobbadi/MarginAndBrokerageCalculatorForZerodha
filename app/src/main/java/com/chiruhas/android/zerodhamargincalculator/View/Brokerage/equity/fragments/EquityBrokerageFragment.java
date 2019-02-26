package com.chiruhas.android.zerodhamargincalculator.View.Brokerage.equity.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chiruhas.android.zerodhamargincalculator.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodhamargincalculator.HelperClasses.BrokerageHelper;
import com.chiruhas.android.zerodhamargincalculator.R;

import java.util.ArrayList;
import java.util.Arrays;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class EquityBrokerageFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
     int pos=0;

    EditText buy,sell,qty;
    public EquityBrokerageFragment() {
        // Required empty public constructor

    }


    // TODO: Rename and change types and number of parameters
    public  static EquityBrokerageFragment newInstance() {
        EquityBrokerageFragment fragment = new EquityBrokerageFragment();

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
       View view = inflater.inflate(R.layout.fragment_equity_brokerage, container, false);

        buy = view.findViewById(R.id.buy);
        sell = view.findViewById(R.id.sell);
        qty = view.findViewById(R.id.lot);



      final View vi = view;

      Button cal = view.findViewById(R.id.calculate);
      cal.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(buy.getText().toString().isEmpty() || sell.getText().toString().isEmpty() || qty.getText().toString().isEmpty())
                  Toast.makeText(getContext(), "Fields can't be empty", Toast.LENGTH_LONG).show();

              else
                  new BrokerageHelper().brokerageCalculate(getContext(),vi,pos,'e');
          }
      });
        AdViewHelper.loadBanner(view);

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
       

    }
    public void updatePos(int pos){
        this.pos=pos ;
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mListener = null;

        Log.d(TAG, "onDestroyView: Brokerage Fragment"+pos);
        
    }
}
