package com.arwinata.am.skripsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arwinata.am.skripsi.Retrofit.CheckingConnection;
import com.arwinata.am.skripsi.Retrofit.model.DataTiket;
import com.arwinata.am.skripsi.Retrofit.model.Tabungan;
import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;
import com.arwinata.am.skripsi.bankActivity.BankDashboard;
import com.arwinata.am.skripsi.bankActivity.QrCodeScanner;

public class Tiket extends AppCompatActivity {
    int CAMERA_PERMISSION_CODE = 1;
    ImageView[][] sticker = new ImageView[3][6];

    final int[][] ivViewId = new int[][]{
            {R.id.ivsticker01, R.id.ivsticker02, R.id.ivsticker03, R.id.ivsticker04, R.id.ivsticker05, R.id.ivsticker06},
            {R.id.ivsticker11, R.id.ivsticker12, R.id.ivsticker13, R.id.ivsticker14, R.id.ivsticker15, R.id.ivsticker16},
            {R.id.ivsticker21, R.id.ivsticker22, R.id.ivsticker23, R.id.ivsticker24, R.id.ivsticker25, R.id.ivsticker26}
    };

    SharedPrefManager sharedPrefManager;
    CheckingConnection ck;
    Button tukarvoucher, back, naikbis;
    TextView tikettersimpan;
    private String jmltabungan;

    public String getJmltabungan() {
        return jmltabungan;
    }

    public void setJmltabungan(String jmltabungan) {
        this.jmltabungan= jmltabungan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket);

        sharedPrefManager = new SharedPrefManager(this);
        ck = new CheckingConnection();

        tukarvoucher = findViewById(R.id.btntukarsticker);
        naikbis = findViewById(R.id.btnnaikbis);
        back = findViewById(R.id.btn_back);

        naikbis.setEnabled(false);

        cekTiket(sharedPrefManager.getSP_iduser());
        cektabungan(sharedPrefManager.getSP_iduser(), getApplicationContext());

        tukarvoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getJmltabungan().equals("0") || getJmltabungan().isEmpty()){
                    new AlertDialog.Builder(Tiket.this)
                            .setTitle("Total tabungan anda kosong!")
                            .setMessage("Anda perlu menabung terlebih dahulu jika ingin menukarkan tiket :(")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(Tiket.this, QrCode.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .create().show();

                } else {
                    Intent i = new Intent(Tiket.this, TukarTiket.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        naikbis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(Tiket.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    startActivity(new Intent(Tiket.this, QrCodeScanner.class));
                } else {
                    verifyPermissions();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Tiket.this, Dashboard.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void cekTiket(String user) {
        //membuat okhttp client
//        OkHttpClient.Builder okhttp = new OkHttpClient.Builder();
//
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        okhttp.addInterceptor(logging);

        //membuat instance retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ck.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create());
//                .client(okhttp.build());

        Retrofit retrofit = builder.build();

        final UserClient client = retrofit.create(UserClient.class);
        Call<DataTiket> call = client.cekTiket(user);

        call.enqueue(new Callback<DataTiket>() {
            @Override
            public void onResponse(Call<DataTiket> call, Response<DataTiket> response) {
                if(response.isSuccessful())
                {
                    tikettersimpan = findViewById(R.id.tiketersimpan);
                    int jumlahvoucher = response.body().getJmlTiket();
                    int jumlah=0;
                    int sisa = jumlahvoucher-18;

                    if(sisa <=0)
                    {
                        tikettersimpan.setText("0");
                    } else {

                        tikettersimpan.setText(Integer.toString(sisa));
                    }

                    for(int i=0; i<3; i++)
                    {
                        for(int j=0; j<6; j++)
                        {
                            sticker[i][j] = findViewById(ivViewId[i][j]);

                            jumlah++;

                            if(jumlahvoucher<=18)
                            {
                                if(jumlah<=jumlahvoucher)
                                {
                                    sticker[i][j].setImageResource(R.drawable.stickertiket);
                                }
                            } else if(jumlahvoucher>18){
                                sticker[i][j].setImageResource(R.drawable.stickertiket);
                            }
                        }
                    }
                }
                if(response.body().getJmlTiket() == 0){
                    naikbis.setEnabled(false);
                } else {
                    naikbis.setEnabled(true);
                }
            }
            @Override
            public void onFailure(Call<DataTiket> call, Throwable t) {
            }
        });
    }

    public void cektabungan(String iduser, final Context context){
        //membuat okhttp client
//        OkHttpClient.Builder okhttp = new OkHttpClient.Builder();
//
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        okhttp.addInterceptor(logging);

        //membuat instance retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ck.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create());
//                .client(okhttp.build());

        Retrofit retrofit = builder.build();

        //mendapatkan client & memanggil object
        final UserClient client = retrofit.create(UserClient.class);
        Call<Tabungan> call = client.cekTabungan(iduser);

        call.enqueue(new Callback<Tabungan>() {

            @Override
            public void onResponse(Call<Tabungan> call, Response<Tabungan> response) {
                int totaltabungan = response.body().getJmlbotolA()+response.body().getJmlbotolB()+response.body().getJmlgelas();

                setJmltabungan(Integer.toString(totaltabungan));
            }

            @Override
            public void onFailure(Call<Tabungan> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifyPermissions(){
        Log.d("TAG", "verifyPermissions: asking user for permissions");
        String[] permissions = {Manifest.permission.CAMERA};

        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
            new AlertDialog.Builder(this)
                    .setTitle("Izin diperlukan!")
                    .setMessage("Aplikasi perlu mengakses kamera anda untuk melanjutkan aksi :(")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(Tiket.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERMISSION_CODE) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent i = new Intent(Tiket.this, ScanTerminal.class);
                startActivity(i);
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "Izin Ditolak", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
