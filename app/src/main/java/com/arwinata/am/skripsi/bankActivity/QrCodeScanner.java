package com.arwinata.am.skripsi.bankActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import android.os.Bundle;
import android.util.Log;

import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;
import com.google.zxing.Result;

public class QrCodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    SharedPrefManager sharedPrefManager;
    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
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
//        sharedPrefManager.saveSPString(SharedPrefManager.sp_iduser, rawResult.getText());
//        sharedPrefManager.saveSPString(SharedPrefManager.sp_iduser, rawResult.getBarcodeFormat().toString());

        Log.v("TAG", rawResult.getText()); // Prints scan results
        Log.v("TAG", rawResult.getBarcodeFormat().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();

        mScannerView.resumeCameraPreview(this);
    }
}
