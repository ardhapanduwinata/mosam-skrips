package com.arwinata.am.skripsi.Retrofit.model;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

	@SerializedName("name")
	private String name;

	@SerializedName("message")
	private String message;

	@SerializedName("email")
	private String email;

	@SerializedName("_id")
	private String _id;

	@SerializedName("level")
	private int level;

	public int getLevel() {	return level;}

	public String getName(){
		return name;
	}

	public String getMessage(){
		return message;
	}

	public String getEmail(){
		return email;
	}

	public String get_id() { return _id;}
}