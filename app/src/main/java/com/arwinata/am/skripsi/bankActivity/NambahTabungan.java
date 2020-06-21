package com.arwinata.am.skripsi.bankActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arwinata.am.skripsi.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NambahTabungan extends AppCompatActivity {

    TextView pengguna;
    EditText edtbotolA, edtbotolB, edtgelas;
    Button btntambah, btnback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nambah_tabungan);

        pengguna = findViewById(R.id.tvPengguna);
        edtbotolA = findViewById(R.id.edtjmlbotolA);
        edtbotolB = findViewById(R.id.edtjmlbotolB);
        edtgelas = findViewById(R.id.edtjmlgelas);
        btntambah = findViewById(R.id.btntambah);
        btnback = findViewById(R.id.btn_backTambahtabungan);
    }
}
