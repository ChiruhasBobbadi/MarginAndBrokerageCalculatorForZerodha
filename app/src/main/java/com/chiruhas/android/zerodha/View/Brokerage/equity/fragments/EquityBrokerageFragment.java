package com.chiruhas.android.zerodha.View.Brokerage.equity.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.chiruhas.android.zerodha.HelperClasses.BrokerageHelper;
import com.chiruhas.android.zerodha.R;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class EquityBrokerageFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private int pos = 0;
    private String state;

    private EditText buy;
    private EditText sell;
    private EditText qty;
    TextView pl;
    private Spinner spinner;
    private ListView listView;
    private ImageView clear;

    private RadioButton nse, def;


    private int stateIndex = 0;

    public EquityBrokerageFragment(int index) {
        // Required empty public constructor
        stateIndex = index;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_equity_brokerage, container, false);

        RadioGroup rg2 = view.findViewById(R.id.rgroup2);
        buy = view.findViewById(R.id.buy);
        sell = view.findViewById(R.id.sell);
        qty = view.findViewById(R.id.lot);
        spinner = view.findViewById(R.id.states);
        EditText per = view.findViewById(R.id.per);
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



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);
        spinner.setSelection(stateIndex);
        spinner.getViewTreeObserver().addOnGlobalLayoutListener(() -> ((TextView) spinner.getSelectedView()).setTextColor(getResources().getColor(R.color.white_grey)));


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //((TextView) parentView.getChildAt(0)).setTextColor(getResources().getColor(R.color.white_grey));
                String s = parentView.getItemAtPosition(position).toString();
                state = s;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
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


        final View vi = view;

        Button cal = view.findViewById(R.id.calculate);
        cal.setOnClickListener(v -> {
            if (buy.getText().toString().isEmpty() || buy.getText().toString().startsWith(".") || sell.getText().toString().isEmpty() || sell.getText().toString().startsWith(".") || qty.getText().toString().isEmpty() || per.getVisibility() == View.VISIBLE && (per.getText().toString().startsWith(".") || per.getText().toString().isEmpty()))

                Toast.makeText(getContext(), "Fields can't be empty", Toast.LENGTH_LONG).show();

            else {
                if (state.isEmpty() || state.equals("Select State"))
                    Toast.makeText(getContext(), "Select State", Toast.LENGTH_SHORT).show();
                else {
                    listView.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);
                    new BrokerageHelper().brokerageCalculate(getContext(), vi, pos, 'e', state);
                }
          }

        });


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

   /* private void initAds(View v) {
        MobileAds.initialize(getContext(), initializationStatus -> { });
        adContainerView = v.findViewById(R.id.ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(getContext());
        adView.setAdUnitId(getResources().getString(R.string.margin_banner));
        adContainerView.addView(adView);
    }

    private void loadBanner() {
        // Create an ad request. Check your logcat output for the hashed device ID
        // to get test ads on a physical device, e.g.,
        // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this
        // device."
        AdRequest adRequest =
                new AdRequest.Builder().build();

        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize);

        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(getContext(), adWidth);
    }*/
}
