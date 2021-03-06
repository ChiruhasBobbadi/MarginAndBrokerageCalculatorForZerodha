//package com.chiruhas.android.zerodha.View.bookmark.Fragments;
//
//import android.content.Context;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.chiruhas.android.zerodha.CustomAdapters.Equity.CommodityAdapter;
//import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
//import com.chiruhas.android.zerodha.HelperClasses.ObjectConverter;
//import com.chiruhas.android.zerodha.Model.Equity.Commodity;
//import com.chiruhas.android.zerodha.Model.Equity.GodModel;
//import com.chiruhas.android.zerodha.Model.Equity.RoomModels.GodCommodity;
//import com.chiruhas.android.zerodha.R;
////import com.chiruhas.android.zerodha.room.Commodity.CommodityViewModel;
//
//import java.util.List;
//
//
//public class CommodityFrag extends Fragment {
//
//
//
//    private OnFragmentInteractionListener mListener;
//    CommodityViewModel commodityViewModel;
//    CommodityAdapter commodityAdapter;
//    RecyclerView recyclerView;
//    public CommodityFrag() {
//        // Required empty public constructor
//    }
//
//
//    // TODO: Rename and change types and number of parameters
//    public static CommodityFrag newInstance() {
//        CommodityFrag fragment = new CommodityFrag();
//        Bundle args = new Bundle();
//
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        commodityAdapter = new CommodityAdapter(new CommodityAdapter.ItemListener() {
//
//            @Override
//            public void onItemClick(Commodity item) {
//                showCommodityPopup(item);
//            }
//
////            @Override
////            public void onBookmarkClick(Commodity model) {
////
////            }
////
////            @Override
////            public void onBookmarkUnClick(Commodity model) {
////                mListener.deleteBookmarkCommodity(model);
////            }
//        },getContext());
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view= inflater.inflate(R.layout.fragment_commodity, container, false);
//
//
//        commodityViewModel = ViewModelProviders.of(this).get(CommodityViewModel.class);
//        commodityViewModel.getAll().observe(this, new Observer<List<Commodity>>() {
//            @Override
//            public void onChanged(List<Commodity> godCommodities) {
//                commodityAdapter.updateData(godCommodities);
//               // commodityAdapter.setCache(godCommodities);
//
//            }
//        });
//
//
//        // adview
//        AdViewHelper.loadBanner(view);
//
//        recyclerView = view.findViewById(R.id.rv);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setHasFixedSize(true);
//
//        recyclerView.setAdapter(commodityAdapter);
//        return view;
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void showCommodityPopup(Commodity GodModel) {
//        if (mListener != null) {
//            mListener.showCommodityPopup(GodModel);
//        }
//    }
//
//    public void delete(Commodity GodModel) {
//        if (mListener != null) {
//            mListener.deleteBookmarkCommodity(GodModel);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void showCommodityPopup(Commodity commodity);
//
//        void deleteBookmarkCommodity(Commodity commodity);
//    }
//}
