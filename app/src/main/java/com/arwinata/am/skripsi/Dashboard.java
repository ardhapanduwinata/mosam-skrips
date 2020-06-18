package com.arwinata.am.skripsi;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arwinata.am.skripsi.Retrofit.CheckingConnection;
import com.arwinata.am.skripsi.Retrofit.model.TabunganResponse;
import com.arwinata.am.skripsi.Retrofit.model.UserResponse;
import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;

public class Dashboard extends AppCompatActivity{
    SharedPrefManager sharedPrefManager;
    TextView username, tvjmlbotolA, tvjmlbotolB, tvjmlgelas, logout;
    ImageView bus, misi, setting;
    Button nabung;
    String namauser;
    int jmlbotolA, jmlbotolB, jmlgelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sharedPrefManager = new SharedPrefManager(this);

        String user = sharedPrefManager.getSP_iduser();
        cekuser(user, this);
        cektabungan(user, this);

        username = findViewById(R.id.tvNamaUser_dash);
        tvjmlbotolA = findViewById(R.id.tvJml15l);
        tvjmlbotolB = findViewById(R.id.tvJml600ml);
        tvjmlgelas = findViewById(R.id.tvtvJml300ml);
        nabung = findViewById(R.id.btnNabung);
        logout = findViewById(R.id.buttonLogout_dash);
        bus = findViewById(R.id.ivBusDash);
        misi = findViewById(R.id.ivMisiDash);
        setting = findViewById(R.id.ivSettingDash);

        nabung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, QrCode.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.sp_sudahLogin, false);
                startActivity(new Intent(Dashboard.this, Login.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, UserSetting.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void cekuser(String idUser, final Context context) {
        //membuat okhttp client
        OkHttpClient.Builder okhttp = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttp.addInterceptor(logging);

        //membuat instance retrofit
        String BASE_URL = "http://192.168.1.70:3000";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp.build());

        Retrofit retrofit = builder.build();

        //mendapatkan client & memanggil object
        UserClient client = retrofit.create(UserClient.class);
        Call<UserResponse> call = client.cekUser(idUser);

        call.enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                namauser = response.body().getName();
                username.setText("Hallo "+namauser+" selamat datang!");
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cektabungan(String iduser, final Context context){
        //membuat okhttp client
        OkHttpClient.Builder okhttp = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttp.addInterceptor(logging);

        //membuat instance retrofit
        String BASE_URL = "http://192.168.1.70:3000";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp.build());

        Retrofit retrofit = builder.build();

        //mendapatkan client & memanggil object
        final UserClient client = retrofit.create(UserClient.class);
        Call<TabunganResponse> call = client.cekTabungan(iduser);

        call.enqueue(new Callback<TabunganResponse>() {

            @Override
            public void onResponse(Call<TabunganResponse> call, Response<TabunganResponse> response) {
                if(response.isSuccessful()){
                    if (response.body().getMessage().equals("Tabungan tidak ditemukan!")) {

                    } else {
                        jmlbotolA = response.body().getJmlbotolA();
                        jmlbotolB = response.body().getJmlbotolB();
                        jmlgelas = response.body().getJmlgelas();

                        tvjmlbotolA.setText(Integer.toString(jmlbotolA));
                        tvjmlbotolB.setText(Integer.toString(jmlbotolB));
                        tvjmlgelas.setText(Integer.toString(jmlgelas));
                    }
                }
            }

            @Override
            public void onFailure(Call<TabunganResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
