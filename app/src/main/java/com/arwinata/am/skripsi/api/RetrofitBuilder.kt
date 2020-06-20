package com.arwinata.am.skripsi.api

import com.arwinata.am.skripsi.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor


object RetrofitBuilder {

    private const val baseUrl: String = "http://192.168.1.70:3000"

    fun create(): ApiEndpointInterface {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        val apiUrl: String = baseUrl + "dev/"

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            httpClient.addInterceptor(logging)
        }

        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(apiUrl).client(httpClient.build())
                .build()
        return retrofit.create(ApiEndpointInterface::class.java)
    }
}