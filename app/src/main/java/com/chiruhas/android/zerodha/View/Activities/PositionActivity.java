package com.chiruhas.android.zerodha.View.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodha.HelperClasses.PositionHelper;
import com.chiruhas.android.zerodha.R;

public class PositionActivity extends AppCompatActivity {

    private EditText cap, rsk, ent, slm, tar;
    private String capital, risk, entry, sl, target;
    Button cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        getSupportActionBar().setTitle("Position Calculator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View rootView = getWindow().getDecorView().getRootView();
        //adview
        AdViewHelper.loadBanner(rootView);


        cap = findViewById(R.id.capital);
        rsk = findViewById(R.id.risk);
        ent = findViewById(R.id.entry);
        slm = findViewById(R.id.sl);
        tar = findViewById(R.id.target);


        cal = findViewById(R.id.calculate);
        cal.setOnClickListener(view -> {
            capital = cap.getText().toString().trim();
            risk = rsk.getText().toString().trim();
            entry = ent.getText().toString().trim();
            sl = slm.getText().toString().trim();
            target = tar.getText().toString().trim();

            if (capital.isEmpty() || risk.isEmpty() || entry.isEmpty() || sl.isEmpty() || target.isEmpty())
                Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show();

            else {

                double c, r, e, s, t;
                c = Double.parseDouble(capital);
                r = Double.parseDouble(risk);
                e = Double.parseDouble(entry);
                s = Double.parseDouble(sl);
                t = Double.parseDouble(target);

                if (s == e)
                    Toast.makeText(this, "Stoploss should be greater or less than entry", Toast.LENGTH_SHORT).show();
                else
                    new PositionHelper(PositionActivity.this).calculatePosition(c, r, e, s, t);
            }
        });


    }
}
