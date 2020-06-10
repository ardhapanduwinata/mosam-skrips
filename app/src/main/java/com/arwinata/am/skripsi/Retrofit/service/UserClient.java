package com.arwinata.am.skripsi.Retrofit.service;

import com.arwinata.am.skripsi.Retrofit.model.UserResponse;
import com.arwinata.am.skripsi.Retrofit.model.User;
import com.arwinata.am.skripsi.Retrofit.model.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserClient {

    @POST("register")
    Call<User> createAccount(@Body User user);

    @POST("login")
    Call<UserResponse> loginAcount(@Body LoginRequest loginRequest);

    @GET("login/{id}")
    Call<UserResponse> cekUser(@Path("id") String id);
}
