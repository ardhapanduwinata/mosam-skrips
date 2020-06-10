
package com.arwinata.am.skripsi.bankActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.arwinata.am.skripsi.Login;
import com.arwinata.am.skripsi.MainActivity;
import com.arwinata.am.skripsi.R;
import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;

import org.w3c.dom.Text;

public class BankDashboard extends AppCompatActivity {

    TextView logout;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_dashboard);

        logout = findViewById(R.id.buttonLogout_dashbank);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.sp_sudahLogin, false);
                startActivity(new Intent(BankDashboard.this, Login.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
    }
}
