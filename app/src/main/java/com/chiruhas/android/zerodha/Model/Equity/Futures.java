package com.chiruhas.android.zerodha.Model.Equity;

import androidx.annotation.NonNull;

public class Futures {

    private String scrip;
    private String expiry;
    private String lot;
    private String price;
    private String nrml;
    private String mis;
    private String mwpl;
    private double co_lower;
    private double co_upper;
    private float margin;
    private float mis_multiplier;

    public Futures() {
    }

    public Futures(@NonNull String scrip, String expiry, String lot, String price, String nrml, String mis, String mwpl, double co_lower, double co_upper, float margin, float mis_multiplier) {
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
        this.mis_multiplier = mis_multiplier;
    }


    public String getScrip() {
        return scrip;
    }

    public void setScrip(@NonNull String scrip) {
        this.scrip = scrip;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNrml() {
        return nrml;
    }

    public void setNrml(String nrml) {
        this.nrml = nrml;
    }

    public String getMis() {
        return mis;
    }

    public void setMis(String mis) {
        this.mis = mis;
    }

    public String getMwpl() {
        return mwpl;
    }

    public void setMwpl(String mwpl) {
        this.mwpl = mwpl;
    }

    public double getCo_lower() {
        return co_lower;
    }

    public void setCo_lower(double co_lower) {
        this.co_lower = co_lower;
    }

    public double getCo_upper() {
        return co_upper;
    }

    public void setCo_upper(double co_upper) {
        this.co_upper = co_upper;
    }

    public float getMargin() {
        return margin;
    }

    public void setMargin(float margin) {
        this.margin = margin;
    }

    public float getMis_multiplier() {
        return mis_multiplier;
    }

    public void setMis_multiplier(float mis_multiplier) {
        this.mis_multiplier = mis_multiplier;
    }
}
