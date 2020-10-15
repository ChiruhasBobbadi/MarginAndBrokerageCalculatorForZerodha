package com.chiruhas.android.zerodha.View.bookmark.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chiruhas.android.zerodha.CustomAdapters.Equity.RecyclerViewAdapter;
import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.zerodha.ZerodhaViewModel;


@SuppressLint("ValidFragment")
public class EquityFrag extends Fragment {


    ZerodhaViewModel viewModel;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    private EquityFragmentListener mListener;
    private int pos;

    public EquityFrag() {
        // Required empty public constructor

    }


    // TODO: Rename and change types and number of parameters
    public static EquityFrag newInstance(String param1, String param2) {
        EquityFrag fragment = new EquityFrag();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_equity, container, false);


        // adview
        AdViewHelper.loadBanner(view);

        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(item -> mListener.showPopup(item));
        recyclerView.setAdapter(adapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void showPopupEquity(GodModel GodModel) {
        if (mListener != null) {
            mListener.showPopup(GodModel);
        }
    }

    public void deleteEqutiyMargin(GodModel GodModel) {
        if (mListener != null) {
            mListener.deleteBookmark(GodModel);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EquityFragmentListener) {
            mListener = (EquityFragmentListener) context;
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


    public interface EquityFragmentListener {
        // TODO: Update argument type and name
        void showPopup(GodModel GodModel);

        void deleteBookmark(GodModel GodModel);
    }
}
