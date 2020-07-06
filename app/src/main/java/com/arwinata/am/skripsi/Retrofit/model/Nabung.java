package com.arwinata.am.skripsi.Retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Nabung {
    @SerializedName("message")
    private String message;

    @SerializedName("_id")
    private String _id;

    @SerializedName("user")
    private String user;

    @SerializedName("bank")
    private String bank;

    @SerializedName("jmlbotolA")
    private int jmlbotolA;

    @SerializedName("jmlbotolB")
    private int jmlbotolB;

    @SerializedName("jmlgelas")
    private int jmlgelas;

    @SerializedName("date")
    private Date date;

    public Nabung(String user, String bank, int jmlbotolA, int jmlbotolB, int jmlgelas, Date date) {
        this.user = user;
        this.bank = bank;
        this.jmlbotolA = jmlbotolA;
        this.jmlbotolB = jmlbotolB;
        this.jmlgelas = jmlgelas;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public String getUser() {
        return user;
    }

    public String getBank() {
        return bank;
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
