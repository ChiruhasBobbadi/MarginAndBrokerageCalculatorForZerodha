package com.chiruhas.android.zerodha.View.Fragments.bracket_order;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodha.HelperClasses.BracketOrder;
import com.chiruhas.android.zerodha.HelperClasses.NameExtractHelper;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**

 */
public class Normal_BO extends Fragment {


    AutoCompleteTextView auto;
    EditText price, qty, sl;
    RadioGroup rg;
    ViewModel viewModel;

    int lot_size = 0;
    Map<String, Integer> map = new HashMap<>();
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
        rg = view.findViewById(R.id.radioGroup);
        final TextView lot = view.findViewById(R.id.lot);
        lot.setVisibility(View.GONE);


        price = view.findViewById(R.id.price);
        qty = view.findViewById(R.id.qty);
        sl = view.findViewById(R.id.sl);

        buy = view.findViewById(R.id.buy);
        sell = view.findViewById(R.id.sell);

        Button cal = view.findViewById(R.id.calculate);


        // change network calls depending on the radio button later


        // default adapter

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


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String lst[] = null;

                switch (checkedId) {
                    case R.id.equity:
                        lst = fetchEquity();

                        lot.setVisibility(View.GONE);
                        break;

                    case R.id.mcx:

                        lst = fetchCommodity();
                        lot.setVisibility(View.VISIBLE);

                        setMap();
                        break;

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lst);
                auto.setAdapter(adapter);
            }
        });

        auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (rg.getCheckedRadioButtonId() == R.id.mcx) {
                    String str = auto.getText().toString().trim();
                    lot.setText("Lot Size : " + map.get(str));
                    lot_size = map.get(str);

                }
            }
        });


        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for BUY or sell
                String status = "";
                if (buy.isChecked())
                    status = "buy";
                else
                    status = "sell";


                if (TextUtils.isEmpty(auto.getText().toString()) ||
                        price.getText().toString().equals("") || qty.getText().toString().equals("")
                        || sl.getText().toString().equals(""))
                    Toast.makeText(getContext(), "Field's can't be empty", Toast.LENGTH_SHORT).show();
                else {

                    GodModel godModel = null;
                    for (GodModel g : list) {
                        if (g.getTradingsymbol().equals(auto.getText().toString().trim())) {
                            godModel = g;
                            break;
                        }
                    }

                    if (godModel == null)
                        Toast.makeText(getContext(), "Oops Something happened", Toast.LENGTH_SHORT).show();
                    else {
                        String type = "";
                        if (rg.getCheckedRadioButtonId() == R.id.equity)
                            type = "equity";
                        else if (rg.getCheckedRadioButtonId() == R.id.mcx)
                            type = "mcx";

                        // checking lot size mapping
                        int q = Integer.parseInt(qty.getText().toString().trim());

                        if (q < lot_size) {

                            Toast.makeText(getContext(), "Enter a valid Quantity....", Toast.LENGTH_SHORT).show();

                        } else {
                            if (q % lot_size != 0)
                                Toast.makeText(getContext(), "Enter a valid Quantity....", Toast.LENGTH_SHORT).show();
                            else {
                                new BracketOrder().calculate(getContext(), godModel.getTradingsymbol(), price.getText().toString(), qty.getText().toString(),
                                        sl.getText().toString(), type, status, godModel);
                            }
                        }


                    }

                }


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

    public String[] fetchCommodity() {

        viewModel.fetchCommodity().observe(this, new Observer<List<GodModel>>() {
            @Override
            public void onChanged(List<GodModel> godModels) {
                list = godModels;

            }
        });
        return NameExtractHelper.EquityNames(list);
    }

    public String[] fetchEquity() {
        viewModel.fetchEquity().observe(this, new Observer<List<GodModel>>() {
            @Override
            public void onChanged(List<GodModel> godModels) {
                list = godModels;

            }
        });
        return NameExtractHelper.EquityNames(list);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void setMap() {

        map.put("ALUMINI", 1000);
        map.put("ALUMINIUM", 5000);
        map.put("BRASSPHY", 1000);
        map.put("CARDAMOM", 100);
        map.put("CASTORSEED", 1000);
        map.put("COPPER", 1000);
        map.put("COPPERM", 250);
        map.put("COTTON", 25);
        map.put("CPO", 1000);
        map.put("CRUDEOIL", 100);

        map.put("CRUDEOILM", 10);
        map.put("GOLD", 100);
        map.put("GOLDGUINEA", 1);
        map.put("GOLDM", 10);
        map.put("GOLDPETAL", 1);
        map.put("LEAD", 5000);
        map.put("LEADMINI", 1000);
        map.put("MENTHAOIL", 360);
        map.put("NATURALGAS", 1250);
        map.put("NICKEL", 250);
        map.put("NICKELM", 100);
        map.put("PEPPER", 10);
        map.put("RBDPMOLEIN", 1000);
        map.put("SILVER", 30);
        map.put("SILVERM", 5);

        map.put("SILVERMIC", 1);
        map.put("ZINC", 5000);
        map.put("ZINCINI", 1000);
    }

}
