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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arwinata.am.skripsi.Retrofit.CheckingConnection;
import com.arwinata.am.skripsi.Retrofit.model.JalaniMisi;
import com.arwinata.am.skripsi.Retrofit.model.PoinDanVoucher;
import com.arwinata.am.skripsi.Retrofit.model.Tabungan;
import com.arwinata.am.skripsi.Retrofit.model.User;
import com.arwinata.am.skripsi.Retrofit.model.DataTiket;
import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    CheckingConnection ck;
    SharedPrefManager sharedPrefManager;

    EditText edtemail, edtusername, edtpassword;
    Button btnback, btnregister;
    String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedPrefManager = new SharedPrefManager(this);
        ck = new CheckingConnection();

        level = "dua";
        edtemail = findViewById(R.id.edEmail_regis);
        edtusername = findViewById(R.id.edUsername_regis);
        edtpassword = findViewById(R.id.edPassword_regis);
        btnregister = findViewById(R.id.btnRegister_regis);
        btnback = findViewById(R.id.btn_backRegis);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(
                edtemail.getText().toString(),
                edtusername.getText().toString(),
                edtpassword.getText().toString(), level);

                sendNetworkRequest(user);
            }
        });
    }

    private void sendNetworkRequest(User user) {

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
        UserClient client = retrofit.create(UserClient.class);
        Call<User> call = client.createAccount(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body().getEmail() == null){
                    Toast.makeText( Register.this,response.body().getMessage(), Toast.LENGTH_LONG).show();

                    edtemail.setText("");
                    edtpassword.setText("");
                } else {
                    Toast.makeText( Register.this,response.body().getMessage(), Toast.LENGTH_LONG).show();

                    String user = response.body().getId();
                    buattabungan(user);
                    buattiket(user);
                    buatpoin(user);
                    buatjalanimisi(user);
                    Intent i = new Intent(Register.this, Login.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Register.this, "Terjadi kesalahan :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buattabungan(String iduser){
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

        Tabungan tabunganbaru = new Tabungan(iduser, 0, 0, 0);

        //mendapatkan client & memanggil object
        final UserClient client = retrofit.create(UserClient.class);
        Call<Tabungan> newtabungan = client.buatTabungan(tabunganbaru);
        newtabungan.enqueue(new Callback<Tabungan>() {
            @Override
            public void onResponse(Call<Tabungan> call, Response<Tabungan> response) {
            }

            @Override
            public void onFailure(Call<Tabungan> call, Throwable t) {
                Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buattiket(String idUser){
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

        DataTiket voucherbaru = new DataTiket(idUser, 0);

        final UserClient client = retrofit.create(UserClient.class);
        Call<DataTiket> newVoucher = client.buatTiket(voucherbaru);

        newVoucher.enqueue(new Callback<DataTiket>() {
            @Override
            public void onResponse(Call<DataTiket> call, Response<DataTiket> response) {

            }

            @Override
            public void onFailure(Call<DataTiket> call, Throwable t) {
                Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buatpoin(String idUser){
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

        PoinDanVoucher voucherbaru = new PoinDanVoucher(idUser, 0);

        final UserClient client = retrofit.create(UserClient.class);
        Call<PoinDanVoucher> call = client.buatPoin(voucherbaru);

        call.enqueue(new Callback<PoinDanVoucher>() {
            @Override
            public void onResponse(Call<PoinDanVoucher> call, Response<PoinDanVoucher> response) {

            }

            @Override
            public void onFailure(Call<PoinDanVoucher> call, Throwable t) {
                Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buatjalanimisi(final String user) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ck.getBASE_URL()+"/misi",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("misi");

//                            OkHttpClient.Builder okhttp = new OkHttpClient.Builder();
//
//                            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//                            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//                            okhttp.addInterceptor(logging);

                            //membuat instance retrofit
                            Retrofit.Builder builder = new Retrofit.Builder()
                                    .baseUrl(ck.getBASE_URL())
                                    .addConverterFactory(GsonConverterFactory.create());
//                                    .client(okhttp.build());

                            Retrofit retrofit = builder.build();
                            UserClient client = retrofit.create(UserClient.class);

                            for(int i=0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);

                                String idmisi = o.getString("_id");

                                JalaniMisi newjalanimisi = new JalaniMisi(user, idmisi);
                                Call<JalaniMisi> call = client.buatJalaniMisi(newjalanimisi);

                                call.enqueue(new Callback<JalaniMisi>() {
                                    @Override
                                    public void onResponse(Call<JalaniMisi> call, Response<JalaniMisi> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<JalaniMisi> call, Throwable t) {

                                    }
                                });
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
}
