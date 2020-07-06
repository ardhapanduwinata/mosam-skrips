package com.arwinata.am.skripsi.Retrofit.model;

import android.provider.ContactsContract;

import com.google.gson.annotations.SerializedName;

public class DataVoucher {

    @SerializedName("user")
    private String user;

    @SerializedName("jmlVoucher")
    private int jmlVoucher;

    public DataVoucher(String user, int jmlVoucher) {
        this.user = user;
        this.jmlVoucher = jmlVoucher;
    }

    public DataVoucher(int jmlVoucher){
        this.jmlVoucher = jmlVoucher;
    }

    public int getJmlVoucher() {
        return jmlVoucher;
    }
}
