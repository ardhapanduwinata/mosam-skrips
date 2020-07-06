
package com.arwinata.am.skripsi.bankActivity;

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

import com.arwinata.am.skripsi.Login;
import com.arwinata.am.skripsi.R;
import com.arwinata.am.skripsi.Retrofit.CheckingConnection;
import com.arwinata.am.skripsi.Retrofit.model.Tabungan;
import com.arwinata.am.skripsi.Retrofit.model.UserResponse;
import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;
import com.arwinata.am.skripsi.UserSetting;

public class BankDashboard extends AppCompatActivity {

    ImageView setting;
    SharedPrefManager sharedPrefManager;
    String iduser, namauser;
    TextView tvuser, tvjmlbotolA, tvjmlbotolB, tvjmlgelas, logout, btnterimatabungan;
    int jmlbotolA, jmlbotolB, jmlgelas;
    CheckingConnection ck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_dashboard);
        sharedPrefManager = new SharedPrefManager(this);
        ck = new CheckingConnection();

        iduser = sharedPrefManager.getSP_idbank();

        cekuser(iduser, this);
        cektabungan(iduser, this);

        logout = findViewById(R.id.buttonLogout_dashbank);
        tvuser = findViewById(R.id.tvNamaUser_dashBank);
        tvjmlbotolA = findViewById(R.id.tvJml15l);
        tvjmlbotolB = findViewById(R.id.tvJml600ml);
        tvjmlgelas = findViewById(R.id.tvtvJml300ml);
        setting = findViewById(R.id.ivSetting);
        btnterimatabungan = findViewById(R.id.btnTerimaNabung);

        btnterimatabungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BankDashboard.this, QrCodeScanner.class));
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.sp_sudahLogin, false);
                startActivity(new Intent(BankDashboard.this, Login.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BankDashboard.this, UserSetting.class);
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
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ck.getBASE_URL())
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
                tvuser.setText("Hallo "+namauser+" selamat datang!");
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
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ck.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp.build());

        Retrofit retrofit = builder.build();

        //mendapatkan client & memanggil object
        final UserClient client = retrofit.create(UserClient.class);
        Call<Tabungan> call = client.cekTabungan(iduser);

        call.enqueue(new Callback<Tabungan>() {

            @Override
            public void onResponse(Call<Tabungan> call, Response<Tabungan> response) {
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
            public void onFailure(Call<Tabungan> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}