package com.arwinata.am.skripsi.Retrofit.model;

import com.google.gson.annotations.SerializedName;

public class Tabungan {
    @SerializedName("message")
    private String message;

    @SerializedName("_id")
    private String _id;

    @SerializedName("user")
    private String user;

    @SerializedName("jmlbotolA")
    private int jmlbotolA;

    @SerializedName("jmlbotolB")
    private int jmlbotolB;

    @SerializedName("jmlgelas")
    private int jmlgelas;

    public Tabungan(String user, int jmlbotolA, int jmlbotolB, int jmlgelas) {
        this.user = user;
        this.jmlbotolA = jmlbotolA;
        this.jmlbotolB = jmlbotolB;
        this.jmlgelas = jmlgelas;
    }

    public String getMessage() {
        return message;
    }

    public String get_id() {
        return _id;
    }

    public String getUserId() {
        return user;
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
