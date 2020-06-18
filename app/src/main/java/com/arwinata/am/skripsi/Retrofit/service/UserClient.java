package com.arwinata.am.skripsi.Retrofit.service;

import com.arwinata.am.skripsi.Retrofit.model.EditProfileReq;
import com.arwinata.am.skripsi.Retrofit.model.TabunganResponse;
import com.arwinata.am.skripsi.Retrofit.model.UserResponse;
import com.arwinata.am.skripsi.Retrofit.model.User;
import com.arwinata.am.skripsi.Retrofit.model.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserClient {

    @POST("register")
    Call<User> createAccount(@Body User user);

    @POST("login")
    Call<UserResponse> loginAcount(@Body LoginRequest loginRequest);

    @GET("login/{id}")
    Call<UserResponse> cekUser(@Path("id") String id);

    @PATCH("updateProfile/{id}")
    Call<UserResponse> updateProfile(@Path("id") String id, @Body EditProfileReq edtprofrequest);

    @GET("tabungan/{id}")
    Call<TabunganResponse> cekTabungan(@Path("id") String id);

    @POST("tabungan")
    Call<TabunganResponse> buatTabungan(@Body TabunganResponse tabunganResponse);
}
