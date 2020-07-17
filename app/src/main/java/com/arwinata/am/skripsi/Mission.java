package com.arwinata.am.skripsi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arwinata.am.skripsi.Retrofit.CheckingConnection;
import com.arwinata.am.skripsi.Retrofit.model.HistoriTransaksi;
import com.arwinata.am.skripsi.Retrofit.model.JalaniMisi;
import com.arwinata.am.skripsi.Retrofit.model.ListJalaniMisi;
import com.arwinata.am.skripsi.Retrofit.model.PoinDanVoucher;
import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Mission extends AppCompatActivity {

    CheckingConnection ck = new CheckingConnection();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    LinearLayout leaderboard;
    TextView tvjmlpoin, tvjmlvoucher;
    Button btnback;
    SharedPrefManager sharedPrefManager;

    Date currentTime = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    String datenow = df.format(currentTime);

    private List<ListJalaniMisi> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        sharedPrefManager = new SharedPrefManager(this);

        tvjmlpoin = findViewById(R.id.tvjmlpoin);
//        tvjmlvoucher = findViewById(R.id.tvjmlvoucher);
        btnback = findViewById(R.id.btn_back);
        recyclerView = findViewById(R.id.lvmisi);
        recyclerView.setHasFixedSize(true);
        leaderboard = findViewById(R.id.leaderboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();

        getpoindanvoucher(sharedPrefManager.getSP_iduser());

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mission.this, Dashboard.class);
                startActivity(i);
                finish();
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(Mission.this, Dashboard.class);
//                startActivity(i);
//                finish();
            }
        });
    }

    public void getpoindanvoucher(String user)
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

        final UserClient client = retrofit.create(UserClient.class);
        Call<PoinDanVoucher> callpoin = client.cekPoin(user);

        callpoin.enqueue(new Callback<PoinDanVoucher>() {
            @Override
            public void onResponse(Call<PoinDanVoucher> call, retrofit2.Response<PoinDanVoucher> response) {
                tvjmlpoin.setText(Integer.toString(response.body().getJmlPoin()));
//                tvjmlvoucher.setText(Integer.toString(response.body().getJmlVoucher()));
                loadRecyclerviewData();
            }

            @Override
            public void onFailure(Call<PoinDanVoucher> call, Throwable t) {

            }
        });
    }

    private void loadRecyclerviewData() {
        final String user = sharedPrefManager.getSP_iduser();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ck.getBASE_URL()+"/jalanimisi/"+user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("misiUser");

                            for(int i=0; i<array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);

                                JSONObject o2 = o.getJSONObject("misi");

                                if(o.getString("dateselesai").equals(datenow) || o.getString("dateselesai").equals("00/00/0000")){
                                    ListJalaniMisi item = new ListJalaniMisi(
                                            user,
                                            o2.getString("_id"),
                                            o2.getString("tabeldibutuhkan"),
                                            o2.getString("detailmisi"),
                                            o2.getInt("targetcapaian"),
                                            o2.getInt("jumlahpoin"),
                                            o.getInt("targettercapai"),
                                            o.getString("status"),
                                            o.getString("claimpoin"),
                                            o.getString("dateselesai")
                                    );
                                    LinearLayoutManager lim = new LinearLayoutManager(getApplicationContext());
                                    lim.setOrientation(LinearLayoutManager.VERTICAL);
                                    listItems.add(item);

                                    adapter = new MisiAdapter(listItems, getApplicationContext());
                                    recyclerView.setLayoutManager(lim);
                                    recyclerView.setAdapter(adapter);
                                }else {
                                    updatemisi(user, o2.getString("_id"), "belum", "belum", "00/00/0000");
                                    break;
                                }
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void updatemisi(final String user, String misi, String status, String claimpoin, String dateselesai) {
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

        JalaniMisi newjalanimisi = new JalaniMisi(user, misi, status, claimpoin, dateselesai);

        //mendapatkan client & memanggil object
        UserClient client = retrofit.create(UserClient.class);
        Call<JalaniMisi> call2 = client.updateJalaniMisi(newjalanimisi);

        call2.enqueue(new Callback<JalaniMisi>() {
            @Override
            public void onResponse(Call<JalaniMisi> call, retrofit2.Response<JalaniMisi> response) {
                if(response.isSuccessful()){
                    loadRecyclerviewData();
                }
            }

            @Override
            public void onFailure(Call<JalaniMisi> call, Throwable t) {

            }
        });
    }
}


