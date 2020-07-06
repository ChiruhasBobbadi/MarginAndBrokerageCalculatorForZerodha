package com.chiruhas.android.zerodha.View.Brokerage.currency;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.chiruhas.android.zerodha.HelperClasses.BrokerageHelper;
import com.chiruhas.android.zerodha.R;


public class CurrencyBrokerageFrag extends Fragment {

    private OnFragmentInteractionListener mListener;

    int pos;

    EditText buy, sell, qty;

    private ListView listView;
    private int stateIndex = 0;
    TextView pl;
    private ImageView clear;

    CurrencyBrokerageFrag(int index) {
        stateIndex = index;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_currency_brokerage, container, false);
        final View tview = view;
        // AdViewHelper.loadBanner(view);
        try {
            listView = view.findViewById(R.id.list);
            buy = view.findViewById(R.id.buy);
            sell = view.findViewById(R.id.sell);
            qty = view.findViewById(R.id.lot);

            Button cal = view.findViewById(R.id.calculate);
            pl = view.findViewById(R.id.pl);
            clear = view.findViewById(R.id.clear);



            clear.setOnClickListener(v -> {
                buy.setText("");
                sell.setText("");
                qty.setText("");
                pl.setText("");
                clear.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);

            });


            cal.setOnClickListener(v -> {

                if (buy.getText().toString().isEmpty() || sell.getText().toString().isEmpty() || qty.getText().toString().isEmpty() || buy.getText().toString().startsWith(".") || sell.getText().toString().startsWith("."))
                    Toast.makeText(getContext(), "Fields can't be empty", Toast.LENGTH_LONG).show();
                else {


                    listView.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);
                    new BrokerageHelper().brokerageCalculate(getContext(), tview, pos, 'C');


                }


            });


        } catch (Exception e) {
            Toast.makeText(getContext(), "Oops Something Happened", Toast.LENGTH_SHORT).show();
        }

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

    public void updatePos(int pos) {
        this.pos = pos;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
