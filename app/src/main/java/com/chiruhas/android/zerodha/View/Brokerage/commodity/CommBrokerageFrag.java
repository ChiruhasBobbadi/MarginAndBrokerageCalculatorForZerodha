package com.chiruhas.android.zerodha.View.Brokerage.commodity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.chiruhas.android.zerodha.HelperClasses.BrokerageHelper;
import com.chiruhas.android.zerodha.HelperClasses.NameExtractHelper;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.zerodha.ZerodhaViewModel;

import java.util.ArrayList;
import java.util.List;


public class CommBrokerageFrag extends Fragment {


    int pos;
    String state;
    EditText buy, sell, qty;

    TextView pl;
    ZerodhaViewModel viewModel;
    private AutoCompleteTextView auto;
    private ListView listView;
    private OnFragmentInteractionListener mListener;
    private ImageView clear;


    private Commodity commodity = null;
    private List<Commodity> list = new ArrayList<>();


    CommBrokerageFrag() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_comm_brokerage, container, false);

        listView = view.findViewById(R.id.list);
        final View tview = view;
        buy = view.findViewById(R.id.buy);
        sell = view.findViewById(R.id.sell);
        qty = view.findViewById(R.id.lot);


        Button cal = view.findViewById(R.id.calculate);
        auto = view.findViewById(R.id.auto_text);
        pl = view.findViewById(R.id.pl);
        clear = view.findViewById(R.id.clear);

        viewModel = ViewModelProviders.of(this).get(ZerodhaViewModel.class);


        viewModel.fetchCommodity().observe(getViewLifecycleOwner(), godModels -> {
            list = godModels;
            String lst[] = NameExtractHelper.commodityName(list);

            if (godModels.isEmpty()) {
                Toast.makeText(getContext(), "Requires Internet Connection", Toast.LENGTH_LONG).show();
            }
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lst);

            auto.setAdapter(adapter1);

        });

        auto.setOnItemClickListener((parent, view1, position, id) -> {
            String str = auto.getText().toString().trim();

            for (Commodity c : list) {
                if (c.getScrip().equals(str)) {
                    commodity = c;
                    break;
                }
            }

        });

        clear.setOnClickListener(v -> {
            buy.setText("");
            sell.setText("");
            qty.setText("");
            pl.setText("");
            clear.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
            auto.clearListSelection();

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

                    listView.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);
                    new BrokerageHelper().brokerageCalculate(getContext(), tview, pos, 'c');


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
