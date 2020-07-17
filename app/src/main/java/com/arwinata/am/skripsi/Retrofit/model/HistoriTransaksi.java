package com.arwinata.am.skripsi.Retrofit.model;

public class HistoriTransaksi {
    String user;
    String namaTransaksi;
    String date;
    String transaksiNabung, transaksiTukarTiket, transaksiNaikBis;

    public HistoriTransaksi(String user, String namaTransaksi, String transaksiNabung, String transaksiTukarTiket, String transaksiNaikBis) {
        this.user = user;
        this.namaTransaksi = namaTransaksi;
        this.transaksiNabung = transaksiNabung;
        this.transaksiTukarTiket = transaksiTukarTiket;
        this.transaksiNaikBis = transaksiNaikBis;
    }

    public HistoriTransaksi(String user, String namaTransaksi, String date) {
        this.user = user;
        this.namaTransaksi = namaTransaksi;
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public String getNamaTransaksi() {
        return namaTransaksi;
    }

    public String getDate() {
        return date;
    }
}
