package com.arwinata.am.skripsi.api

import com.arwinata.am.skripsi.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpointInterface {


    //Register
    @FormUrlEncoded
    @POST("register")
    fun createAccount(
            @Body user: User?
    ): Call<User>

    //login
    @FormUrlEncoded
    @POST("login")
    fun loginAcount(
            @Body loginRequest: LoginRequest?
    ): Call<UserResponse?>?

    //cek user
    @GET("login/{id}")
    fun cekUser(
            @Path("id") id: String?
    ): Call<UserResponse?>?

    //update profil
    @PATCH("updateProfile/{id}")
    fun updateProfile(
            @Path("id") id: String?,
            @Body edtprofrequest: EditProfileReq?
    ): Call<UserResponse?>?

    //tabungan
    @GET("tabungan/{id}")
    fun cekTabungan(
            @Path("id") id: String?
    ): Call<TabunganResponse?>?

    @FormUrlEncoded
    @POST("tabungan")
    fun buatTabungan(
            @Body tabunganResponse: TabunganResponse?
    ): Call<TabunganResponse?>?

//    //Login
//    @FormUrlEncoded
//    @POST("user/login")
//    fun getLogin(
//            @Field("username") username: String?,
//            @Field("password") password: String?
//    ): Call<LoginModel>
//
//    //Dashboar
//    @GET("dashboard")
//    fun getDashboard(
//            @Header("Authorization") auth: String?,
//            @Header("Ver") ver: String?,
//            @Query("nip") nip: String?
//    ): Call<DashboardModel>
//
//    //Profil
//    @GET("user/profil")
//    fun getProfil(
//            @Header("Authorization") auth: String?,
//            @Query("nip") nip: String?
//    ): Call<ProfilModel>
//
//    @FormUrlEncoded
//    @POST("user/edit_profil")
//    fun ubahProfil(
//            @Header("Authorization") authorization: String?,
//            @Field("nip") nip: String?,
//            @Field("no_telp") telp: String?,
//            @Field("email") email: String?
//    ): Call<GeneralResponseModel>
//
//    @FormUrlEncoded
//    @POST("user/edit_password")
//    fun ubahPassword(
//            @Header("Authorization") authorization: String?,
//            @Field("nip") nip: String?,
//            @Field("old_password") oldPass: String?,
//            @Field("new_password") newPass: String?,
//            @Field("renew_password") renewPass: String?
//    ): Call<GeneralResponseModel>
//
//
//    //Target SKP
//    @GET("targetskp")
//    fun getListTarget(
//            @Header("Authorization") authorization: String,
//            @Query("nip") user_nip: String
//    ): Call<SkpModel>
//
//    @FormUrlEncoded
//    @POST("targetskp/add")
//    fun tambahTarget(
//            @Header("Authorization") authorization: String,
//            @Field("nip") nip: String,
//            @Field("uraian") uraian: String,
//            @Field("ak") ak: String,
//            @Field("output") output: String,
//            @Field("output_stn") output_stn: String,
//            @Field("mutu") mutu: String,
//            @Field("waktu") waktu: String,
//            @Field("waktu_stn") waktu_stn: String,
//            @Field("biaya") biaya: String
//    ): Call<GeneralResponseModel>
//
//    @FormUrlEncoded
//    @POST("targetskp/delete")
//    fun hapusTarget(
//            @Header("Authorization") authorization: String,
//            @Field("id_target") id_target: Long,
//            @Field("id_skp") id_skp: Long
//    ): Call<GeneralResponseModel>
//
//    @FormUrlEncoded
//    @POST("targetskp/edit")
//    fun editTarget(
//            @Header("Authorization") authorization: String,
//            @Field("nip") nip: String,
//            @Field("id_target") id_target: Long,
//            @Field("uraian") uraian: String,
//            @Field("ak") ak: String,
//            @Field("output") output: String,
//            @Field("output_stn") output_stn: String,
//            @Field("mutu") mutu: String,
//            @Field("waktu") waktu: String,
//            @Field("waktu_stn") waktu_stn: String,
//            @Field("biaya") biaya: String
//    ): Call<GeneralResponseModel>
//
//    //Tugas Tambahan
//    @GET("tugas_tambahan")
//    fun getListTugas(
//            @Header("Authorization") authorization: String?,
//            @Query("nip") user_nip: String?
//    ): Call<TugasTambahanModel>
//
//    @FormUrlEncoded
//    @POST("tugas_tambahan/save")
//    fun tambahTugas(
//            @Header("Authorization") authorization: String,
//            @Field("nip") user_nip: String,
//            @Field("uraian") uraian: String
//    ): Call<GeneralResponseModel>
//
//    @FormUrlEncoded
//    @POST("tugas_tambahan/delete")
//    fun hapusTugas(
//            @Header("Authorization") authorization: String,
//            @Field("id_tambahan") id_tambahan: String
//    ): Call<GeneralResponseModel>
//
//    @FormUrlEncoded
//    @POST("tugas_tambahan/edit")
//    fun editTugas(
//            @Header("Authorization") authorization: String,
//            @Field("nip") nip: String,
//            @Field("id_tambahan") id_tambahan: String,
//            @Field("uraian") uraian: String
//    ): Call<GeneralResponseModel>
//
//    //Penilaian Target
//    @GET("penetapan_target/bawahan")
//    fun getListBawahan(
//            @Header("Authorization") authorization: String?,
//            @Query("nip") id_user: String?,
//            @Query("tahun") tahun: String?
//    ): Call<BawahanModel>
//
//    @GET("penetapan_target/bawahan_target")
//    fun getListTargetBawahan(
//            @Header("Authorization") authorization: String,
//            @Query("nip_kodeja") nip_kodeja: String,
//            @Query("tahun") tahun: String
//    ): Call<TargetBawahanModel>
//
//    @FormUrlEncoded
//    @POST("penetapan_target/konfirmasi")
//    fun setKonfirmasiTarget(
//            @Header("Authorization") authorization: String,
//            @Field("action") action: String,
//            @Field("nip_kodeja") nip_kodeja: String,
//            @Field("tahun") tahun: String
//    ): Call<GeneralResponseModel>
//
//    //Aktifitas
//    @GET("aktivitas_skp")
//    fun getListAktifitas(
//            @Header("Authorization") authorization: String,
//            @Query("nip") nip: String,
//            @Query("bulan") bulan: String,
//            @Query("tahun") tahun: String
//    ): Call<AktifitasModel>
//
//    @GET("aktivitas_skp")
//    fun searchAktifitas(
//            @Header("Authorization") authorization: String?,
//            @Query("nip") nip: String?,
//            @Query("bulan") bulan: String?,
//            @Query("tahun") tahun: String?,
//            @Query("word") kata: String?
//    ): Call<AktifitasModel>
//
//    @GET("aktivitas_skp/search")
//    fun getListKamus(
//            @Header("Authorization") authorization: String,
//            @Query("word") word: String
//    ): Call<KamusModel>
//
//    @FormUrlEncoded
//    @POST("aktivitas_skp/save")
//    fun addAktifitas(
//            @Header("Authorization") authorization: String,
//            @Field("nip") nip: String,
//            @Field("act") act: String,
//            @Field("log_id") logId: String?,
//            @Field("tanggal") tanggal: String,
//            @Field("aktifitas") aktifitas: String,
//            @Field("bk_id") bk_id: String,
//            @Field("target") target: String?,
//            @Field("jam_mulai") jam_mulai: String,
//            @Field("jam_berakhir") jam_berakhir: String,
//            @Field("output") output: String,
//            @Field("output_stn") output_stn: String,
//            @Field("catatan") catatan: String
//    ): Call<GeneralResponseModel>
//
//    @GET("aktivitas_skp/detail")
//    fun getAktifitasDetail(
//            @Header("Authorization") authorization: String?,
//            @Query("log_id") log_id: String?
//    ): Call<AktifitasModel>
//
//    @FormUrlEncoded
//    @POST("aktivitas_skp/delete")
//    fun hapusAktivitas(
//            @Header("Authorization") authorization: String?,
//            @Field("log_id") log_id: String?
//    ): Call<GeneralResponseModel>
//
//    //Realisasi
//    @GET("realisasi_skp")
//    fun getRealisasi(
//            @Header("Authorization") authorization: String?,
//            @Query("nip") nip: String?
//    ): Call<RealisasiModel>
//
//    @FormUrlEncoded
//    @POST("realisasi_skp/save")
//    fun addRealisasi(
//            @Header("Authorization") authorization: String,
//            @Field("nip") nip: String,
//            @Field("id_tkerja") idTkerja: String,
//            @Field("output") output: String,
//            @Field("mutu") mutu: String,
//            @Field("waktu") waktu: String,
//            @Field("biaya") biaya: String
//    ): Call<GeneralResponseModel>
//
//    @FormUrlEncoded
//    @POST("realisasi_skp/save")
//    fun editRealisasi(
//            @Header("Authorization") authorization: String,
//            @Field("nip") nip: String,
//            @Field("id_realisasi") idRealisasi: String,
//            @Field("id_tkerja") idTkerja: String,
//            @Field("output") output: String,
//            @Field("mutu") mutu: String,
//            @Field("waktu") waktu: String,
//            @Field("biaya") biaya: String
//    ): Call<GeneralResponseModel>
//
//    //Kreativitas
//    @GET("kreativitas")
//    fun getKreativitas(
//            @Header("Authorization") authorization: String?,
//            @Query("nip") nip: String?
//    ): Call<KreativitasModel>
//
//    @Multipart
//    @POST("kreativitas/save")
//    fun tambahKreativitas(
//            @Header("Authorization") authorization: String?,
//            @PartMap partMap: HashMap<String, @JvmSuppressWildcards RequestBody>?,
//            @Part imgFile: MultipartBody.Part?
//    ): Call<GeneralResponseModel>
//
//    @FormUrlEncoded
//    @POST("kreativitas/delete")
//    fun hapusKreativitas(
//            @Header("Authorization") authorization: String,
//            @Field("id_kreatif") id_kreatif: String
//    ): Call<GeneralResponseModel>
//
//    @Multipart
//    @POST("kreativitas/edit")
//    fun editKreativitas(
//            @Header("Authorization") authorization: String?,
////        @Field("nip") nip: String,
////        @Field("id_kreatif") id_kreatif: String,
////        @Field("uraian") uraian: String,
////        @Field("imgFile") imgFile: String
//            @PartMap partMap: HashMap<String, @JvmSuppressWildcards RequestBody>?,
//            @Part imgFile: MultipartBody.Part?
//    ): Call<GeneralResponseModel>
//
//    //Penilaian
//    @GET("penilaian_aktivitas/bawahan_aktivitas")
//    fun getListAktifitasBawahan(
//            @Header("Authorization") authorization: String?,
//            @Query("nip") nip: String?,
//            @Query("tahun") tahun: String?,
//            @Query("bulan") bulan: String?
//    ): Call<AktifitasModel>
//
//    @FormUrlEncoded
//    @POST("penilaian_aktivitas/save")
//    fun tolakAktivitas(
//            @Header("Authorization") auth: String?,
//            @Field("act") act: String?,
//            @Field("log_id") logId: String?,
//            @Field("keterangan") keterangan: String?
//    ): Call<GeneralResponseModel>
//
//    @FormUrlEncoded
//    @POST("penilaian_aktivitas/cancel_decline")
//    fun batalTolakAktivitas(
//            @Header("Authorization") auth: String?,
//            @Field("log_id") logId: String?
//    ): Call<GeneralResponseModel>
//
//    /*
//    status : yes = diterima
//    status : no = batal diterima
//    */
//    @FormUrlEncoded
//    @POST("penilaian_aktivitas/validasi")
//    fun terimaAktivitas(
//            @Header("Authorization") auth: String?,
//            @Field("log_id") logId: String?,
//            @Field("status") status: String?
//    ): Call<GeneralResponseModel>
//
//    @GET("penilaian_perilaku")
//    fun getListPenilaian(
//            @Header("Authorization") auth: String?,
//            @Query("nip") nip: String?,
//            @Query("tahun") tahun: String?
//    ): Call<PerilakuModel>
//
//    @FormUrlEncoded
//    @POST("realisasi_skp/confirm")
//    fun terimaRealisasi(
//            @Header("Authorization") auth: String?,
//            @Field("nip") nip: String?,
//            @Field("tahun") tahun: String?
//    ): Call<GeneralResponseModel>
//
//    @FormUrlEncoded
//    @POST("realisasi_skp/cancel_confirm")
//    fun batalTerimaRealisasi(
//            @Header("Authorization") auth: String?,
//            @Field("nip") nip: String?,
//            @Field("tahun") tahun: String?
//    ): Call<GeneralResponseModel>

}









