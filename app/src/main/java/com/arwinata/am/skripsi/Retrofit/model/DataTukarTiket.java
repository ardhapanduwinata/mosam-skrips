package com.arwinata.am.skripsi.Retrofit.model;

import com.google.gson.annotations.SerializedName;

public class DataTukarTiket {

    @SerializedName("user")
    private String user;

    @SerializedName("date")
    private String date;

    @SerializedName("jmlTiket")
    private  int jmlTiket;

    @SerializedName("jmlbotolA")
    private int jmlbotolA;

    @SerializedName("jmlbotolB")
    private int jmlbotolB;

    @SerializedName("jmlgelas")
    private int jmlgelas;

    public DataTukarTiket(String user, String date, int jmlTiket, int jmlbotolA, int jmlbotolB, int jmlgelas) {
        this.user = user;
        this.date = date;
        this.jmlTiket = jmlTiket;
        this.jmlbotolA = jmlbotolA;
        this.jmlbotolB = jmlbotolB;
        this.jmlgelas = jmlgelas;
    }

    public String getDate() {
        return date;
    }

    public int getJmlTiket() {
        return jmlTiket;
    }

    public int getJmlbotolA() {
        return jmlbotolA;
    }

    public int getJmlbotolB() {
        return jmlbotolB;
    }

    public int getJmlgelas() {
        return jmlgelas;
    }
}
