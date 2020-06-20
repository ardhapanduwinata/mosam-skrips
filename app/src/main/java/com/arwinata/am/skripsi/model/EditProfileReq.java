package com.arwinata.am.skripsi.model;

public class EditProfileReq {
    public String email, name, password;

    public  EditProfileReq(String name, String email){
        this.email = email;
        this.name = name;
    }

    public EditProfileReq(String name, String email, String password){
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public EditProfileReq(String password){
        this.password = password;
    }
}
