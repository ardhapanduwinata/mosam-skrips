package com.arwinata.am.skripsi.Retrofit.model;

import com.google.gson.annotations.SerializedName;

public class Misi{

	@SerializedName("jumlahpoin")
	private String jumlahpoin;

	@SerializedName("tabeldibutuhkan")
	private String tabeldibutuhkan;

	@SerializedName("detailmisi")
	private String detailmisi;

	@SerializedName("targetcapaian")
	private int targetcapaian;

	@SerializedName("__v")
	private int V;

	@SerializedName("_id")
	private String id;

	public String getJumlahpoin(){
		return jumlahpoin;
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