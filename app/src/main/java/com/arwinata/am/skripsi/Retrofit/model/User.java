package com.arwinata.am.skripsi.Retrofit.model;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("password")
	private String password;

	@SerializedName("name")
	private String name;

	@SerializedName("level")
	private String level;

	@SerializedName("email")
	private String email;

	@SerializedName("message")
	private String message;

	@SerializedName("_id")
	private String _id;

	public String get_id() {
		return _id;
	}

	public String getMessage() {
		return message;
	}

	public User(String email, String name, String password, String level) {
        this.password = password;
        this.name = name;
        this.level = level;
        this.email = email;
    }

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getLevel() {return level;}
}