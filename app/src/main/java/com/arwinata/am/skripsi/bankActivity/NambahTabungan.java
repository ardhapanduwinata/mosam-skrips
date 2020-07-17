package com.arwinata.am.skripsi.bankActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arwinata.am.skripsi.MisiAdapter;
import com.arwinata.am.skripsi.R;
import com.arwinata.am.skripsi.Retrofit.CheckingConnection;
import com.arwinata.am.skripsi.Retrofit.model.DataIdMisi;
import com.arwinata.am.skripsi.Retrofit.model.HistoriTransaksi;
import com.arwinata.am.skripsi.Retrofit.model.JalaniMisi;
import com.arwinata.am.skripsi.Retrofit.model.ListJalaniMisi;
import com.arwinata.am.skripsi.Retrofit.model.Nabung;
import com.arwinata.am.skripsi.Retrofit.model.UserResponse;
import com.arwinata.am.skripsi.Retrofit.service.SharedPrefManager;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NambahTabungan extends AppCompatActivity {

    TextView pengguna, btnback;
    String user, bank;
    EditText edtbotolA, edtbotolB, edtgelas;
    Button btntambah;
    SharedPrefManager sharedPrefManager;
    CheckingConnection ck;

    Date currentTime = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    String datenow = df.format(currentTime);

    DataIdMisi idmisi = new DataIdMisi();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nambah_tabungan);
        sharedPrefManager = new SharedPrefManager(this);
        ck = new CheckingConnection();

        pengguna = findViewById(R.id.tvPengguna);
        edtbotolA = findViewById(R.id.edtjmlbotolA);
        edtbotolB = findViewById(R.id.edtjmlbotolB);
        edtgelas = findViewById(R.id.edtjmlgelas);
        btntambah = findViewById(R.id.btntambah);
        btnback = findViewById(R.id.btn_backTambahtabungan);

        user = sharedPrefManager.getSP_iduser();
        bank = sharedPrefManager.getSP_idbank();

        cekuser(user, this);
        cekmisi(user);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager.saveSPString(SharedPrefManager.sp_iduser, "");
                sharedPrefManager.saveSPString(SharedPrefManager.sp_namauser, "");

                Intent i = new Intent(NambahTabungan.this, BankDashboard.class);
                finish();
                startActivity(i);
                finish();
            }
        });

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cektransaksiNabung(user, idmisi.getMisimenabung(), idmisi.getStatusmenabung());
                transaksiNabung(user, bank);
            }
        });
    }

    public void cekmisi(final String user)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ck.getBASE_URL()+"/jalanimisi/"+user,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("misiUser");

                            for(int i=0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);

                                JSONObject o2 = o.getJSONObject("misi");

                                String misi = o2.getString("_id");
                                String detailmisi = o2.getString("detailmisi");
                                String status = o.getString("status");

                                if(detailmisi.toLowerCase().contains("lakukan transaksi")){
                                    idmisi.setMisimenabung(misi);
                                    idmisi.setStatusmenabung(status);
                                }
                                if(detailmisi.toLowerCase().contains("mengumpulkan 15")){
                                    idmisi.setMisimengumpulkanbotolA(misi);
                                    idmisi.setStatusmengumpulkanbotolA(status);
                                }
                                if(detailmisi.toLowerCase().contains("mengumpulkan 25")){
                                    idmisi.setMisimengumpulkanbotolB(misi);
                                    idmisi.setStatusmengumpulkanbotolB(status);
                                }
                                if(detailmisi.toLowerCase().contains("mengumpulkan 50")){
                                    idmisi.setMisimengumpulkangelas(misi);
                                    idmisi.setStatusmengumpulkangelas(status);
                                }
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void cektransaksiNabung(final String user, final String misimenabung, String status)
    {
        if(status.equals("belum"))
        {
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    ck.getBASE_URL()+"/nabung/"+user,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray array = jsonObject.getJSONArray("TransaksiNabung");
//
                                String terakhirtransaksi = "";
                                if(array.length() > 0)
                                {
                                    JSONObject o = array.getJSONObject(array.length()-1);
                                    terakhirtransaksi = o.getString("date");
//                                Toast.makeText(getApplicationContext(), terakhirtransaksi,Toast.LENGTH_LONG).show();
                                } else if(array.length() == 0)
                                {
                                    terakhirtransaksi = "00/00/0000";
                                }
//                            Toast.makeText(getApplicationContext(), terakhirtransaksi,Toast.LENGTH_LONG).show();
                                updatemisimenabung(user, misimenabung, terakhirtransaksi, "sudah");
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else{}
    }

    public void cekuser(String idUser, final Context context) {
        //membuat okhttp client
//        OkHttpClient.Builder okhttp = new OkHttpClient.Builder();
//
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        okhttp.addInterceptor(logging);

        //membuat instance retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ck.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create());
//                .client(okhttp.build());

        Retrofit retrofit = builder.build();

        //mendapatkan client & memanggil object
        UserClient client = retrofit.create(UserClient.class);
        Call<UserResponse> call = client.cekUser(idUser);

        call.enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                String namauser = response.body().getName();
                sharedPrefManager.saveSPString(SharedPrefManager.sp_namauser, namauser);
                pengguna.setText("Nama Nasabah: "+namauser);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void transaksiNabung(final String user, final String bank){
        //membuat okhttp client
//        OkHttpClient.Builder okhttp = new OkHttpClient.Builder();
//
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        okhttp.addInterceptor(logging);

        //membuat instance retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ck.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create());
//                .client(okhttp.build());

        Retrofit retrofit = builder.build();

        String edtjmlbotolA = edtbotolA.getText().toString();
        String edtjmlbotolB = edtbotolB.getText().toString();
        String edtjmlgelas = edtgelas.getText().toString();

        if(edtbotolA.getText().toString().equals("")){
            edtjmlbotolA = "0";
        }if (edtbotolB.getText().toString().equals("")){
            edtjmlbotolB = "0";
        }if (edtgelas.getText().toString().equals("")){
            edtjmlgelas = "0";
        }

        int jmlbotolA = Integer.parseInt(edtjmlbotolA);
        int jmlbotolB = Integer.parseInt(edtjmlbotolB);
        int jmlgelas = Integer.parseInt(edtjmlgelas);

        if(jmlbotolA>= 15 && jmlbotolA!=0){
            Log.e("wkwk", idmisi.getMisimengumpulkanbotolA());
            updatemisiditabung(user, idmisi.getMisimengumpulkanbotolA(), "sudah");
        }

        if(jmlbotolB>= 25 && jmlbotolB!=0){
            Log.e("wkwk", idmisi.getMisimengumpulkanbotolB());
            updatemisiditabung(user, idmisi.getMisimengumpulkanbotolB(), "sudah");
        }

        if(jmlgelas>= 50 && jmlgelas!=0){
            Log.e("wkwk", idmisi.getMisimengumpulkangelas());
            updatemisiditabung(user, idmisi.getMisimengumpulkangelas(), "sudah");
        }

        Nabung nabung1 = new Nabung(user, bank, jmlbotolA, jmlbotolB, jmlgelas, datenow);
        //mendapatkan client & memanggil object
        UserClient client = retrofit.create(UserClient.class);
        Call<Nabung> call1 = client.transaksiNabung(nabung1);

        addHistory(user, "Transaksi Menabung di bank sampah", datenow);
        addHistory(bank, "Terima botol/gelas dari Nasabah: "+sharedPrefManager.getSP_namauser(), datenow);
        call1.enqueue(new Callback<Nabung>() {
            @Override
            public void onResponse(Call<Nabung> call, Response<Nabung> response) {
                if(response.isSuccessful()){
                    edtbotolA.setText("");
                    edtbotolB.setText("");
                    edtgelas.setText("");
                }
            }

            @Override
            public void onFailure(Call<Nabung> call, Throwable t) {
                Toast.makeText(NambahTabungan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Nabung nabung2 = new Nabung(bank, bank, jmlbotolA, jmlbotolB, jmlgelas, datenow);
        Call<Nabung> call2 = client.transaksiNabung(nabung2);
        call2.enqueue(new Callback<Nabung>() {
            @Override
            public void onResponse(Call<Nabung> call, Response<Nabung> response) {
                Toast.makeText(NambahTabungan.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Nabung> call, Throwable t) {

            }
        });
    }

    private void updatemisimenabung(String user, String misimenabung, String terakhirtransaksi, String status) {
        if(terakhirtransaksi.equals(datenow))
        {
//            Toast.makeText(getApplicationContext(), "MISI TIDAK TERUPDATE! "+terakhirtransaksi,Toast.LENGTH_LONG).show();
        } else {
            //membuat okhttp client
//            OkHttpClient.Builder okhttp = new OkHttpClient.Builder();
//
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//            okhttp.addInterceptor(logging);

            //membuat instance retrofit
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(ck.getBASE_URL())
                    .addConverterFactory(GsonConverterFactory.create());
//                    .client(okhttp.build());

            Retrofit retrofit = builder.build();

            JalaniMisi newjalanimisi = new JalaniMisi(user, misimenabung, status, "belum", datenow);

            //mendapatkan client & memanggil object
            UserClient client = retrofit.create(UserClient.class);
            Call<JalaniMisi> call2 = client.updateJalaniMisi(newjalanimisi);

            call2.enqueue(new Callback<JalaniMisi>() {
                @Override
                public void onResponse(Call<JalaniMisi> call, Response<JalaniMisi> response) {
                    if(response.isSuccessful()){
//                        Toast.makeText(getApplicationContext(), "INI UPDATE MENABUNG",Toast.LENGTH_LONG).show();
                    }
//                        Toast.makeText(getApplicationContext(), "Selamat Anda telah menyelesaikan Misi Menabung! Silahkan ke halaman misi untuk claim poin!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<JalaniMisi> call, Throwable t) {

                }
            });
        }
    }

    private void updatemisiditabung(String user, String misi, String status) {
        //membuat okhttp client
//        OkHttpClient.Builder okhttp = new OkHttpClient.Builder();
//
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        okhttp.addInterceptor(logging);

        //membuat instance retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ck.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create());
//                .client(okhttp.build());

        Retrofit retrofit = builder.build();

        JalaniMisi newjalanimisi = new JalaniMisi(user, misi, status, "belum", datenow);

        //mendapatkan client & memanggil object
        UserClient client = retrofit.create(UserClient.class);
        Call<JalaniMisi> call2 = client.updateJalaniMisi(newjalanimisi);

        call2.enqueue(new Callback<JalaniMisi>() {
            @Override
            public void onResponse(Call<JalaniMisi> call, Response<JalaniMisi> response) {
                if(response.isSuccessful()){
//                    Toast.makeText(getApplicationContext(), "INI UPDATE DITABUNG",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JalaniMisi> call, Throwable t) {

            }
        });
    }

    public void addHistory(String user, String namatransaksi, String datenow)
    {
//        OkHttpClient.Builder okhttp = new OkHttpClient.Builder();
//
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        okhttp.addInterceptor(logging);

        //membuat instance retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ck.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create());
//                .client(okhttp.build());

        Retrofit retrofit = builder.build();

        HistoriTransaksi historiTransaksi = new HistoriTransaksi(user, namatransaksi, datenow);

        final UserClient client = retrofit.create(UserClient.class);
        Call<HistoriTransaksi> call = client.addHistori(historiTransaksi);
        call.enqueue(new Callback<HistoriTransaksi>() {
            @Override
            public void onResponse(Call<HistoriTransaksi> call, Response<HistoriTransaksi> response) {

            }

            @Override
            public void onFailure(Call<HistoriTransaksi> call, Throwable t) {

            }
        });
    }
}
