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

    public Commodity() {
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

    @Ignore
    public Commodity(String scrip, String lot, double price, int nrml, int mis) {
        this.scrip = scrip;
        this.lot = lot;
        this.price = price;
        this.nrml = nrml;
        this.mis = mis;
    }
}
