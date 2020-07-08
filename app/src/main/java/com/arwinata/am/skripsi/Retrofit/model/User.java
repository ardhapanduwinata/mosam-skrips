package com.arwinata.am.skripsi.Retrofit.model;

import com.google.gson.annotations.SerializedName;

public class User{
	@SerializedName("password")
	private String password;
	@SerializedName("level")
	private String level;
	@SerializedName("name")
	private String name;
	@SerializedName("id")
	private String id;
	@SerializedName("message")
	private String message;

	public User(String email, String name, String password, String level) {
		this.password = password;
		this.name = name;
		this.email = email;
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

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
