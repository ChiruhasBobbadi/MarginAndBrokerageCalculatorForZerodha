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

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.chiruhas.android.zerodha.HelperClasses.BracketOrder;
import com.chiruhas.android.zerodha.HelperClasses.NameExtractHelper;
import com.chiruhas.android.zerodha.HelperClasses.ObjectConverter;
import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 *
 */
public class Normal_BO extends Fragment {


    AutoCompleteTextView auto;
    EditText price, qty, sl;
    RadioGroup rg;
    ViewModel viewModel;

    int lot_size = 0;
    Map<String, Integer> map = new HashMap<>();
    List<GodModel> list = new ArrayList<>();
    List<Commodity> commodityList = new ArrayList<>();
    List<Futures> futuresList = new ArrayList<>();
    List<Currency> currencyList = new ArrayList<>();
    // add additional radio buttons for different segments


    RadioButton buy, sell;
    private OnFragmentInteractionListener mListener;

    public Normal_BO() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Normal_BO newInstance(String param1, String param2) {
        Normal_BO fragment = new Normal_BO();


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

                        break;
                    case R.id.nfo:
                        lst = fetchFutures();
                        lot.setVisibility(View.VISIBLE);
                        break;

                        case R.id.cds:


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
                    Commodity commodity = null;
                    for (Commodity c : commodityList) {
                        if (c.getScrip().equals(str)) {
                            commodity = c;
                            break;
                        }
                    }


                    lot.setText("Lot Size : " + commodity.getLot());
                    lot_size = Integer.parseInt(commodity.getLot());
                    price.setText("");
                    qty.setText("");
                    sl.setText("");

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
                        price.getText().toString().equals("") || !price.getText().toString().startsWith(".") || qty.getText().toString().equals("")
                        || sl.getText().toString().equals("") || !sl.getText().toString().startsWith("."))
                    Toast.makeText(getContext(), "Field's can't be empty", Toast.LENGTH_SHORT).show();
                else {

                    Commodity commodity = null;
                    GodModel equity = null;
                    Futures futures = null;

                    if (list == null && commodityList == null && futuresList == null)
                        Toast.makeText(getContext(), "Oops Something happened", Toast.LENGTH_SHORT).show();
                    else {
                        String type = "";
                        if (rg.getCheckedRadioButtonId() == R.id.equity) {
                            for (GodModel model : list) {
                                if (model.getTradingsymbol().equals(auto.getText().toString())) {
                                    equity = model;
                                    break;

                                }
                            }
                            type = "equity";
                        } else if (rg.getCheckedRadioButtonId() == R.id.mcx) {
                            for (Commodity c : commodityList) {
                                if (c.getScrip().equals(auto.getText().toString())) {
                                    commodity = c;
                                    break;
                                }
                            }
                            type = "mcx";
                        } else if (rg.getCheckedRadioButtonId() == R.id.nfo) {
                            for (Futures c : futuresList) {
                                if (c.getScrip().equals(auto.getText().toString())) {
                                    futures = c;
                                    break;
                                }
                            }
                            type = "nfo";
                        }

                        // checking lot size mapping
                        int q = Integer.parseInt(qty.getText().toString().trim());

                        if (type.equals("mcx")) {
                            if (q < lot_size) {

                                Toast.makeText(getContext(), "Enter a valid Quantity....", Toast.LENGTH_SHORT).show();

                            } else {
                                if (q % lot_size != 0)
                                    Toast.makeText(getContext(), "Enter a valid Quantity....", Toast.LENGTH_SHORT).show();
                                else {
                                    new BracketOrder().calculate(getContext(), commodity.getScrip(), price.getText().toString(), qty.getText().toString(),
                                            sl.getText().toString(), type, status, ObjectConverter.commodity2God(commodity));
                                }
                            }
                        } else if (type.equals("equity")) {
                            new BracketOrder().calculate(getContext(), equity.getTradingsymbol(), price.getText().toString(), qty.getText().toString(),
                                    sl.getText().toString(), type, status, equity);
                        } else if (type.equals("nfo")) {
                            if (q < lot_size) {
                                Toast.makeText(getContext(), "Enter a valid Quantity....", Toast.LENGTH_SHORT).show();
                            } else {
                                if (q % lot_size != 0)
                                    Toast.makeText(getContext(), "Enter a valid Quantity....", Toast.LENGTH_SHORT).show();
                                else {
                                    new BracketOrder().calculate(getContext(), futures.getScrip(), price.getText().toString(), qty.getText().toString(),
                                            sl.getText().toString(), type, status, ObjectConverter.future2God(futures));
                                }
                            }
                        }
                        else if(type.equals("cds")){

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

        viewModel.fetchCommodity().observe(this, new Observer<List<Commodity>>() {
            @Override
            public void onChanged(List<Commodity> godModels) {
                commodityList = godModels;

            }
        });
        return NameExtractHelper.commodityName(commodityList);
    }

    public String[] fetchFutures() {
        viewModel.fetchFutures().observe(this, new Observer<List<Futures>>() {
            @Override
            public void onChanged(List<Futures> godModels) {
                futuresList = godModels;

            }
        });
        return NameExtractHelper.futureNames(futuresList);
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


}
