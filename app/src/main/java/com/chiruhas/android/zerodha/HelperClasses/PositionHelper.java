package com.chiruhas.android.zerodha.HelperClasses;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiruhas.android.zerodha.R;

public class PositionHelper {
    Context context;
    Dialog myDialog;
    private TextView shares, position, profit, loss, rratio;

    public PositionHelper(Context context) {
        this.context = context;
        myDialog = new Dialog(context);
    }

    public void calculatePosition(double capital, double risk, double entry, double sl, double target) {

        sl = Math.abs(entry - sl);
        target = Math.abs(entry - target);
        double capRisk = (capital * risk) / 100;
        int numStocks = (int) (capRisk / sl);
        double pSize = numStocks * entry;
        //profit
        double pr = target;
        //loss
        double ls = sl;
        //risk reward ratio
        double riskReward = ls / pr;

        //final View view = LayoutInflater.from(context).inflate(R.layout.positionpopup, null);

        myDialog.setContentView(R.layout.positionpopup);

        shares = myDialog.findViewById(R.id.shares);
        position = myDialog.findViewById(R.id.position);
        profit = myDialog.findViewById(R.id.profit);
        loss = myDialog.findViewById(R.id.loss);
        rratio = myDialog.findViewById(R.id.ratio);

        shares.setText(numStocks + "");
        position.setText(String.format("%.2f", pSize));
        profit.setText(String.format("%.2f", pr));
        loss.setText(String.format("%.2f", ls));
        rratio.setText(String.format("%.2f", riskReward));

        ImageView close = myDialog.findViewById(R.id.close);
        close.setOnClickListener(v -> myDialog.dismiss());
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setCancelable(false);
        myDialog.show();


    }
}
