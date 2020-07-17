package com.arwinata.am.skripsi.Retrofit.model;

import com.google.gson.annotations.SerializedName;

public class PoinDanVoucher {

    @SerializedName("user")
    private String user;

    @SerializedName("jmlPoin")
    private int jmlPoin;

    @SerializedName("jmlVoucher")
    private int jmlVoucher;

    @SerializedName("badge")
    String badge;

    public int getJmlPoin() {
        return jmlPoin;
    }

    public String getBadge() {
        return badge;
    }

    public PoinDanVoucher(String user, int jmlPoin) {
        this.user = user;
        this.jmlPoin = jmlPoin;
    }

    public int getJmlVoucher() {
        return jmlVoucher;
    }

    public PoinDanVoucher(int jmlPoin) {
        this.jmlPoin = jmlPoin;
    }

    public PoinDanVoucher(int jmlPoin, String badge){
        this.jmlPoin = jmlPoin;
        this.badge = badge;
    }
}
