package com.chiruhas.android.zerodha.View.Brokerage.commodity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.ViewModel;

import java.util.ArrayList;
import java.util.List;


public class CommBrokerageFrag extends Fragment {

    private OnFragmentInteractionListener mListener;

    String lotsize[] = {"5 MT", "1 MT","1 MT", "5 MT", "1 MT", "100 KGS", "110 MT", "1 MT", "250 KGS", "25 BALES", "10 MT", "100 BBL", "10 BBL", "1 KGS",
            "8 GRMS", "100 GRMS", "1 GRMS", "5 MT", "1 MT", "360 KGS", "1250 MMBTU", "250 KGS", "100 KGS", "1 MT", "10 MT"
            ,"30 KGS", "5 KGS", "1 KGS"};

    Spinner spinner;
    ViewModel viewModel;

    List<GodModel> list = new ArrayList<>();
    List<String> names = new ArrayList<>();

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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_comm_brokerage, container, false);

        spinner = view.findViewById(R.id.spinner);
        final View tview=view;

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        viewModel.fetchCommodity().observe(getViewLifecycleOwner(), new Observer<List<GodModel>>() {
            @Override
            public void onChanged(List<GodModel> godModels) {

                for (int i = 0; i < godModels.size(); i++) {
                    godModels.get(i).setLotsize(lotsize[i]);
                }
                list = godModels;
            }
        });

        for(GodModel g: list){
            names.add(g.getTradingsymbol());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,names);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // list contains all commodities and lot sizes;


        /**
         * inputs required
         * 1. spinner selection
         * 2.buy
         * 3.sell
         */



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
