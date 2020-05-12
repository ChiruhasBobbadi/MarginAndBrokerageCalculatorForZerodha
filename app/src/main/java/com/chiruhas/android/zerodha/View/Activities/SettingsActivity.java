package com.chiruhas.android.zerodha.View.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chiruhas.android.zerodha.R;

public class SettingsActivity extends AppCompatActivity {
    private Spinner spinner;
    private int state;
    private String default_state;
    private SharedPreferences data;
    private Button save;
    private int stateIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkSharedPrefs();
        init();


        save.setOnClickListener(view -> {

            updateData();
        });


    }

    private void updateData() {
        SharedPreferences.Editor myEdit
                = data.edit();
        myEdit.putString(
                "default_state",
                state + "");
        Toast.makeText(this, "Preferences Updated", Toast.LENGTH_SHORT).show();
        myEdit.apply();
    }

    private void checkSharedPrefs() {

        data = getSharedPreferences("dataStore",
                MODE_PRIVATE);
        default_state = data.getString("default_state", "");
        if (!default_state.equals(""))
            stateIndex = Integer.parseInt(default_state);

    }

    private void init() {
        spinner = findViewById(R.id.spinner);
        save = findViewById(R.id.save);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(SettingsActivity.this, R.array.states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.getViewTreeObserver().addOnGlobalLayoutListener(() -> ((TextView) spinner.getSelectedView()).setTextColor(getResources().getColor(R.color.white_grey)));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                state = position;
               /* save.setEnabled(true);
                save.setBackgroundResource(R.drawable.rounded_calculate_btn);
                save.setClickable(true);*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner.setSelection(stateIndex);
    }
}
