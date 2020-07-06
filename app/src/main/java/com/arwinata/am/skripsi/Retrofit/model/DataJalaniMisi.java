package com.arwinata.am.skripsi.Retrofit.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataJalaniMisi{

	@SerializedName("count")
	private int count;

	@SerializedName("jalanimisi")
	private List<JalanimisiItem> jalanimisi;

	public int getCount(){
		return count;
	}

	public List<JalanimisiItem> getJalanimisi(){
		return jalanimisi;
	}
}