package com.chiruhas.android.zerodha.HelperClasses;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.View.Activities.CommodityActivity;

public class AlertHelper {
    Context context;
    Dialog myDialog;

     float cnc1,mis1;

    public AlertHelper(Context context){
        this.context=context;
        myDialog = new Dialog(context);
        cnc1=0.0f;
        mis1=0.0f;
    }
    public  void loadEquityPopup(GodModel item){
        final View view = LayoutInflater.from(context).inflate(R.layout.equitycalculate, null);

        myDialog.setContentView(R.layout.equitycalculate);


        TextView scrip;
        final TextView mis_mux,nrml;

        scrip = myDialog.findViewById(R.id.scrip);
        mis_mux = myDialog.findViewById(R.id.mis_mux);
        nrml = myDialog.findViewById(R.id.mis_mux2);

         float mis =0;
         float cnc=0;

        scrip.setText(item.getTradingsymbol());
        if(context instanceof CommodityActivity){
            mis_mux.setText("MIS : "+item.getMis_margin());
            nrml.setText("NRML : "+item.getNrml_margin());
            mis = item.getMis_margin();
            cnc = item.getNrml_margin();
        }
        else{
            // for equity
            mis_mux.setText("MIS : " + item.getMis_multiplier() + "X");
            mis = item.getMis_multiplier();
            cnc=1.0f;
        }



        cnc1=cnc;
        mis1=mis;
        final EditText amount = myDialog.findViewById(R.id.amount);

       final EditText price = myDialog.findViewById(R.id.price);



        TextView close = myDialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });



        price.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.equals("")) {

                    double amt = Double.parseDouble(amount.getText().toString());
                    if (amt != 0.0) {
                        TextView misqty = myDialog.findViewById(R.id.misqty);
                        TextView cncqty = myDialog.findViewById(R.id.cncqty);

                        double prc = Double.parseDouble(charSequence.toString());
                        misqty.setText(Math.floor(((amt * mis1) / prc)) + "");
                        cncqty.setText(Math.floor(((amt*cnc1) / prc)) + "");
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setCancelable(false);
        myDialog.show();
    }



}
