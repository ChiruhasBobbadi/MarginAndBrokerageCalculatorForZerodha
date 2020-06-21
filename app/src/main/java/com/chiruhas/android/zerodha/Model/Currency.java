package com.chiruhas.android.zerodha.Model;

import androidx.annotation.NonNull;

public class Currency {
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
    private float mis_multiplier;

    public float getOp_mis() {
        return op_mis;
    }

    public void setOp_mis(float op_mis) {
        this.op_mis = op_mis;
    }

    private float op_mis;


    public Currency() {
    }

    public Currency(@NonNull String scrip, String expiry, String lot, double price, int nrml, int mis, String mwpl, double co_lower, double co_upper, float margin, float mis_multiplier) {
        this.scrip = scrip;
        this.expiry = expiry;
        this.lot = lot;
        this.price = price;
        this.nrml = nrml;
        this.mis = mis;
        this.mis_multiplier = mis_multiplier;
        this.mwpl = mwpl;
        this.co_lower = co_lower;
        this.co_upper = co_upper;
        this.margin = margin;
    }

    public float getMis_multiplier() {
        return mis_multiplier;
    }

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

    public double getCo_lower() {
        return co_lower;
    }

    public double getCo_upper() {
        return co_upper;
    }

    public float getMargin() {
        return margin;
    }
}
