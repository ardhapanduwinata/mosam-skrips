package com.arwinata.am.skripsi.Retrofit.model;

public class JalaniMisi{
	private String misi;
	private String user;
	private int targettercapai;
	private String status;
	private String message;
	private String claimpoin;
	private String dateselesai;

    public String getClaimpoin() {
        return claimpoin;
    }

    public String getMessage() {
		return message;
	}

	public JalaniMisi(String user, String misi, String status, String claimpoin) {
		this.misi = misi;
		this.user = user;
		this.status = status;
		this.claimpoin = claimpoin;
	}

	public String getMisi(){
		return misi;
	}

	public String getUser(){
		return user;
	}

	public int getTargettercapai(){
		return targettercapai;
	}

	public String getDateselesai() {
		return dateselesai;
	}

	public JalaniMisi(String user, String misi, String status, String claimpoin, String dateselesai) {
		this.misi = misi;
		this.user = user;
		this.status = status;
		this.claimpoin = claimpoin;
		this.dateselesai = dateselesai;
	}

	public JalaniMisi(String user, String misi) {
		this.misi = misi;
		this.user = user;
	}

	public String getStatus(){
		return status;
	}
}
