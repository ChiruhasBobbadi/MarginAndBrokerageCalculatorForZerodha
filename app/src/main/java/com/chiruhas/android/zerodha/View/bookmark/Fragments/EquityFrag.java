package com.chiruhas.android.zerodha.View.bookmark.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chiruhas.android.zerodha.CustomAdapters.Equity.RecyclerViewAdapter;
import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodha.HelperClasses.ObjectConverter;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.Model.Equity.RoomModels.GodEquity;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.ViewModel;
import com.chiruhas.android.zerodha.room.equity.EquityViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


@SuppressLint("ValidFragment")
public class EquityFrag extends Fragment {


    EquityViewModel equityViewModel;
    ViewModel viewModel;
    private EquityFragmentListener mListener;
    private int pos;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

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


        equityViewModel = ViewModelProviders.of(this).get(EquityViewModel.class);
        equityViewModel.getAll().observe(this, new Observer<List<GodEquity>>() {
            @Override
            public void onChanged(List<GodEquity> GodModels) {


//                adapter.updateData(ObjectConverter.godEquitytoGodModel(GodModels));
//                adapter.setCache(ObjectConverter.godEquitytoGodModel(GodModels));

            }
        });


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
        adapter = new RecyclerViewAdapter(new RecyclerViewAdapter.ItemListener() {
            @Override
            public void onItemClick(GodModel item) {
                mListener.showPopup(item);
            }

            @Override
            public void onBookmarkClick(GodModel GodModel) {

            }

            @Override
            public void onBookmarkUnClick(GodModel GodModel) {
                mListener.deleteBookmark(GodModel);
            }
        });
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
