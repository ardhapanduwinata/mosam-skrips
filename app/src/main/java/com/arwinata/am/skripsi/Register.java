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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arwinata.am.skripsi.Retrofit.model.User;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;

public class Register extends AppCompatActivity {

    private String BASE_URL = "http://192.168.1.70:3000";

    EditText edtemail, edtusername, edtpassword;
    Button btnback, btnregister;
    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        level = 2;
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
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp.build());

        final Retrofit retrofit = builder.build();

        //mendapatkan client & memanggil object
        UserClient client = retrofit.create(UserClient.class);
        Call<User> call = client.createAccount(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("Success", "createUserWithEmail:success");

                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();

                Toast.makeText( Register.this,"Registrasi Berhasil! Silahkan Login Terlebih Dahulu!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Register.this, "something went wrong :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
