package com.chiruhas.android.zerodha.View.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chiruhas.android.zerodha.R;

public class SettingsActivity extends AppCompatActivity {

    private String default_state;
    private SharedPreferences data;
    private Button save;

    private EditText equity, commodity, futures, currency;

    private double _equity = 0, _commodity = 0, _futures = 0, _currency = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkSharedPrefs();
        init();


        save.setOnClickListener(view -> {

            try {
                double temp = 0;
                String eq = equity.getText().toString();
                if (eq.length() > 0)
                    temp = Double.parseDouble(eq);

                String co = commodity.getText().toString();
                if (co.length() > 0)
                    temp = Double.parseDouble(co);

                String fu = futures.getText().toString();
                if (fu.length() > 0)
                    temp = Double.parseDouble(fu);

                String cu = currency.getText().toString();
                if (cu.length() > 0)
                    temp = Double.parseDouble(cu);

                updateData(eq, co, fu, cu);


            } catch (Exception e) {
                Toast.makeText(this, "Please enter proper values", Toast.LENGTH_SHORT).show();
            }

            //updateData();
        });


    }

    private void updateData(String eq, String co, String fu, String cu) {
        SharedPreferences.Editor myEdit
                = data.edit();

        myEdit.putString(
                "equity",
                eq);
        myEdit.putString(
                "commodity",
                co);
        myEdit.putString(
                "futures",
                fu);
        myEdit.putString(
                "currency",
                cu);
        Toast.makeText(this, "Preferences Updated", Toast.LENGTH_LONG).show();
        myEdit.apply();
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
    }

    private void checkSharedPrefs() {

        data = getSharedPreferences("dataStore",
                MODE_PRIVATE);


        default_state = data.getString("equity", "");
        if (!default_state.equals(""))
            _equity = Double.parseDouble(default_state);

        default_state = data.getString("commodity", "");
        if (!default_state.equals(""))
            _commodity = Double.parseDouble(default_state);

        default_state = data.getString("futures", "");
        if (!default_state.equals(""))
            _futures = Double.parseDouble(default_state);

        default_state = data.getString("currency", "");
        if (!default_state.equals(""))
            _currency = Double.parseDouble(default_state);


    }

    private void init() {

        save = findViewById(R.id.save);
        equity = findViewById(R.id.equity);
        commodity = findViewById(R.id.commodity);
        futures = findViewById(R.id.futures);
        currency = findViewById(R.id.currency);
        if (_equity != 0)
            equity.setText(_equity + "");
        if (_commodity != 0)
            commodity.setText(_commodity + "");
        if (_futures != 0)
            futures.setText(_futures + "");
        if (_currency != 0)
            commodity.setText(_currency + "");


    }
}
