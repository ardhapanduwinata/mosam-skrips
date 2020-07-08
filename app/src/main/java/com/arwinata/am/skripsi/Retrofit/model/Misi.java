package com.arwinata.am.skripsi.Retrofit.model;

public class Misi{
	private int jumlahpoin;
	private String tabeldibutuhkan;
	private String detailmisi;
	private int targetcapaian;
	private int V;
	private String id;

	public int getJumlahpoin(){
		return jumlahpoin;
	}

    public Misi(String detailmisi, int targetcapaian, int jumlahpoin, String tabeldibutuhkan) {
        this.detailmisi = detailmisi;
        this.targetcapaian = targetcapaian;
        this.jumlahpoin = jumlahpoin;
        this.tabeldibutuhkan = tabeldibutuhkan;
    }

    public String getTabeldibutuhkan(){
		return tabeldibutuhkan;
	}

	public String getDetailmisi(){
		return detailmisi;
	}

	public int getTargetcapaian(){
		return targetcapaian;
	}

	public int getV(){
		return V;
	}

	public String getId(){
		return id;
	}
}
