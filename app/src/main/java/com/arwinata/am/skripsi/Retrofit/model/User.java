package com.arwinata.am.skripsi.Retrofit.model;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("password")
	private String password;

	@SerializedName("level")
	private String level;

	@SerializedName("__v")
	private int V;

	@SerializedName("name")
	private String name;

	@SerializedName("_id")
	private String id;

	@SerializedName("message")
	private String message;

	public String getMessage() {
		return message;
	}

	public User(String email, String name, String password, String level) {
		this.password = password;
		this.level = level;
		this.name = name;
		this.email = email;
	}

	@SerializedName("email")
	private String email;

	public String getPassword(){
		return password;
	}

	public String getLevel(){
		return level;
	}

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}
}