package com.arwinata.am.skripsi;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.PorterDuff.Mode;
import android.widget.TextView;

import com.arwinata.am.skripsi.Retrofit.CheckingConnection;
import com.arwinata.am.skripsi.Retrofit.model.DataVoucher;
import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;

import java.util.ArrayList;

public class Voucher extends AppCompatActivity {

    ImageView[][] sticker = new ImageView[3][6];

    final int[][] ivViewId = new int[][]{
            {R.id.ivsticker01, R.id.ivsticker02, R.id.ivsticker03, R.id.ivsticker04, R.id.ivsticker05, R.id.ivsticker06},
            {R.id.ivsticker11, R.id.ivsticker12, R.id.ivsticker13, R.id.ivsticker14, R.id.ivsticker15, R.id.ivsticker16},
            {R.id.ivsticker21, R.id.ivsticker22, R.id.ivsticker23, R.id.ivsticker24, R.id.ivsticker25, R.id.ivsticker26}
    };

    SharedPrefManager sharedPrefManager;
    CheckingConnection ck;
    Button tukarvoucher, back;
    TextView tikettersimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);

        sharedPrefManager = new SharedPrefManager(this);
        ck = new CheckingConnection();

        cekVoucher(sharedPrefManager.getSP_iduser());

        tukarvoucher = findViewById(R.id.btntukarsticker);
        back = findViewById(R.id.btn_back);


        tukarvoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Voucher.this, TukarTiket.class);
                startActivity(i);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Voucher.this, Dashboard.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void cekVoucher(String user) {
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
        Call<DataVoucher> call = client.cekVoucher(user);

        call.enqueue(new Callback<DataVoucher>() {
            @Override
            public void onResponse(Call<DataVoucher> call, Response<DataVoucher> response) {
                if(response.isSuccessful())
                {
                    tikettersimpan = findViewById(R.id.tiketersimpan);
                    int jumlahvoucher = response.body().getJmlVoucher();
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
            }

            @Override
            public void onFailure(Call<DataVoucher> call, Throwable t) {

            }
        });
    }
}
