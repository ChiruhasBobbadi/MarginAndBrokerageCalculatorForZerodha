package com.chiruhas.android.zerodha.View.Brokerage.commodity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.chiruhas.android.zerodha.HelperClasses.BrokerageHelper;
import com.chiruhas.android.zerodha.HelperClasses.NameExtractHelper;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.Repo.zerodha.ZerodhaViewModel;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class CommBrokerageFrag extends Fragment {


    int pos;
    String state;
    EditText buy, sell, qty;
    Spinner spinner;
    ZerodhaViewModel viewModel;
    private AutoCompleteTextView auto;
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

        //AdViewHelper.loadBanner(view);
        final View tview = view;
        buy = view.findViewById(R.id.buy);
        sell = view.findViewById(R.id.sell);
        qty = view.findViewById(R.id.lot);
        spinner = view.findViewById(R.id.states);
        Button cal = view.findViewById(R.id.calculate);
        auto = view.findViewById(R.id.auto_text);

        viewModel = ViewModelProviders.of(this).get(ZerodhaViewModel.class);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        viewModel.fetchCommodity().observe(this, godModels -> {
            list = godModels;
            String lst[] = NameExtractHelper.commodityName(list);

            if (godModels.isEmpty()) {
                Toast.makeText(getContext(), "Requires Internet Connection", Toast.LENGTH_LONG).show();
            }
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lst);

            auto.setAdapter(adapter1);
            Log.d(TAG, "onChanged: Sucess");
        });

        auto.setOnItemClickListener((parent, view1, position, id) -> {


            String str = auto.getText().toString().trim();

            for (Commodity c : list) {
                if (c.getScrip().equals(str)) {
                    commodity = c;
                    break;
                }
            }


//

        });


        spinner.setAdapter(adapter);
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


        cal.setOnClickListener(v -> {

            try {

                if (buy.getText().toString().isEmpty() || sell.getText().toString().isEmpty() || qty.getText().toString().isEmpty() || buy.getText().toString().startsWith(".") || sell.getText().toString().startsWith(".") || list.isEmpty()) {
                    if (list.isEmpty())
                        Toast.makeText(getContext(), "Requires Internet Connection..", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(), "Fields can't be empty", Toast.LENGTH_LONG).show();
                } else if (commodity == null) {
                    Toast.makeText(getContext(), "Invalid Symbol", Toast.LENGTH_SHORT).show();
                } else {
                    if (state.isEmpty() || state.equals("Select State"))
                        Toast.makeText(getContext(), "Select State", Toast.LENGTH_SHORT).show();
                    else {

                        new BrokerageHelper().brokerageCalculate(getContext(), tview, pos, 'c', state);
                    }

                }

            } catch (Exception e) {
                Toast.makeText(getContext(), "Oops Something happened..", Toast.LENGTH_SHORT).show();
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
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
