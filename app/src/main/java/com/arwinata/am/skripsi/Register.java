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

import com.arwinata.am.skripsi.model.TabunganResponse;
import com.arwinata.am.skripsi.model.User;
import com.arwinata.am.skripsi.shared_preference.SharedPrefManager;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;

public class Register extends AppCompatActivity {

    private String BASE_URL = "http://192.168.1.70:3000";
    SharedPrefManager sharedPrefManager;

    EditText edtemail, edtusername, edtpassword;
    Button btnback, btnregister;
    String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedPrefManager = new SharedPrefManager(this);

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
                if(response.body().getEmail() == null){
                    Toast.makeText( Register.this,response.body().getMessage(), Toast.LENGTH_LONG).show();

                    edtemail.setText("");
                    edtpassword.setText("");
                } else {
                    Toast.makeText( Register.this,response.body().getMessage(), Toast.LENGTH_LONG).show();

                    String user = response.body().get_id();
                    buattabungan(user);
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
        String BASE_URL = "http://192.168.1.70:3000";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp.build());

        Retrofit retrofit = builder.build();

        TabunganResponse tabunganbaru = new TabunganResponse(iduser, 0, 0, 0);

        //mendapatkan client & memanggil object
        final UserClient client = retrofit.create(UserClient.class);
        Call<TabunganResponse> newtabungan = client.buatTabungan(tabunganbaru);
        newtabungan.enqueue(new Callback<TabunganResponse>() {
            @Override
            public void onResponse(Call<TabunganResponse> call, Response<TabunganResponse> response) {

            }

            @Override
            public void onFailure(Call<TabunganResponse> call, Throwable t) {
                Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
