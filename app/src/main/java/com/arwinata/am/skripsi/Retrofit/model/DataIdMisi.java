package com.arwinata.am.skripsi.Retrofit.model;

public class DataIdMisi{
    public String getMisimenabung() {
        return misimenabung;
    }

    public String getMisimengumpulkanbotolA() {
        return misimengumpulkanbotolA;
    }

    public String getMisimengumpulkanbotolB() {
        return misimengumpulkanbotolB;
    }

    public String getMisimengumpulkangelas() {
        return misimengumpulkangelas;
    }

    public DataIdMisi(){}

    public void setMisimenabung(String misimenabung) {
        this.misimenabung = misimenabung;
    }

    public void setMisimengumpulkanbotolA(String misimengumpulkanbotolA) {
        this.misimengumpulkanbotolA = misimengumpulkanbotolA;
    }

    public void setMisimengumpulkanbotolB(String misimengumpulkanbotolB) {
        this.misimengumpulkanbotolB = misimengumpulkanbotolB;
    }

    public void setMisimengumpulkangelas(String misimengumpulkangelas) {
        this.misimengumpulkangelas = misimengumpulkangelas;
    }

    private String misimenabung, misimengumpulkanbotolA, misimengumpulkanbotolB, misimengumpulkangelas;
    private String statusmenabung, statusmengumpulkanbotolA, statusmengumpulkanbotolB, statusmengumpulkangelas;

    public String getStatusmenabung() {
        return statusmenabung;
    }

    public void setStatusmenabung(String statusmenabung) {
        this.statusmenabung = statusmenabung;
    }

    public String getStatusmengumpulkanbotolA() {
        return statusmengumpulkanbotolA;
    }

    public void setStatusmengumpulkanbotolA(String statusmengumpulkanbotolA) {
        this.statusmengumpulkanbotolA = statusmengumpulkanbotolA;
    }

    public String getStatusmengumpulkanbotolB() {
        return statusmengumpulkanbotolB;
    }

    public void setStatusmengumpulkanbotolB(String statusmengumpulkanbotolB) {
        this.statusmengumpulkanbotolB = statusmengumpulkanbotolB;
    }

    public String getStatusmengumpulkangelas() {
        return statusmengumpulkangelas;
    }

    public void setStatusmengumpulkangelas(String statusmengumpulkangelas) {
        this.statusmengumpulkangelas = statusmengumpulkangelas;
    }
}