package com.arwinata.am.skripsi.Retrofit.model;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("password")
	private String password;

	@SerializedName("name")
	private String name;

	@SerializedName("level")
	private int level;

	@SerializedName("email")
	private String email;

    public User(String email, String name, String password, int level) {
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
}