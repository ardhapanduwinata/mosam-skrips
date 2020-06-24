package com.arwinata.am.skripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.arwinata.am.skripsi.shared_preference.SharedPrefManager;
import com.arwinata.am.skripsi.bank_activity.BankDashboard;
import com.arwinata.am.skripsi.user_activity.Dashboard;
import com.arwinata.am.skripsi.user_activity.Login;
import com.arwinata.am.skripsi.user_activity.Register;

public class MainActivity extends AppCompatActivity {

    Button login, register;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefManager = new SharedPrefManager(this);

        login = findViewById(R.id.buttonLogin_main);
        register = findViewById(R.id.buttonRegister_main);

        if (sharedPrefManager.getSPSudahLogin() != false) {
            if (sharedPrefManager.getSP_level().equals("satu")) {
                startActivity(new Intent(MainActivity.this, BankDashboard.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            } else if (sharedPrefManager.getSP_level().equals("dua")) {
                startActivity(new Intent(MainActivity.this, Dashboard.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        } else {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, Login.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, Register.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            });
        }
    }
}
