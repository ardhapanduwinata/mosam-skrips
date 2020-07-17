package com.arwinata.am.skripsi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arwinata.am.skripsi.Retrofit.model.HistoriTransaksi;
import com.arwinata.am.skripsi.Retrofit.model.ListJalaniMisi;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoriAdapter extends RecyclerView.Adapter<HistoriAdapter.HistoriViewHolder> {
    private Context context;
    private List<HistoriTransaksi> datalist;

    public HistoriAdapter(List<HistoriTransaksi> datalist, Context context){
        this.datalist = datalist;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_item_histori, parent, false);

        return new HistoriViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoriViewHolder holder, int position) {
        holder.tvnamatransaksi.setText(datalist.get(position).getNamaTransaksi());
        holder.tvdate.setText(datalist.get(position).getDate());
    }

    @Override
    public int getItemCount() {return datalist.size();}

    public class HistoriViewHolder extends RecyclerView.ViewHolder {
        private TextView tvnamatransaksi, tvdate;
        public HistoriViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            tvnamatransaksi = itemView.findViewById(R.id.tvnamatransaksi);
            tvdate = itemView.findViewById(R.id.tvdate);
        }
    }
}
