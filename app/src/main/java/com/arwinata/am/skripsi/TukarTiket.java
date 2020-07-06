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
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arwinata.am.skripsi.Retrofit.CheckingConnection;
import com.arwinata.am.skripsi.Retrofit.model.DataVoucher;
import com.arwinata.am.skripsi.Retrofit.model.Tabungan;
import com.arwinata.am.skripsi.Retrofit.model.TukarVoucher;
import com.arwinata.am.skripsi.Retrofit.model.User;
import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;

import java.util.Calendar;
import java.util.Date;

public class TukarTiket extends AppCompatActivity {

    Button tambahbotolA, kurangbotolA, tambahbotolB, kurangbotolB,
            tambahgelas, kuranggelas, maxbotolA, maxbotolB, maxgelas,
            back, tukar;

    TextView jmlbotolA, jmlbotolB, jmlgelas;
    EditText edtvoucherbotolA, edtvoucherbotolB, edtvouchergelas;
    SharedPrefManager sharedPrefManager;
    CheckingConnection ck;

    Date currentTime = Calendar.getInstance().getTime();

//    int a=20, b=30, c=50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tukar_tiket);
        sharedPrefManager = new SharedPrefManager(this);
        ck = new CheckingConnection();

        back = findViewById(R.id.btn_back);
        tukar = findViewById(R.id.btntukartiket);
        tambahbotolA = findViewById(R.id.tambahbotolA);
        tambahbotolB = findViewById(R.id.tambahbotolB);
        tambahgelas = findViewById(R.id.tambahgelas);
        kurangbotolA = findViewById(R.id.kurangbotolA);
        kurangbotolB = findViewById(R.id.kurangbotolB);
        kuranggelas = findViewById(R.id.kuranggelas);
        maxbotolA = findViewById(R.id.maxbotolA);
        maxbotolB = findViewById(R.id.maxbotolB);
        maxgelas = findViewById(R.id.maxgelas);
        jmlbotolA = findViewById(R.id.jmlbotolA);
        jmlbotolB = findViewById(R.id.jmlbotolB);
        jmlgelas = findViewById(R.id.jmlgelas);
        edtvoucherbotolA = findViewById(R.id.edtvoucherbotolA);
        edtvoucherbotolB = findViewById(R.id.edtvoucherbotolB);
        edtvouchergelas = findViewById(R.id.edtvouchergelas);

        cektabungan(sharedPrefManager.getSP_iduser(), this);

//        edtvoucherbotolA.setText("0");
//        edtvoucherbotolB.setText("0");
//        edtvouchergelas.setText("0");

        tambahbotolA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int jumlahA = Integer.parseInt(jmlbotolA.getText().toString());
                int jmlvoucher= Integer.parseInt(edtvoucherbotolA.getText().toString());

