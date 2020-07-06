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

import com.arwinata.am.skripsi.Retrofit.CheckingConnection;
import com.arwinata.am.skripsi.Retrofit.model.Poin;
import com.arwinata.am.skripsi.Retrofit.model.Tabungan;
import com.arwinata.am.skripsi.Retrofit.model.User;
import com.arwinata.am.skripsi.Retrofit.model.DataVoucher;
import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;

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
        OkHttpClient.Builder okhttp = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttp.addInterceptor(logging);

        //membuat instance retrofit
        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ck.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp.build());

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
                    buatvoucher(user);
                    buatpoin(user);
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

    public void buatvoucher(String idUser){
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

        DataVoucher voucherbaru = new DataVoucher(idUser, 0);

        final UserClient client = retrofit.create(UserClient.class);
        Call<DataVoucher> newVoucher = client.buatVoucher(voucherbaru);

        newVoucher.enqueue(new Callback<DataVoucher>() {
            @Override
            public void onResponse(Call<DataVoucher> call, Response<DataVoucher> response) {

            }

            @Override
            public void onFailure(Call<DataVoucher> call, Throwable t) {
                Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buatpoin(String idUser){
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

        Poin voucherbaru = new Poin(idUser);

        final UserClient client = retrofit.create(UserClient.class);
        Call<Poin> call = client.buatPoin(voucherbaru);

        call.enqueue(new Callback<Poin>() {
            @Override
            public void onResponse(Call<Poin> call, Response<Poin> response) {

            }

            @Override
            public void onFailure(Call<Poin> call, Throwable t) {
                Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
