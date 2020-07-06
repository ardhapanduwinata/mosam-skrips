package com.arwinata.am.skripsi.bankActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arwinata.am.skripsi.R;
import com.arwinata.am.skripsi.Retrofit.CheckingConnection;
import com.arwinata.am.skripsi.Retrofit.model.Nabung;
import com.arwinata.am.skripsi.Retrofit.model.UserResponse;
import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NambahTabungan extends AppCompatActivity {

    TextView pengguna, btnback;
    String user, bank;
    EditText edtbotolA, edtbotolB, edtgelas;
    Button btntambah;
    SharedPrefManager sharedPrefManager;
    Date currentTime = Calendar.getInstance().getTime();
    CheckingConnection ck;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nambah_tabungan);
        sharedPrefManager = new SharedPrefManager(this);
        ck = new CheckingConnection();

        pengguna = findViewById(R.id.tvPengguna);
        edtbotolA = findViewById(R.id.edtjmlbotolA);
        edtbotolB = findViewById(R.id.edtjmlbotolB);
        edtgelas = findViewById(R.id.edtjmlgelas);
        btntambah = findViewById(R.id.btntambah);
        btnback = findViewById(R.id.btn_backTambahtabungan);

        user = sharedPrefManager.getSP_iduser();
        bank = sharedPrefManager.getSP_idbank();

        cekuser(user, this);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NambahTabungan.this, BankDashboard.class);
                finish();
                startActivity(i);
                finish();
            }
        });

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                transaksiNabung(user, bank);
                transaksiNabung(bank, bank);
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
                String namauser = response.body().getName();
                pengguna.setText("Nama Nasabah: "+namauser);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void transaksiNabung(String user, String bank){
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

        int jmlbotolA = Integer.parseInt(edtbotolA.getText().toString());
        int jmlbotolB = Integer.parseInt(edtbotolB.getText().toString());
        int jmlgelas = Integer.parseInt(edtgelas.getText().toString());

        Nabung nabung = new Nabung(user, bank, jmlbotolA, jmlbotolB, jmlgelas, currentTime);

        //mendapatkan client & memanggil object
        UserClient client = retrofit.create(UserClient.class);
        Call<Nabung> call = client.transaksiNabung(nabung);

        call.enqueue(new Callback<Nabung>() {
            @Override
            public void onResponse(Call<Nabung> call, Response<Nabung> response) {
                if(response.isSuccessful()){
                    Toast.makeText(NambahTabungan.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    edtbotolA.setText("");
                    edtbotolB.setText("");
                    edtgelas.setText("");
                }
            }

            @Override
            public void onFailure(Call<Nabung> call, Throwable t) {
                Toast.makeText(NambahTabungan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
