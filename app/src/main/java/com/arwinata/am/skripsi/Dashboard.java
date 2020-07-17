package com.arwinata.am.skripsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arwinata.am.skripsi.Retrofit.CheckingConnection;
import com.arwinata.am.skripsi.Retrofit.model.HistoriTransaksi;
import com.arwinata.am.skripsi.Retrofit.model.ListJalaniMisi;
import com.arwinata.am.skripsi.Retrofit.model.Tabungan;
import com.arwinata.am.skripsi.Retrofit.model.UserResponse;
import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;
import com.arwinata.am.skripsi.bankActivity.BankDashboard;
import com.arwinata.am.skripsi.bankActivity.QrCodeScanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity{
    private  int CAMERA_PERMISSION_CODE = 1;
    SharedPrefManager sharedPrefManager;
    TextView username, tvjmlbotolA, tvjmlbotolB, tvjmlgelas, logout, nabung;
    RecyclerView  recyclerView;
    RecyclerView.Adapter adapter;

    List<HistoriTransaksi> listItems;

    LinearLayout bus, misi;
    ImageView setting, reload;
    String namauser;
    int jmlbotolA, jmlbotolB, jmlgelas;
    CheckingConnection ck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sharedPrefManager = new SharedPrefManager(this);
        ck = new CheckingConnection();
        listItems = new ArrayList<>();

        String user = sharedPrefManager.getSP_iduser();

        username = findViewById(R.id.tvNamaUser_dash);
        tvjmlbotolA = findViewById(R.id.tvJml15l);
        tvjmlbotolB = findViewById(R.id.tvJml600ml);
        tvjmlgelas = findViewById(R.id.tvtvJml300ml);
        nabung = findViewById(R.id.btnNabung);
        logout = findViewById(R.id.buttonLogout_dash);
        bus = findViewById(R.id.ivBusDash);
        misi = findViewById(R.id.ivMisiDash);
        setting = findViewById(R.id.ivSettingDash);
        recyclerView = findViewById(R.id.lvhistori);
        reload = findViewById(R.id.ivreload);

        loadRecyclerViewData();
        cekuser(user, this);
        cektabungan(user, this);

        nabung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, QrCode.class));
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

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Reload page.....", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), Dashboard.class);
                finish();
                overridePendingTransition( 0, 0);
                startActivity(i);
                overridePendingTransition( 0, 0);
            }
        });

        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, Tiket.class);
                startActivity(i);
                finish();
            }
        });

        misi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, Mission.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void loadRecyclerViewData() {
        final String user = sharedPrefManager.getSP_iduser();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ck.getBASE_URL()+"/histori/"+user,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("transaksiUser");

                            for(int i=array.length()-1; i>=0; i--) {
                                JSONObject o = array.getJSONObject(i);

                                HistoriTransaksi item = new HistoriTransaksi(
                                        user,
                                        o.getString("namaTransaksi"),
                                        o.getString("date")
                                );
                                listItems.add(item);

                                LinearLayoutManager lim = new LinearLayoutManager(getApplicationContext());
                                lim.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(lim);

                                adapter = new HistoriAdapter(listItems, getApplicationContext());
                                recyclerView.setAdapter(adapter);

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

    public void cekuser(String idUser, final Context context) {
        Log.d("ASW", idUser);

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
        UserClient client = retrofit.create(UserClient.class);
        Call<UserResponse> call = client.cekUser(idUser);

        call.enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                namauser = response.body().getName();
                username.setText(namauser);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
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
