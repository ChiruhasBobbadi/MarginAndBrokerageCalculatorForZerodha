package com.chiruhas.android.zerodhamargincalculator.Model.Equity.RoomModels;

import androidx.annotation.NonNull;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "commodity_table")
public class GodCommodity {
    private double margin;
    private double co_lower;
    private float mis_multiplier;
    @NonNull
    @PrimaryKey
    private String tradingsymbol;
    private double co_upper;
    private int nrml_margin;
    private int mis_margin;


    public String getLotsize() {
        return lotsize;
    }

    public void setLotsize(String lotsize) {
        this.lotsize = lotsize;
    }

    String lotsize;


    public GodCommodity(double margin, double co_lower, float mis_multiplier, String tradingsymbol, double co_upper, int nrml_margin, int mis_margin) {
        this.margin = margin;
        this.co_lower = co_lower;
        this.mis_multiplier = mis_multiplier;
        this.tradingsymbol = tradingsymbol;
        this.co_upper = co_upper;
        this.nrml_margin = nrml_margin;
        this.mis_margin = mis_margin;
    }

    public GodCommodity() {
    }

    public double getMargin() {
        return margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }

    public double getCo_lower() {
        return co_lower;
    }

    public void setCo_lower(double co_lower) {
        this.co_lower = co_lower;
    }

    public float getMis_multiplier() {
        return mis_multiplier;
    }

    public void setMis_multiplier(float mis_multiplier) {
        this.mis_multiplier = mis_multiplier;
    }

    @NonNull
    public String getTradingsymbol() {
        return tradingsymbol;
    }

    public void setTradingsymbol(@NonNull String tradingsymbol) {
        this.tradingsymbol = tradingsymbol;
    }

    public double getCo_upper() {
        return co_upper;
    }

    public void setCo_upper(double co_upper) {
        this.co_upper = co_upper;
    }

    public int getNrml_margin() {
        return nrml_margin;
    }

    public void setNrml_margin(int nrml_margin) {
        this.nrml_margin = nrml_margin;
    }

    public int getMis_margin() {
        return mis_margin;
    }

    public void setMis_margin(int mis_margin) {
        this.mis_margin = mis_margin;
    }
}
