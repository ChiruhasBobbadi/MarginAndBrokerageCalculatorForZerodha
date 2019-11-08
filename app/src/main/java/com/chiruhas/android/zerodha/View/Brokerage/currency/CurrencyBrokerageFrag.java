package com.chiruhas.android.zerodha.View.Brokerage.currency;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodha.HelperClasses.BrokerageHelper;
import com.chiruhas.android.zerodha.R;


public class CurrencyBrokerageFrag extends Fragment {

    private OnFragmentInteractionListener mListener;

    int pos;
    String state;
    EditText buy, sell, qty;
    Spinner spinner;

    public CurrencyBrokerageFrag() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CurrencyBrokerageFrag newInstance(String param1, String param2) {
        CurrencyBrokerageFrag fragment = new CurrencyBrokerageFrag();

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
        View view = inflater.inflate(R.layout.fragment_currency_brokerage, container, false);
        final View tview = view;
       // AdViewHelper.loadBanner(view);
        try{

            buy = view.findViewById(R.id.buy);
            sell = view.findViewById(R.id.sell);
            qty = view.findViewById(R.id.lot);
            spinner = view.findViewById(R.id.states);
            Button cal = view.findViewById(R.id.calculate);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.states, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            spinner.setAdapter(adapter);
            spinner.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    ((TextView) spinner.getSelectedView()).setTextColor(getResources().getColor(R.color.white_grey));
                }
            });


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


            cal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buy.getText().toString().isEmpty() || sell.getText().toString().isEmpty() || qty.getText().toString().isEmpty() || buy.getText().toString().startsWith(".") || sell.getText().toString().startsWith("."))
                        Toast.makeText(getContext(), "Fields can't be empty", Toast.LENGTH_LONG).show();
                    else {
                        if (state.isEmpty() || state.equals("Select State"))
                            Toast.makeText(getContext(), "Select State", Toast.LENGTH_SHORT).show();
                        else
                            new BrokerageHelper().brokerageCalculate(getContext(), tview, pos, 'C', state);

                    }


                }
            });


        }
        catch (Exception e){
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
