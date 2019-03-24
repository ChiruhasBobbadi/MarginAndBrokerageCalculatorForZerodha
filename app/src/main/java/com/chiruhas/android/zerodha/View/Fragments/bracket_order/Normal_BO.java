package com.chiruhas.android.zerodha.View.Fragments.bracket_order;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodha.HelperClasses.AlertHelper;
import com.chiruhas.android.zerodha.HelperClasses.BracketOrder;
import com.chiruhas.android.zerodha.HelperClasses.NameExtractHelper;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.ViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Normal_BO.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Normal_BO#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Normal_BO extends Fragment {


    AutoCompleteTextView auto;
    EditText price, qty, sl;

    ViewModel viewModel;

    List<GodModel> list = new ArrayList<>();
    // add additional radio buttons for different segments
    RadioButton equity;

    RadioButton buy, sell;
    private OnFragmentInteractionListener mListener;

    public Normal_BO() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Normal_BO newInstance(String param1, String param2) {
        Normal_BO fragment = new Normal_BO();
        Bundle args = new Bundle();

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
        View view = inflater.inflate(R.layout.fragment_normal__bo, container, false);
        auto = view.findViewById(R.id.auto_text);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        final View v2 = view;
        // change network calls depending on the radio button later

        viewModel.fetchEquity().observe(this, new Observer<List<GodModel>>() {
            @Override
            public void onChanged(List<GodModel> godModels) {
                list = godModels;
                String lst[] = NameExtractHelper.EquityNames(list);

                if (godModels.isEmpty()) {
                    Toast.makeText(getContext(), "Requires Internet Connection", Toast.LENGTH_LONG).show();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lst);

                auto.setAdapter(adapter);
                Log.d(TAG, "onChanged: Sucess");
            }
        });

        if (auto.getAdapter() == null) {
            Toast.makeText(getContext(), "Requires Internet Connection", Toast.LENGTH_LONG).show();
        }

        price = view.findViewById(R.id.price);
        qty = view.findViewById(R.id.qty);
        sl = view.findViewById(R.id.sl);

        buy = view.findViewById(R.id.buy);
        sell = view.findViewById(R.id.sell);

        Button cal = view.findViewById(R.id.calculate);

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(auto.getText().toString()) || price.getText().toString().equals("")||qty.getText().toString().equals("")||sl.getText().toString().equals(""))
                    Toast.makeText(getContext(), "Field's can't be empty", Toast.LENGTH_SHORT).show();
                else
                new AlertHelper(getContext()).nrml_bo();


            }
        });

        Button reset = view.findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto.setText("");
                price.setText("");
                qty.setText("");
                sl.setText("");

            }
        });


        AdViewHelper.loadBanner(view);


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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
