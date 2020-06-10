package com.arwinata.am.skripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arwinata.am.skripsi.Retrofit.CheckingConnection;
import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;

public class Dashboard extends AppCompatActivity{
    SharedPrefManager sharedPrefManager;
    TextView username, botolbesar, botolsedang, gelaskecil, logout;
    Button nabung;
    String idUser, namauser;
    CheckingConnection checkingConnection = new CheckingConnection();
    Context getContext = Dashboard.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent i = getIntent();
        idUser = i.getStringExtra("idUser");
        namauser = i.getStringExtra("username");
        checkingConnection.cekUser(idUser, getContext);

        username = findViewById(R.id.tvNamaUser_dash);
        botolbesar = findViewById(R.id.tvJml15l);
        botolsedang = findViewById(R.id.tvJml600ml);
        gelaskecil = findViewById(R.id.tvtvJml300ml);
        nabung = findViewById(R.id.btnNabung);
        logout = findViewById(R.id.buttonLogout_dash);

        username.setText("Hallo "+namauser+" selamat datang!");

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
    }
}
