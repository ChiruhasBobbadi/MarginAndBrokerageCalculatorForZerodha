package com.chiruhas.android.zerodha.Model.Equity;


public class GodModel {
    private double margin;
    private double co_lower;
    private float mis_multiplier;

    private String tradingsymbol;
    private double co_upper;
    private int nrml_margin;
    private float mis_margin;
    private double price;
    private String lotsize;
    private String expiry;
    private float nrml_multiplier;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public GodModel(double margin, double co_lower, float mis_multiplier, String tradingsymbol, double price, double co_upper, int nrml_margin, float mis_margin, String expiry, String lot) {
        this.margin = margin;
        this.co_lower = co_lower;
        this.mis_multiplier = mis_multiplier;
        this.tradingsymbol = tradingsymbol;
        this.co_upper = co_upper;
        this.nrml_margin = nrml_margin;
        this.mis_margin = mis_margin;
        this.price = price;
        this.expiry = expiry;
        this.lotsize = lot;
    }
    public GodModel(double margin, double co_lower, float mis_multiplier, String tradingsymbol, double price, double co_upper, int nrml_margin, float mis_margin, String expiry, String lot,float nrml_multiplier) {
        this.margin = margin;
        this.co_lower = co_lower;
        this.mis_multiplier = mis_multiplier;
        this.tradingsymbol = tradingsymbol;
        this.co_upper = co_upper;
        this.nrml_margin = nrml_margin;
        this.mis_margin = mis_margin;
        this.price = price;
        this.expiry = expiry;
        this.lotsize = lot;
        if(nrml_multiplier==0)
            this.nrml_multiplier=1;
        else
            this.nrml_multiplier=nrml_multiplier;
    }

    public float getNrml_multiplier() {
        return nrml_multiplier;
    }

    public void setNrml_multiplier(float nrml_multiplier) {
        this.nrml_multiplier = nrml_multiplier;
    }

    public GodModel() {
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



    public float getMis_multiplier() {
        return mis_multiplier;
    }



    public String getTradingsymbol() {
        return tradingsymbol;
    }



    public double getCo_upper() {
        return co_upper;
    }



    public int getNrml_margin() {
        return nrml_margin;
    }



    public float getMis_margin() {
        return mis_margin;
    }



    public String getLotsize() {
        return lotsize;
    }


}