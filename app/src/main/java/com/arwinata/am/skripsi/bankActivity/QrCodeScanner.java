package com.arwinata.am.skripsi.bankActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;
import com.google.zxing.Result;

public class QrCodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    String hasil;
    SharedPrefManager sharedPrefManager;
    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        sharedPrefManager = new SharedPrefManager(this);
        setContentView(mScannerView);
    }
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        hasil = rawResult.getText();

        sharedPrefManager.saveSPString(SharedPrefManager.sp_iduser, hasil);

        Intent i = new Intent(QrCodeScanner.this, NambahTabungan.class);
        finish();
        startActivity(i);

//        Log.v("TAG", hasil); // Prints scan results
//        Log.v("TAG", rawResult.getBarcodeFormat().toString());
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Scan Result");
//        builder.setMessage(hasil);
//        AlertDialog alert1 = builder.create();
//        alert1.show();


        mScannerView.resumeCameraPreview(this);
    }
}
