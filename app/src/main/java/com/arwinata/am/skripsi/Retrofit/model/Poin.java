package com.arwinata.am.skripsi.Retrofit.model;

import android.provider.ContactsContract;

import com.google.gson.annotations.SerializedName;

public class Poin {

    @SerializedName("user")
    private String user;

    @SerializedName("jmlPoin")
    private int jmlPoin;

    @SerializedName("badge")
    String badge;

    public int getJmlVoucher() {
        return jmlPoin;
    }

    public String getBadge() {
        return badge;
    }

    public Poin(String user) {
        this.user = user;
    }

    public Poin(int jmlPoin, String badge){
        this.jmlPoin = jmlPoin;
        this.badge = badge;
    }
}
