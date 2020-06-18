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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arwinata.am.skripsi.Retrofit.model.EditProfileReq;
import com.arwinata.am.skripsi.Retrofit.model.User;
import com.arwinata.am.skripsi.Retrofit.model.UserResponse;
import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;

public class UserSetting extends AppCompatActivity {

    EditText edtusername, edtemail, edtpass, edtreinputpass;
    String username, email, id, usernamelama, emaillama, clicked = "no", newpass;
    Button update, back, pass;
    TextView tvpass, tvreinputpass;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        sharedPrefManager = new SharedPrefManager(this);

        cekuser(sharedPrefManager.getSP_iduser(), this);

        edtusername = findViewById(R.id.edusername_setting);
        edtemail = findViewById(R.id.edemail_setting);
        update = findViewById(R.id.btn_updateSetting);
        back = findViewById(R.id.btn_backSetting);
        pass = findViewById(R.id.btnpass);
        edtpass = findViewById(R.id.edtpass);
        edtreinputpass = findViewById(R.id.edtnewpasscheck);
        tvpass = findViewById(R.id.tvpasswordSetting);
        tvreinputpass = findViewById(R.id.tvreinputpass);

        edtpass.setEnabled(false);
        edtreinputpass.setVisibility(View.INVISIBLE);
        tvreinputpass.setVisibility(View.INVISIBLE);

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtpass.setEnabled(true);
                tvpass.setText("Input New Password");
                edtreinputpass.setVisibility(View.VISIBLE);
                tvreinputpass.setVisibility(View.VISIBLE);
                clicked = "yes";
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserSetting.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clicked.equals("yes")){
                    if(edtpass.getText().toString().equals(edtreinputpass.getText().toString())){
                        edtpass.setEnabled(false);
                        edtreinputpass.setVisibility(View.INVISIBLE);
                        tvreinputpass.setVisibility(View.INVISIBLE);
                        tvpass.setText("Password");
                        username = edtusername.getText().toString();
                        email = edtemail.getText().toString();
                        id = sharedPrefManager.getSP_iduser();

                        EditProfileReq edtprofile = new EditProfileReq(username, email,newpass);
                        updateConnection(id ,edtprofile);
                    } else {
                        Toast.makeText( UserSetting.this,"Pastikan password yang anda ulang sama!", Toast.LENGTH_LONG).show();
                        edtreinputpass.setText("");
                    }
                }else if(clicked.equals("no")){
                    username = edtusername.getText().toString();
                    email = edtemail.getText().toString();
                    id = sharedPrefManager.getSP_iduser();
                    EditProfileReq edtprofile = new EditProfileReq(username, email);

                    updateConnection(id ,edtprofile);
                }
            }
        });
    }

    public void updateConnection(String id,EditProfileReq edtprofile)
    {
        //membuat okhttp client
        String BASE_URL = "http://192.168.1.70:3000";
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
        Call<UserResponse> call = client.updateProfile(id, edtprofile);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.body().getEmail() == null)
                {
                    Toast.makeText( UserSetting.this,"Email Telah digunakan pengguna lain!", Toast.LENGTH_LONG).show();

                    edtusername.setText(usernamelama);
                    edtemail.setText(emaillama);
                } else {
                    Toast.makeText( UserSetting.this,"Data berhasil diubah", Toast.LENGTH_LONG).show();

                    String newname, newemail;

                    newname = response.body().getName();
                    newemail = response.body().getEmail();

                    edtusername.setText(newname);
                    edtemail.setText(newemail);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText( UserSetting.this,"Terjadi kesalahan, profile tidak terupdate :(", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void cekuser(String idUser, final Context context)
    {
        OkHttpClient.Builder okhttp = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttp.addInterceptor(logging);

        String BASE_URL = "http://192.168.1.70:3000";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp.build());

        Retrofit retrofit = builder.build();
        UserClient client = retrofit.create(UserClient.class);
        Call<UserResponse> call = client.cekUser(idUser);

        call.enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful())
                {
                    edtusername.setText(response.body().getName());
                    edtemail.setText(response.body().getEmail());
                    edtpass.setText("password");

                    usernamelama = response.body().getName();
                    emaillama = response.body().getEmail();
                } else {
                    Toast.makeText( context,"Terjadi kesalahan :(", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
