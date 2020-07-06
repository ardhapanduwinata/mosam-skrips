package com.arwinata.am.skripsi.Retrofit.service;

import com.arwinata.am.skripsi.Retrofit.model.DataVoucher;
import com.arwinata.am.skripsi.Retrofit.model.EditProfileReq;
import com.arwinata.am.skripsi.Retrofit.model.Nabung;
import com.arwinata.am.skripsi.Retrofit.model.Poin;
import com.arwinata.am.skripsi.Retrofit.model.Tabungan;
import com.arwinata.am.skripsi.Retrofit.model.TukarVoucher;
import com.arwinata.am.skripsi.Retrofit.model.UserResponse;
import com.arwinata.am.skripsi.Retrofit.model.User;
import com.arwinata.am.skripsi.Retrofit.model.LoginRequest;
import com.arwinata.am.skripsi.TukarTiket;
import com.arwinata.am.skripsi.Voucher;

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
    Call<Tabungan> cekTabungan(@Path("id") String id);

    @POST("tabungan")
    Call<Tabungan> buatTabungan(@Body Tabungan tabungan);

    @POST("nabung")
    Call<Nabung> transaksiNabung(@Body Nabung nabung);

    @PATCH("voucher/{id}")
    Call<DataVoucher> updateVoucher(@Path("id") String id, @Body DataVoucher jmlVoucher);

    @GET("voucher/{id}")
    Call<DataVoucher> cekVoucher(@Path("id") String id);

    @POST("voucher")
    Call<DataVoucher> buatVoucher(@Body DataVoucher voucher);

    @POST("tukarvoucher")
    Call<TukarVoucher> tukarVoucher( @Body TukarVoucher tukarVoucher);

    @POST("poin")
    Call<Poin> buatPoin(@Body Poin poin);
}
