package com.arwinata.am.skripsi.Retrofit.model;

import com.google.gson.annotations.SerializedName;

public class JalanimisiItem{

	@SerializedName("misi")
	private Misi misi;

	@SerializedName("user")
	private User user;

	@SerializedName("targettercapai")
	private int targettercapai;

	@SerializedName("status")
	private String status;

	public Misi getMisi(){
		return misi;
	}

	public User getUser(){
		return user;
	}

	public int getTargettercapai(){
		return targettercapai;
	}

	public String getStatus(){
		return status;
	}
}