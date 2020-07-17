package com.arwinata.am.skripsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arwinata.am.skripsi.Retrofit.CheckingConnection;
import com.arwinata.am.skripsi.Retrofit.model.DataTiket;
import com.arwinata.am.skripsi.Retrofit.model.DataTukarTiket;
import com.arwinata.am.skripsi.Retrofit.model.HistoriTransaksi;
import com.arwinata.am.skripsi.Retrofit.model.JalaniMisi;
import com.arwinata.am.skripsi.Retrofit.model.Tabungan;
import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;
import com.arwinata.am.skripsi.bankActivity.BankDashboard;
import com.arwinata.am.skripsi.bankActivity.QrCodeScanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TukarTiket extends AppCompatActivity {
    int CAMERA_PERMISSION_CODE = 1;

    Button tambahbotolA, kurangbotolA, tambahbotolB, kurangbotolB,
            tambahgelas, kuranggelas, maxbotolA, maxbotolB, maxgelas,
            back, tukar;

    TextView jmlbotolA, jmlbotolB, jmlgelas;
    EditText edtvoucherbotolA, edtvoucherbotolB, edtvouchergelas;
    SharedPrefManager sharedPrefManager;
    CheckingConnection ck;

    private class DataMisi{
        String misi, statusmisi;

        public DataMisi(){}

        public void setMisi(String misi) {
            this.misi = misi;
        }

        public void setStatusmisi(String statusmisi) {
            this.statusmisi = statusmisi;
        }

        public String getMisi() {
            return misi;
        }

        public String getStatusmisi() {
            return statusmisi;
        }
    }
    DataMisi dataMisi = new DataMisi();

    Date currentTime = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    String datenow = df.format(currentTime);
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

        cekmisi(sharedPrefManager.getSP_iduser());
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
                Intent i = new Intent(TukarTiket.this, Tiket.class);
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

    public void cekmisi(final String user)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ck.getBASE_URL()+"/jalanimisi/"+user,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("misiUser");

                            for(int i=0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);

                                JSONObject o2 = o.getJSONObject("misi");

                                String misi = o2.getString("_id");
                                String status = o.getString("status");
                                String detailmisi = o2.getString("detailmisi");
                                int target = o2.getInt("targetcapaian");

                                if(detailmisi.toLowerCase().contains("tukarkan tabungan dengan 2 sticker bis")){
                                    dataMisi.setMisi(misi);
                                    dataMisi.setStatusmisi(status);
                                }
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void cektransaksiTukarTiket(final String user,final String misi, String status) {
//        Toast.makeText(getApplicationContext(), "ini di cek misi "+misi, Toast.LENGTH_LONG).show();
        if(status.equals("belum")) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    ck.getBASE_URL() + "/tukartiket/" + user,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray array = jsonObject.getJSONArray("TransaksiTukarTiket");
//
                                String terakhirtransaksi = "";
                                if (array.length() > 0) {
                                    JSONObject o = array.getJSONObject(array.length() - 1);
                                    terakhirtransaksi = o.getString("date");
                                Toast.makeText(getApplicationContext(), terakhirtransaksi,Toast.LENGTH_LONG).show();
                                } else if (array.length() == 0) {
                                    terakhirtransaksi = "kosong wkwkwk";
                                }
//                            Toast.makeText(getApplicationContext(), terakhirtransaksi,Toast.LENGTH_LONG).show();
                                updatemisiTukarTiket(user, misi, terakhirtransaksi);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else if(status.equals("sudah")){}
    }

    private void updatemisiTukarTiket(String user, String misi, String terakhirtransaksi) {
        if(terakhirtransaksi.equals(datenow))
        {
//            Toast.makeText(getApplicationContext(), "MISI TIDAK TERUPDATE! "+terakhirtransaksi,Toast.LENGTH_LONG).show();
        } else {
            //membuat okhttp client
//            OkHttpClient.Builder okhttp = new OkHttpClient.Builder();
//
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//            okhttp.addInterceptor(logging);

            //membuat instance retrofit
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(ck.getBASE_URL())
                    .addConverterFactory(GsonConverterFactory.create());
//                    .client(okhttp.build());

            Retrofit retrofit = builder.build();

            JalaniMisi newjalanimisi = new JalaniMisi(user, misi, "sudah", "belum", datenow);

            //mendapatkan client & memanggil object
            UserClient client = retrofit.create(UserClient.class);
            Call<JalaniMisi> call2 = client.updateJalaniMisi(newjalanimisi);

            call2.enqueue(new Callback<JalaniMisi>() {
                @Override
                public void onResponse(Call<JalaniMisi> call, Response<JalaniMisi> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Selamat Anda Menyelesaikan Misi! Ke halaman misi untuk claim!",Toast.LENGTH_LONG).show();
                    }
              }

                @Override
                public void onFailure(Call<JalaniMisi> call, Throwable t) {

                }
            });
        }
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

        DataTukarTiket voucherbaru = new DataTukarTiket(
                user,
                datenow,
                jmlVoucher,
                jmlbotolA,
                jmlbotolB,
                jmlgelas);

        if(jmlVoucher >= 2){
            cektransaksiTukarTiket(sharedPrefManager.getSP_iduser(), dataMisi.getMisi(), dataMisi.getStatusmisi());
        }

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
        Call<DataTukarTiket> call = client.tukarTiket(voucherbaru);

        call.enqueue(new Callback<DataTukarTiket>() {
            @Override
            public void onResponse(Call<DataTukarTiket> call, Response<DataTukarTiket> response) {
                updatejmlvoucher(user, response.body().getJmlTiket());
                addHistory(user, "Tukar Tiket sebanyak "+response.body().getJmlTiket(), datenow);
            }

            @Override
            public void onFailure(Call<DataTukarTiket> call, Throwable t) {
                Toast.makeText(context, "Maaf penukaran tiket gagal :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updatejmlvoucher(final String user, final int jmlvoucher)
    {
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

        final Retrofit retrofit = builder.build();

        final UserClient client = retrofit.create(UserClient.class);
        Call<DataTiket> call = client.cekTiket(user);

        call.enqueue(new Callback<DataTiket>() {
            @Override
            public void onResponse(Call<DataTiket> call, Response<DataTiket> response) {
                edtvoucherbotolA.setText("0");
                edtvoucherbotolB.setText("0");
                edtvouchergelas.setText("0");

                int jmlvoucherlama = (response.body().getJmlTiket()) + jmlvoucher;

                DataTiket newDataTiket = new DataTiket(jmlvoucherlama);

                final UserClient client = retrofit.create(UserClient.class);
                Call<DataTiket> call2 = client.updateTiket(user, newDataTiket);

                call2.enqueue(new Callback<DataTiket>() {
                    @Override
                    public void onResponse(Call<DataTiket> call, Response<DataTiket> response) {
//                        Toast.makeText(TukarTiket.this, "Tiket Berhasil ditukar!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<DataTiket> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<DataTiket> call, Throwable t) {
                Toast.makeText(TukarTiket.this, "Data Tiket tidak ter-Update :(", Toast.LENGTH_SHORT).show();
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
