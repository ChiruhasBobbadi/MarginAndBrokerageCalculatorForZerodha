package com.chiruhas.android.zerodha.Model.Equity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "futures_table")
public class Futures {
    @NonNull
    @PrimaryKey
    private String scrip;
    private String expiry;
    private String lot;
    private double price;
    private float nrml;
    private float mis;
    private String mwpl;
    private double co_lower;
    private double co_upper;
    private float margin;

    @NonNull
    public String getScrip() {
        return scrip;
    }

    public String getExpiry() {
        return expiry;
    }

    public String getLot() {
        return lot;
    }

    public double getPrice() {
        return price;
    }

    public float getNrml() {
        return nrml;
    }

    public float getMis() {
        return mis;
    }

    public String getMwpl() {
        return mwpl;
    }

    public Futures() {
    }

    public double getCo_lower() {
        return co_lower;
    }

    public double getCo_upper() {
        return co_upper;
    }

    public float getMargin() {
        return margin;
    }

    public Futures(@NonNull String scrip, String expiry, String lot, double price, float nrml, float mis, String mwpl, double co_lower, double co_upper, float margin) {
        this.scrip = scrip;
        this.expiry = expiry;
        this.lot = lot;
        this.price = price;
        this.nrml = nrml;
        this.mis = mis;
        this.mwpl = mwpl;
        this.co_lower = co_lower;
        this.co_upper = co_upper;
        this.margin = margin;
    }
}
