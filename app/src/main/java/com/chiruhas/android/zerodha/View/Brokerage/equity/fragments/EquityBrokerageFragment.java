package com.chiruhas.android.zerodha.View.Brokerage.equity.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.chiruhas.android.zerodha.HelperClasses.BrokerageHelper;
import com.chiruhas.android.zerodha.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class EquityBrokerageFragment extends Fragment {

    TextView pl;
    private OnFragmentInteractionListener mListener;
    private int pos = 0;
    private String state;
    private EditText buy;
    private EditText sell;
    private EditText qty;
    private ListView listView;
    private ImageView clear;
    private InterstitialAd brokerage;
    private RadioButton nse, def;
    private View vi;
    private EditText per;

    public EquityBrokerageFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAds();
    }

    private void initAds() {
        brokerage = new InterstitialAd(getContext());
        brokerage.setAdUnitId(getResources().getString(R.string.brokerage_inter));

        brokerage.loadAd(new AdRequest.Builder().build());
        brokerage.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                brokerage.loadAd(new AdRequest.Builder().build());
                showResult();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_equity_brokerage, container, false);

        RadioGroup rg2 = view.findViewById(R.id.rgroup2);
        buy = view.findViewById(R.id.buy);
        sell = view.findViewById(R.id.sell);
        qty = view.findViewById(R.id.lot);

        per = view.findViewById(R.id.per);
        listView = view.findViewById(R.id.list);
        clear = view.findViewById(R.id.clear);
        pl = view.findViewById(R.id.pl);
        nse = view.findViewById(R.id.nse);
        def = view.findViewById(R.id.def);

        rg2.setOnCheckedChangeListener((group, checkedId) -> {

            switch (checkedId) {
                case R.id.def:
                    per.setVisibility(View.GONE);
                    break;
                case R.id.custom:
                    per.setVisibility(View.VISIBLE);
                    break;
            }
        });


        clear.setOnClickListener(v -> {
            buy.setText("");
            sell.setText("");
            qty.setText("");
            pl.setText("");
            clear.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
            nse.setChecked(true);
            def.setChecked(true);
            per.setText("");
            per.setVisibility(View.GONE);

        });


        vi = view;

        Button cal = view.findViewById(R.id.calculate);
        cal.setOnClickListener(v -> {


            showResult();

        });


        return view;

    }

    private void showResult() {
        if (buy.getText().toString().isEmpty() || buy.getText().toString().startsWith(".") || sell.getText().toString().isEmpty() || sell.getText().toString().startsWith(".") || qty.getText().toString().isEmpty() || per.getVisibility() == View.VISIBLE && (per.getText().toString().startsWith(".") || per.getText().toString().isEmpty()))

            Toast.makeText(getContext(), "Fields can't be empty", Toast.LENGTH_LONG).show();

        else {
            if (brokerage.isLoaded())
                brokerage.show();
            else {
                listView.setVisibility(View.VISIBLE);
                clear.setVisibility(View.VISIBLE);
                new BrokerageHelper().brokerageCalculate(getContext(), vi, pos, 'e');
            }

        }
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

    public void updatePos(int pos) {
        this.pos = pos;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mListener = null;

        Log.d(TAG, "onDestroyView: Brokerage Fragment" + pos);

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
