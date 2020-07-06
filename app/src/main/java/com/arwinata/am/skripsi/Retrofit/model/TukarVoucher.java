package com.arwinata.am.skripsi.Retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TukarVoucher {

    @SerializedName("user")
    private String user;

    @SerializedName("date")
    private Date date;

    @SerializedName("jmlVoucher")
    private  int jmlVoucher;

    @SerializedName("jmlbotolA")
    private int jmlbotolA;

    @SerializedName("jmlbotolB")
    private int jmlbotolB;

    @SerializedName("jmlgelas")
    private int jmlgelas;

    public TukarVoucher(String user, Date date, int jmlVoucher, int jmlbotolA, int jmlbotolB, int jmlgelas) {
        this.user = user;
        this.date = date;
        this.jmlVoucher = jmlVoucher;
        this.jmlbotolA = jmlbotolA;
        this.jmlbotolB = jmlbotolB;
        this.jmlgelas = jmlgelas;
    }

    public Date getDate() {
        return date;
    }

    public int getJmlVoucher() {
        return jmlVoucher;
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
