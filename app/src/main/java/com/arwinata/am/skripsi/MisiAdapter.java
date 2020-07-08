package com.arwinata.am.skripsi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arwinata.am.skripsi.Retrofit.CheckingConnection;
import com.arwinata.am.skripsi.Retrofit.model.JalanimisiItem;
import com.arwinata.am.skripsi.Retrofit.model.Misi;
import com.arwinata.am.skripsi.Retrofit.service.UserClient;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MisiAdapter extends RecyclerView.Adapter<MisiAdapter.MisiViewHolder> {

    private List<Misi> dataList;
    private Context context;
    CheckingConnection ck = new CheckingConnection();

    public MisiAdapter(List<Misi> dataList, Context context){
        this.dataList = dataList;
        this.context = context;
    }

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
    public void onBindViewHolder(@NonNull MisiViewHolder holder, final int position) {

        holder.detailmisi.setText(dataList.get(position).getDetailmisi());
        holder.target.setText("0/"+dataList.get(position).getTargetcapaian());
        holder.btnclaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "ini button ke "+ position, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MisiViewHolder extends RecyclerView.ViewHolder {
        private TextView detailmisi, target;
        private Button btnclaim;
        public MisiViewHolder(@NonNull View itemView) {
            super(itemView);
            detailmisi = itemView.findViewById(R.id.tvdetailmisi);
            target = itemView.findViewById(R.id.tvtarget);
            btnclaim = itemView.findViewById(R.id.buttonclaim);
        }
    }

    public void claimpoin()
    {
        OkHttpClient.Builder okhttp = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttp.addInterceptor(logging);

        //membuat instance retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ck.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp.build());

        Retrofit retrofit = builder.build();

        String user = "5efbee383f18cb1d6c0d4ab0";
        String misi = "5f02c17093fd041c64c80ca9";

        final UserClient client = retrofit.create(UserClient.class);
        Call<JalanimisiItem> call = client.updateJalaniMisi(user, misi);

        call.enqueue(new Callback<JalanimisiItem>() {
            @Override
            public void onResponse(Call<JalanimisiItem> call, Response<JalanimisiItem> response) {

            }

            @Override
            public void onFailure(Call<JalanimisiItem> call, Throwable t) {

            }
        });
    }
}
