package com.arwinata.am.skripsi.Retrofit.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataJalaniMisi{

	@SerializedName("count")
	private int count;

	@SerializedName("jalanimisi")
	private List<JalaniMisi> jalanimisi;

	public int getCount(){
		return count;
	}

	public List<JalaniMisi> getJalanimisi(){
		return jalanimisi;
	}
}