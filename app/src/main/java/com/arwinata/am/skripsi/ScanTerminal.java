package com.arwinata.am.skripsi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.arwinata.am.skripsi.Retrofit.CheckingConnection;
import com.arwinata.am.skripsi.Retrofit.model.DataTiket;
import com.arwinata.am.skripsi.Retrofit.model.HistoriTransaksi;
import com.arwinata.am.skripsi.Retrofit.model.Terminal;
import com.arwinata.am.skripsi.Retrofit.model.User;
import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;
import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ScanTerminal extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    SharedPrefManager sharedPrefManager;
    private ZXingScannerView mScannerView;
    CheckingConnection ck = new CheckingConnection();

    Date currentTime = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    String datenow = df.format(currentTime);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        sharedPrefManager = new SharedPrefManager(this);
        setContentView(mScannerView);
    }
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(ScanTerminal.this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        final String terminal = rawResult.getText();

        final String user = sharedPrefManager.getSP_iduser();

        //membuat okhttp client
//        OkHttpClient.Builder okhttp = new OkHttpClient.Builder();
//
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        okhttp.addInterceptor(logging);

        //membuat instance retrofit
        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ck.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create());
//                .client(okhttp.build());

        final Retrofit retrofit = builder.build();

        //mendapatkan client & memanggil object
        final UserClient client = retrofit.create(UserClient.class);

        Call<DataTiket> call2 = client.cekTiket(user);
        call2.enqueue(new Callback<DataTiket>() {
            @Override
            public void onResponse(Call<DataTiket> call, Response<DataTiket> response) {
                int tiketlama = response.body().getJmlTiket();
                DataTiket tiketbaru = new DataTiket( tiketlama-1);
                Call<DataTiket> call3 = client.updateTiket(user, tiketbaru);
                call3.enqueue(new Callback<DataTiket>() {
                    @Override
                    public void onResponse(Call<DataTiket> call, Response<DataTiket> response) {
                        Terminal naikbis = new Terminal(terminal, user, datenow);
                        Call<Terminal> call4 = client.naikbis(naikbis);
                        call4.enqueue(new Callback<Terminal>() {
                            @Override
                            public void onResponse(Call<Terminal> call, Response<Terminal> response) {}
                            @Override
                            public void onFailure(Call<Terminal> call, Throwable t) {}
                        });

                        Call<Terminal> call5 = client.cekTerminal(terminal);
                        call5.enqueue(new Callback<Terminal>() {
                            @Override
                            public void onResponse(Call<Terminal> call, Response<Terminal> response) {

                                new AlertDialog.Builder(ScanTerminal.this)
                                        .setTitle("Anda sudah naik bis")
                                        .setMessage("Anda sudah menukarkan 1 tiket untuk naik bis dari "+response.body().getNamaTerminal())
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(ScanTerminal.this, Tiket.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(ScanTerminal.this, Tiket.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .create().show();

                                addHistory(user, "Naik bis dari: "+response.body().getNamaTerminal(), datenow);
                            }
                            @Override
                            public void onFailure(Call<Terminal> call, Throwable t) {}
                        });
                    }
                    @Override
                    public void onFailure(Call<DataTiket> call, Throwable t) {}
                });
            }
            @Override
            public void onFailure(Call<DataTiket> call, Throwable t) {

            }
        });
    }

    public void addHistory(String user, String namatransaksi, String datenow)
    {
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

        HistoriTransaksi historiTransaksi = new HistoriTransaksi(user, namatransaksi, datenow);

        final UserClient client = retrofit.create(UserClient.class);
        Call<HistoriTransaksi> call = client.addHistori(historiTransaksi);
        call.enqueue(new Callback<HistoriTransaksi>() {
            @Override
            public void onResponse(Call<HistoriTransaksi> call, Response<HistoriTransaksi> response) {

            }

            @Override
            public void onFailure(Call<HistoriTransaksi> call, Throwable t) {

            }
        });
    }
}