                if(jumlahA>=3){
                    int ubah;
                    ubah = jumlahA-3;
                    jmlvoucher = jmlvoucher+1;

                    jmlbotolA.setText(Integer.toString(ubah));
                    edtvoucherbotolA.setText(Integer.toString(jmlvoucher));
                } else if(jumlahA<3) {
                    Toast.makeText(TukarTiket.this, "Tabungan anda tidak mencukupi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tambahbotolB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int jumlahB = Integer.parseInt(jmlbotolB.getText().toString());
                int jmlvoucher= Integer.parseInt(edtvoucherbotolB.getText().toString());

                if(jumlahB>=5){
                    int ubah;
                    ubah = jumlahB-5;
                    jmlvoucher = jmlvoucher+1;

                    jmlbotolB.setText(Integer.toString(ubah));
                    edtvoucherbotolB.setText(Integer.toString(jmlvoucher));
                } else if(jumlahB<5) {
                    Toast.makeText(TukarTiket.this, "Tabungan anda tidak mencukupi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tambahgelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int jumlahC = Integer.parseInt(jmlgelas.getText().toString());
                int jmlvoucher= Integer.parseInt(edtvouchergelas.getText().toString());

                if(jumlahC>=10){
                    int ubah;
                    ubah = jumlahC-10;
                    jmlvoucher = jmlvoucher+1;

                    jmlgelas.setText(Integer.toString(ubah));
                    edtvouchergelas.setText(Integer.toString(jmlvoucher));
                } else if(jumlahC<10) {
                    Toast.makeText(TukarTiket.this, "Tabungan anda tidak mencukupi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        kurangbotolA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int jumlahA = Integer.parseInt(jmlbotolA.getText().toString());
                int ubah, jmlvoucher = Integer.parseInt(edtvoucherbotolA.getText().toString());

                if(jmlvoucher != 0){
                    ubah = jumlahA+3;
                    jmlvoucher--;

                    jmlbotolA.setText(Integer.toString(ubah));
                    edtvoucherbotolA.setText(Integer.toString(jmlvoucher));
                } else {
                    Toast.makeText(TukarTiket.this, "Tiket tidak bisa dikurangi lagi!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        kurangbotolB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int jumlahB = Integer.parseInt(jmlbotolB.getText().toString());
                int ubah, jmlvoucher = Integer.parseInt(edtvoucherbotolB.getText().toString());

                if(jmlvoucher != 0){
                    ubah = jumlahB+5;
                    jmlvoucher--;

                    jmlbotolB.setText(Integer.toString(ubah));
                    edtvoucherbotolB.setText(Integer.toString(jmlvoucher));
                }else {
                    Toast.makeText(TukarTiket.this, "Tiket tidak bisa dikurangi lagi!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        kuranggelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int jumlahC = Integer.parseInt(jmlgelas.getText().toString());
                int ubah, jmlvoucher = Integer.parseInt(edtvouchergelas.getText().toString());

                if(jmlvoucher != 0){
                    ubah = jumlahC+10;
                    jmlvoucher--;

                    jmlgelas.setText(Integer.toString(ubah));
                    edtvouchergelas.setText(Integer.toString(jmlvoucher));
                }else {
                    Toast.makeText(TukarTiket.this, "Tiket tidak bisa dikurangi lagi!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        maxbotolA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ubah;
                int jumlahA = Integer.parseInt(jmlbotolA.getText().toString());
                int jmlvoucher= Integer.parseInt(edtvoucherbotolA.getText().toString());

                if(jumlahA%3 != 0){
                    ubah = jumlahA%3;
                    jmlvoucher = jumlahA/3;

                    jmlbotolA.setText(Integer.toString(ubah));
                    edtvoucherbotolA.setText(Integer.toString(jmlvoucher));
                }  else if(jumlahA == 0 || jumlahA<3){
                    Toast.makeText(TukarTiket.this, "Tiket sudah max!", Toast.LENGTH_SHORT).show();
                }else {
                    jmlvoucher = jumlahA/3;
                    ubah = jumlahA-(jmlvoucher*3);

                    jmlbotolA.setText(Integer.toString(ubah));
                    edtvoucherbotolA.setText(Integer.toString(jmlvoucher));
                }
            }
        });

        maxbotolB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ubah;
                int jumlahB = Integer.parseInt(jmlbotolB.getText().toString());
                int jmlvoucher= Integer.parseInt(edtvoucherbotolB.getText().toString());

                if(jumlahB%5 != 0){
                    ubah = jumlahB%5;
                    jmlvoucher = jumlahB/5;

                    jmlbotolA.setText(Integer.toString(ubah));
                    edtvoucherbotolB.setText(Integer.toString(jmlvoucher));
                } else if(jumlahB == 0 || jumlahB<5){
                    Toast.makeText(TukarTiket.this, "Tiket sudah max!", Toast.LENGTH_SHORT).show();
                }else{
                    jmlvoucher = jumlahB/5;
                    ubah = jumlahB-(jmlvoucher*5);

                    jmlbotolB.setText(Integer.toString(ubah));
                    edtvoucherbotolB.setText(Integer.toString(jmlvoucher));
                }
            }
        });

        maxgelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ubah;
                int jumlahC = Integer.parseInt(jmlgelas.getText().toString());
                int jmlvoucher= Integer.parseInt(edtvouchergelas.getText().toString());

                if(jumlahC%10 != 0){
                    ubah = jumlahC%10;
                    jmlvoucher = jumlahC/10;

                    jmlgelas.setText(Integer.toString(ubah));
                    edtvouchergelas.setText(Integer.toString(jmlvoucher));
                } else if(jumlahC == 0 || jumlahC<10){
                    Toast.makeText(TukarTiket.this, "Tiket sudah max!", Toast.LENGTH_SHORT).show();
                } else {
                    jmlvoucher = jumlahC/10;
                    ubah = jumlahC-(jmlvoucher*10);

                    jmlgelas.setText(Integer.toString(ubah));
                    edtvouchergelas.setText(Integer.toString(jmlvoucher));
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TukarTiket.this, Voucher.class);
                startActivity(i);
                finish();
            }
        });

        tukar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addtransaksitukar(sharedPrefManager.getSP_iduser(), TukarTiket.this);

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
                        int
                        jmlA = response.body().getJmlbotolA(),
                        jmlB = response.body().getJmlbotolB(),
                        jmlC = response.body().getJmlgelas();

                        jmlbotolA.setText(Integer.toString(jmlA));
                        jmlbotolB.setText(Integer.toString(jmlB));
                        jmlgelas.setText(Integer.toString(jmlC));
                    }
                }
            }

