package com.arwinata.am.skripsi.Retrofit.service;

import com.arwinata.am.skripsi.HistoriAdapter;
import com.arwinata.am.skripsi.Retrofit.model.DataTiket;
import com.arwinata.am.skripsi.Retrofit.model.EditProfileReq;
import com.arwinata.am.skripsi.Retrofit.model.HistoriTransaksi;
import com.arwinata.am.skripsi.Retrofit.model.JalaniMisi;
import com.arwinata.am.skripsi.Retrofit.model.Misi;
import com.arwinata.am.skripsi.Retrofit.model.Nabung;
import com.arwinata.am.skripsi.Retrofit.model.PoinDanVoucher;
import com.arwinata.am.skripsi.Retrofit.model.Tabungan;
import com.arwinata.am.skripsi.Retrofit.model.DataTukarTiket;
import com.arwinata.am.skripsi.Retrofit.model.Terminal;
import com.arwinata.am.skripsi.Retrofit.model.UserResponse;
import com.arwinata.am.skripsi.Retrofit.model.User;
import com.arwinata.am.skripsi.Retrofit.model.LoginRequest;

import java.util.List;

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

    @PATCH("tiket/{id}")
    Call<DataTiket> updateTiket(@Path("id") String id, @Body DataTiket jmlVoucher);

    @GET("tiket/{id}")
    Call<DataTiket> cekTiket(@Path("id") String id);

    @POST("tiket")
    Call<DataTiket> buatTiket(@Body DataTiket voucher);

    @POST("tukartiket")
    Call<DataTukarTiket> tukarTiket(@Body DataTukarTiket dataTukarTiket);

    @GET("poin/{id}")
    Call<PoinDanVoucher> cekPoin(@Path("id") String id);

    @POST("poin")
    Call<PoinDanVoucher> buatPoin(@Body PoinDanVoucher poinDanVoucher);

    @PATCH("poin/{id}")
    Call<PoinDanVoucher> updatePoin(@Path("id") String id, @Body PoinDanVoucher poinDanVoucher);

    @GET("misi")
    Call<List<Misi>> daftarMisi();

    @POST("jalanimisi")
    Call<JalaniMisi> buatJalaniMisi(@Body JalaniMisi jalaniMisi);

    @PATCH("jalanimisi")
    Call<JalaniMisi> updateJalaniMisi(@Body JalaniMisi jalanimisi);

    @GET("nabung/{id}")
    Call<Nabung> getAllNabung(@Path("id") String id);

    @GET("tukartiket/{id}")
    Call<DataTukarTiket> getAllTukarTiket(@Path("id") String id);

    @GET("terminal/{id}")
    Call<Terminal> cekTerminal(@Path("id") String id);

    @POST("naikbis")
    Call<Terminal> naikbis(@Body Terminal terminal);

    @POST("histori")
    Call<HistoriTransaksi> addHistori(@Body HistoriTransaksi historiTransaksi);
}
