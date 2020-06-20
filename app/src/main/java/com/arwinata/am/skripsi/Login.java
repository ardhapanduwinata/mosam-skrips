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

import com.arwinata.am.skripsi.model.UserResponse;
import com.arwinata.am.skripsi.model.LoginRequest;
import com.arwinata.am.skripsi.shared_preference.SharedPrefManager;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;
import com.arwinata.am.skripsi.bankActivity.BankDashboard;

public class Login extends AppCompatActivity {

    private String BASE_URL = "http://192.168.1.70:3000";

    //SharedPreference (biar login terus)
    SharedPrefManager sharedPrefManager;

    EditText edt_login_email, edt_login_password;
    Button btn_login, btnback;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPrefManager = new SharedPrefManager(this);

            //init view
            edt_login_email = (EditText) findViewById(R.id.edemail_login);
            edt_login_password = (EditText) findViewById(R.id.edPassword_login);
            btnback = (Button) findViewById(R.id.btn_backLogin);
            btn_login = (Button) findViewById(R.id.btnLogin_login);

            btnback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            });

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    email = edt_login_email.getText().toString();
                    password = edt_login_password.getText().toString();
                    LoginRequest logreq = new LoginRequest(email, password);

                    loginConnection(logreq);
                }
            });
    }

    private void loginConnection(LoginRequest loginRequest) {

        //membuat okhttp client
        OkHttpClient.Builder okhttp = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttp.addInterceptor(logging);

        //membuat instance retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp.build());

        Retrofit retrofit = builder.build();

        //mendapatkan client & memanggil object
        UserClient client = retrofit.create(UserClient.class);
        Call<UserResponse> call = client.loginAcount(loginRequest);

        call.enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful())
                {
                    String
                            idUser = response.body().get_id(),
                            username = response.body().getName();
                    String level = response.body().getLevel();

                    sharedPrefManager.saveSPString(SharedPrefManager.sp_iduser, idUser);
                    sharedPrefManager.saveSPString(SharedPrefManager.sp_namauser, username);
                    sharedPrefManager.saveSPBoolean(SharedPrefManager.sp_sudahLogin, true);
                    sharedPrefManager.saveSPString(SharedPrefManager.sp_level, level);
                    Class nextClass = Dashboard.class;

                    Toast.makeText( Login.this,"Halo " + username, Toast.LENGTH_LONG).show();

                    if(level.equals("satu")){
                        nextClass = BankDashboard.class;
                    } else if (level.equals("dua")){
                        nextClass = Dashboard.class;
                    }

                    Intent i = new Intent(Login.this, nextClass);
                    i.putExtra("idUser", idUser);
                    i.putExtra("username", username);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } else {
                    Toast.makeText( Login.this,"Email/password anda salah :(", Toast.LENGTH_LONG).show();

                    edt_login_email.setText("");
                    edt_login_password.setText("");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(Login.this, "Koneksi anda gagal :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
