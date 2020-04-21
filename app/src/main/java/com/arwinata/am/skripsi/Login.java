package com.arwinata.am.skripsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.arwinata.am.skripsi.Retrofit.IMyService;
import com.arwinata.am.skripsi.Retrofit.LoginResult;
import com.arwinata.am.skripsi.Retrofit.RetrofitClient;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    private String BASE_URL = "http://192.168.1.70:3000";

    private Retrofit retrofit;
    private IMyService retrofitInterface;
    TextView txt_create_account;
    MaterialEditText edt_login_email, edt_login_password;
    Button btn_login;

    CompositeDisposable compositeDisposible = new CompositeDisposable();
    IMyService iMyService;

    public Login() {
    }

    @Override
    protected void onStop(){
        compositeDisposible.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init service
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(IMyService.class);
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        //init view
        edt_login_email = (MaterialEditText) findViewById(R.id.edt_email);
        edt_login_password = (MaterialEditText) findViewById(R.id.edt_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();

                map.put("email", edt_login_email.getText().toString());
                map.put("password", edt_login_password.getText().toString());

                Call<LoginResult> call = retrofitInterface.executeLogin(map);

                call.enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

                        if(response.code() == 200) {

                            LoginResult result = response.body();

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(Login.this);
                            builder1.setTitle(result.getName());
                            builder1.setMessage(result.getEmail());

                            builder1.show();
                        } else if (response.code() == 404) {
                            Toast.makeText(Login.this, "Wrong Email/Password", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
//
//                loginUser(edt_login_email.getText().toString(),
//                        edt_login_password.getText().toString());

            }
        });

        txt_create_account = (TextView) findViewById(R.id.txt_create_account);
        txt_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View register_layout = LayoutInflater.from(Login.this)
                        .inflate(R.layout.activity_register, null);

                new MaterialStyledDialog.Builder(Login.this)
                        .setIcon(R.drawable.ic_account)
                        .setTitle("REGISTRATION")
                        .setDescription("Please fill all fields")
                        .setCustomView(register_layout)
                        .setNegativeText("CANCEL")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("REGISTER")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                MaterialEditText edt_register_email = (MaterialEditText) register_layout.findViewById(R.id.edt_email);
                                MaterialEditText edt_register_name = (MaterialEditText) register_layout.findViewById(R.id.edt_name);
                                MaterialEditText edt_register_password = (MaterialEditText) register_layout.findViewById(R.id.edt_password);

                                if(TextUtils.isEmpty(edt_register_email.getText().toString()))
                                {
                                    Toast.makeText(Login.this, "Email cannot be null or empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if(TextUtils.isEmpty(edt_register_name.getText().toString()))
                                {
                                    Toast.makeText(Login.this, "Name cannot be null or empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if(TextUtils.isEmpty(edt_register_password.getText().toString()))
                                {
                                    Toast.makeText(Login.this, "Password cannot be null or empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }


                                HashMap<String, String> map = new HashMap<>();

                                map.put("name", edt_register_name.getText().toString());
                                map.put("email", edt_register_email.getText().toString());
                                map.put("password", edt_register_password.getText().toString());

                                Call<Void> call = retrofitInterface.executeRegister(map);

                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                        if(response.code() == 200) {
                                            Toast.makeText(Login.this, "Login success", Toast.LENGTH_SHORT).show();
                                        } else if (response.code() == 400) {
                                            Toast.makeText(Login.this, "Already login", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
//
//                                registerUser(edt_register_email.getText().toString(),
//                                        edt_register_name.getText().toString(),
//                                        edt_register_password.getText().toString());
                            }
                        }).show();
            }
        });
    }

    private void registerUser(String email, String name, String password)
    {
        compositeDisposible.add(iMyService.registerUser(email, name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        Toast.makeText(Login.this, ""+response, Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }

    private void loginUser(String email, String password){
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Email cannot be null or empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Password cannot be null or empty", Toast.LENGTH_SHORT).show();
            return;
        }

        compositeDisposible.add(iMyService.loginUser(email, password)
        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        Toast.makeText(Login.this, ""+response, Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }
}
