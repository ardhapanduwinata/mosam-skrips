package com.arwinata.am.skripsi.Retrofit.model;

import com.google.gson.annotations.SerializedName;

public class DataTiket {

    @SerializedName("user")
    private String user;

    @SerializedName("jmlTiket")
    private int jmlTiket;

    public DataTiket(String user, int jmlTiket) {
        this.user = user;
        this.jmlTiket = jmlTiket;
    }

    public DataTiket(int jmlTiket){
        this.jmlTiket = jmlTiket;
    }

    public int getJmlTiket() {
        return jmlTiket;
    }
}
