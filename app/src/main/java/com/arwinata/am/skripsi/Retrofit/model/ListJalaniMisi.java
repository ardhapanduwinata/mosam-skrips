package com.arwinata.am.skripsi.Retrofit.model;

public class ListJalaniMisi{
    private String detailmisi;
    private int targetcapaian;
    private int poin;
    private int targettercapai;
    private String status;
    private String user;
    private String misi;
    private String tabeldibutuhkan;
    private String claimpoin;
    private String dateselesai;

    public String getClaimpoin() {
        return claimpoin;
    }

    public String getDateselesai() {
        return dateselesai;
    }

    public ListJalaniMisi(String user, String misi, String tabeldibutuhkan, String detailmisi, int targetcapaian, int poin, int targettercapai, String status, String claimpoin, String dateselesai) {
        this.user = user;
        this.misi = misi;
        this.detailmisi = detailmisi;
        this.tabeldibutuhkan = tabeldibutuhkan;
        this.targetcapaian = targetcapaian;
        this.poin = poin;
        this.targettercapai = targettercapai;
        this.status = status;
        this.claimpoin = claimpoin;
        this.dateselesai = dateselesai;
    }

    public String getTabeldibutuhkan() {
        return tabeldibutuhkan;
    }

    public String getDetailmisi() {
        return detailmisi;
    }

    public int getTargetcapaian() {
        return targetcapaian;
    }

    public int getPoin() {
        return poin;
    }

    public int getTargettercapai() {
        return targettercapai;
    }

    public String getStatus(){
        return status;
    }

    public String getUser() {
        return user;
    }

    public String getMisi() {
        return misi;
    }
}
