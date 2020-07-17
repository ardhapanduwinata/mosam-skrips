package com.arwinata.am.skripsi.Retrofit.model;

public class Lencana {
    String badge, namauser, user;
    int jmltransaksibotolA, jmltransaksibotolB, jmltransaksigelas;

    public String getNamaUser() {
        return namauser;
    }

    public String getBadge() {
        return badge;
    }

    public int getJmltransaksibotolA() {
        return jmltransaksibotolA;
    }

    public int getJmltransaksibotolB() {
        return jmltransaksibotolB;
    }

    public int getJmltransaksigelas() {
        return jmltransaksigelas;
    }

    public Lencana(String namauser, int jmltransaksibotolA, int jmltransaksibotolB, int jmltransaksigelas, String badge) {
        this.namauser = namauser;
        this.badge = badge;
        this.jmltransaksibotolA = jmltransaksibotolA;
        this.jmltransaksibotolB = jmltransaksibotolB;
        this.jmltransaksigelas = jmltransaksigelas;
    }

    public Lencana(String user) {
        this.user = user;
    }
}
