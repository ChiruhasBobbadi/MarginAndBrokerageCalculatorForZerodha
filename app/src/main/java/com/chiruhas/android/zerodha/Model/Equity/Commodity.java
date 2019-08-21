package com.chiruhas.android.zerodha.Model.Equity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "commodity_table")
public class Commodity {
    @NonNull
    @PrimaryKey
    private String scrip;
    private String lot;
    private double price;
    private int nrml;
    private int mis;
    private double co_lower;
    private double co_upper;
    private double margin;
    private float mis_multiplier;

    public float getMis_multiplier() {
        return mis_multiplier;
    }

    public void setMis_multiplier(float mis_multiplier) {
        this.mis_multiplier = mis_multiplier;
    }

    public double getMargin() {
        return margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }

    public Commodity() {
    }

    public void setCo_lower(double co_lower) {
        this.co_lower = co_lower;
    }

    public void setCo_upper(double co_upper) {
        this.co_upper = co_upper;
    }

    public String getScrip() {
        return scrip;
    }
    @NonNull
    public void setScrip(String scrip) {
        this.scrip = scrip;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNrml() {
        return nrml;
    }

    public void setNrml(int nrml) {
        this.nrml = nrml;
    }

    public int getMis() {
        return mis;
    }

    public void setMis(int mis) {
        this.mis = mis;
    }

    public double getCo_lower() {
        return co_lower;
    }

    public double getCo_upper() {
        return co_upper;
    }

    @Ignore
    public Commodity(String scrip, String lot, double price, int nrml, int mis,double co_lower,double co_upper,double margin,float mis_multiplier) {
        this.scrip = scrip;
        this.lot = lot;
        this.price = price;
        this.nrml = nrml;
        this.mis = mis;
        this.co_lower = co_lower;
        this.co_upper = co_upper;
        this.margin = margin;
        this.mis_multiplier = mis_multiplier;
    }
}
