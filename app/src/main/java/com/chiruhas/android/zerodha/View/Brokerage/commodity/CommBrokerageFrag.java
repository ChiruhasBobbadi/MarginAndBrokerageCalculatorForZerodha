package com.chiruhas.android.zerodha.View.Brokerage.commodity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodha.HelperClasses.BrokerageHelper;
import com.chiruhas.android.zerodha.HelperClasses.NameExtractHelper;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class CommBrokerageFrag extends Fragment {

    String lotsize[] = {"5 MT", "1 MT", "1 MT", "5 MT", "1 MT", "100 KGS", "110 MT", "1 MT", "250 KGS", "25 BALES", "10 MT", "100 BBL", "10 BBL", "1 KGS",
            "8 GRMS", "100 GRMS", "1 GRMS", "5 MT", "1 MT", "360 KGS", "1250 MMBTU", "250 KGS", "100 KGS", "1 MT", "10 MT"
            , "30 KGS", "5 KGS", "1 KGS"};
    int pos;
    String state;
    EditText buy, sell, qty;
    Spinner spinner;
    private AutoCompleteTextView auto;
    ViewModel viewModel;
    private OnFragmentInteractionListener mListener;


    private Commodity commodity = null;
    private List<Commodity> list = new ArrayList<>();

    public CommBrokerageFrag() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CommBrokerageFrag newInstance(String param1, String param2) {
        CommBrokerageFrag fragment = new CommBrokerageFrag();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_comm_brokerage, container, false);
        try {
            AdViewHelper.loadBanner(view);
            final View tview = view;
            buy = view.findViewById(R.id.buy);
            sell = view.findViewById(R.id.sell);
            qty = view.findViewById(R.id.lot);
            spinner = view.findViewById(R.id.states);
            Button cal = view.findViewById(R.id.calculate);
            auto = view.findViewById(R.id.auto_text);

            viewModel = ViewModelProviders.of(this).get(ViewModel.class);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.states, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            viewModel.fetchCommodity().observe(this, new Observer<List<Commodity>>() {
                @Override
                public void onChanged(List<Commodity> godModels) {
                    list = godModels;
                    String lst[] = NameExtractHelper.commodityName(list);

                    if (godModels.isEmpty()) {
                        Toast.makeText(getContext(), "Requires Internet Connection", Toast.LENGTH_LONG).show();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lst);

                    auto.setAdapter(adapter);
                    Log.d(TAG, "onChanged: Sucess");
                }
            });

            auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    String str = auto.getText().toString().trim();

                    for (Commodity c : list) {
                        if (c.getScrip().equals(str)) {
                            commodity = c;
                            break;
                        }
                    }


//

                }
            });


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


                    if (buy.getText().toString().isEmpty() || sell.getText().toString().isEmpty() || qty.getText().toString().isEmpty() || buy.getText().toString().startsWith(".") || sell.getText().toString().startsWith(".") || list.isEmpty() || commodity == null) {
                        if (list.isEmpty())
                            Toast.makeText(getContext(), "Requires Internet Connection..", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getContext(), "Fields can't be empty", Toast.LENGTH_LONG).show();
                    } else {
                        if (state.isEmpty() || state.equals("Select State"))
                            Toast.makeText(getContext(), "Select State", Toast.LENGTH_SHORT).show();
                        else {

                            new BrokerageHelper().brokerageCalculate(getContext(), tview, pos, 'c', state);
                        }

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
