package com.arwinata.am.skripsi;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arwinata.am.skripsi.Retrofit.CheckingConnection;
import com.arwinata.am.skripsi.Retrofit.model.HistoriTransaksi;
import com.arwinata.am.skripsi.Retrofit.model.JalaniMisi;
import com.arwinata.am.skripsi.Retrofit.model.ListJalaniMisi;
import com.arwinata.am.skripsi.Retrofit.model.PoinDanVoucher;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MisiAdapter extends RecyclerView.Adapter<MisiAdapter.MisiViewHolder> {

    private List<ListJalaniMisi> dataList;
    private Context context;
    CheckingConnection ck = new CheckingConnection();

    public MisiAdapter(List<ListJalaniMisi> dataList, Context context){
        this.dataList = dataList;
        this.context = context;
    }

    Date currentTime = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    String datenow = df.format(currentTime);

    @NonNull
    @Override
    public MisiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.activity_item_misi,parent, false);

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_item_misi, parent, false);
        return new MisiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MisiViewHolder holder, final int position) {

        holder.detailmisi.setText(dataList.get(position).getDetailmisi());
        holder.tvpoin.setText(dataList.get(position).getPoin()+" Poin");

        if(dataList.get(position).getStatus().equals("belum")){
            holder.btnclaim.setText("go");
            holder.btnclaim.setBackgroundResource(R.drawable.pinkroundedbg);
            holder.btnclaim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(dataList.get(position).getTabeldibutuhkan().equals("transaksiNabung")){
                        Intent i = new Intent(context, QrCode.class);
                        context.startActivity(i);
                        ((Activity)context).finish();
                    } else if(dataList.get(position).getTabeldibutuhkan().equals("transaksiTukarVoucher")){
                        Intent i = new Intent(context, TukarTiket.class);
                        context.startActivity(i);
                        ((Activity)context).finish();
                    }
                }
            });
        }
        if(dataList.get(position).getStatus().equals("sudah")){
            holder.btnclaim.setText("claim");
            holder.btnclaim.setBackgroundResource(R.drawable.greenroundedbg);

            holder.btnclaim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    claimpoin(dataList.get(position).getUser(), dataList.get(position).getMisi(), dataList.get(position).getPoin(), dataList.get(position).getDateselesai());

                    Toast.makeText(context, "Claiming......", Toast.LENGTH_LONG).show();
//                    holder.btnclaim.setText("claimed");
                    holder.btnclaim.setEnabled(false);
                }
            });
        }
        if(dataList.get(position).getClaimpoin().equals("sudah"))
        {
            holder.btnclaim.setText("claimed");
            holder.btnclaim.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MisiViewHolder extends RecyclerView.ViewHolder {
        private TextView detailmisi, tvpoin;
        private Button btnclaim;
        public MisiViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            detailmisi = itemView.findViewById(R.id.tvdetailmisi);
            btnclaim = itemView.findViewById(R.id.buttonclaim);
            tvpoin = itemView.findViewById(R.id.tvpoin);
        }
    }

    public void claimpoin(final String user, String misi, final int poin, String dateselesai)
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

        JalaniMisi newjalanimisi = new JalaniMisi(user, misi, "sudah", "sudah", dateselesai);

        final UserClient client = retrofit.create(UserClient.class);
        Call<JalaniMisi> call = client.updateJalaniMisi(newjalanimisi);

        call.enqueue(new Callback<JalaniMisi>() {
            @Override
            public void onResponse(Call<JalaniMisi> call, Response<JalaniMisi> response) {
                updatepoin(user, poin);
            }

            @Override
            public void onFailure(Call<JalaniMisi> call, Throwable t) {

            }
        });
    }

    public void updatepoin(final String user, final int jmlpoin){
//        OkHttpClient.Builder okhttp = new OkHttpClient.Builder();

//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        okhttp.addInterceptor(logging);

        //membuat instance retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ck.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create());
//                .client(okhttp.build());

        Retrofit retrofit = builder.build();
        final UserClient client = retrofit.create(UserClient.class);

        Call<PoinDanVoucher> call1 = client.cekPoin(user);
        call1.enqueue(new Callback<PoinDanVoucher>() {
            @Override
            public void onResponse(Call<PoinDanVoucher> call, Response<PoinDanVoucher> response) {
                int jumlahtotal = response.body().getJmlPoin()+jmlpoin;

//                Toast.makeText(context.getApplicationContext(), "ini jumlah poinnya"+jumlahtotal, Toast.LENGTH_LONG).show();

                PoinDanVoucher poinDanVoucher = new PoinDanVoucher(jumlahtotal);
                addHistory(user, "Claim poin sebanyak "+jmlpoin, datenow);

                Call<PoinDanVoucher> call2 = client.updatePoin(user, poinDanVoucher);
                call2.enqueue(new Callback<PoinDanVoucher>() {
                    @Override
                    public void onResponse(Call<PoinDanVoucher> call, Response<PoinDanVoucher> response) {
//                        Toast.makeText(context, "Ini jumlah poin total" +response.body().getJmlPoin(), Toast.LENGTH_LONG).show();
                        Toast.makeText(context, "Selamat anda telah claim Poin anda!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(context, Mission.class);

                        ((Activity)context).finish();
                        ((Activity)context).overridePendingTransition( 0, 0);
                        context.startActivity(i);
                        ((Activity)context).overridePendingTransition( 0, 0);
                    }

                    @Override
                    public void onFailure(Call<PoinDanVoucher> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<PoinDanVoucher> call, Throwable t) {

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
