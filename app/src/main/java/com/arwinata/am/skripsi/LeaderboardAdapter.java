package com.arwinata.am.skripsi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arwinata.am.skripsi.Retrofit.CheckingConnection;
import com.arwinata.am.skripsi.Retrofit.model.Lencana;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {

    private List<Lencana> datalist;
    private Context context;
    CheckingConnection ck = new CheckingConnection();

    public LeaderboardAdapter(List<Lencana> datalist, Context context){
        this.datalist = datalist;
        this.context = context;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_item_leaderboard, parent, false);

        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {

        holder.tvnamauser.setText(datalist.get(position).getNamaUser());
        holder.tvbadge.setText(datalist.get(position).getBadge());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder {
        ImageView ivbadge;
        TextView tvnamauser, tvbadge;
        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            ivbadge = itemView.findViewById(R.id.ivbadge);
            tvnamauser = itemView.findViewById(R.id.tvnamauser);
            tvbadge = itemView.findViewById(R.id.tvbadge);
        }
    }
}