            @Override
            public void onFailure(Call<Tabungan> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addtransaksitukar(final String user, final Context context)
    {
        int voucherA = Integer.parseInt(edtvoucherbotolA.getText().toString());
        int voucherB = Integer.parseInt(edtvoucherbotolB.getText().toString());
        int voucherC = Integer.parseInt(edtvouchergelas.getText().toString());
        int jmlVoucher = voucherA+voucherB+voucherC;

        int jmlbotolA = voucherA*3;
        int jmlbotolB = voucherB*5;
        int jmlgelas = voucherC*10;

        TukarVoucher voucherbaru = new TukarVoucher(
                user,
                currentTime,
                jmlVoucher,
                jmlbotolA,
                jmlbotolB,
                jmlgelas);

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

        final UserClient client = retrofit.create(UserClient.class);
        Call<TukarVoucher> call = client.tukarVoucher(voucherbaru);

        call.enqueue(new Callback<TukarVoucher>() {
            @Override
            public void onResponse(Call<TukarVoucher> call, Response<TukarVoucher> response) {
                updatejmlvoucher(user, response.body().getJmlVoucher());
            }

            @Override
            public void onFailure(Call<TukarVoucher> call, Throwable t) {
                Toast.makeText(context, "Maaf penukaran tiket gagal :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updatejmlvoucher(final String user, final int jmlvoucher)
    {
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

        final Retrofit retrofit = builder.build();

        final UserClient client = retrofit.create(UserClient.class);
        Call<DataVoucher> call = client.cekVoucher(user);

        call.enqueue(new Callback<DataVoucher>() {
            @Override
            public void onResponse(Call<DataVoucher> call, Response<DataVoucher> response) {
                edtvoucherbotolA.setText("0");
                edtvoucherbotolB.setText("0");
                edtvouchergelas.setText("0");

                int jmlvoucherlama = (response.body().getJmlVoucher()) + jmlvoucher;

                DataVoucher newDataVoucher = new DataVoucher(jmlvoucherlama);

                final UserClient client = retrofit.create(UserClient.class);
                Call<DataVoucher> call2 = client.updateVoucher(user, newDataVoucher);

                call2.enqueue(new Callback<DataVoucher>() {
                    @Override
                    public void onResponse(Call<DataVoucher> call, Response<DataVoucher> response) {
                        Toast.makeText(TukarTiket.this, "Tiket Berhasil ditukar!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<DataVoucher> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<DataVoucher> call, Throwable t) {
                Toast.makeText(TukarTiket.this, "Data Voucher tidak ter-Update :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
