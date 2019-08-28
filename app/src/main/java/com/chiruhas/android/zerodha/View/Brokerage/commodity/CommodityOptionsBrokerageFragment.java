package com.chiruhas.android.zerodha.View.Brokerage.commodity;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chiruhas.android.zerodha.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommodityOptionsBrokerageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommodityOptionsBrokerageFragment extends Fragment {



    public CommodityOptionsBrokerageFragment() {
        // Required empty public constructor
    }


    public static CommodityOptionsBrokerageFragment newInstance(String param1, String param2) {
        CommodityOptionsBrokerageFragment fragment = new CommodityOptionsBrokerageFragment();

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
        return inflater.inflate(R.layout.fragment_commodity_options_brokerage, container, false);
    }

}
