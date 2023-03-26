package com.example.consult_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consult_app.R;
import com.example.consult_app.model.KondisiModel;
import com.example.consult_app.model.LocationModel;
import com.example.consult_app.utils.SharedPrefManager;
import com.google.android.material.transition.Hold;

import java.util.List;

public class KondisiAdapter extends RecyclerView.Adapter<KondisiAdapter.HolderData> {

    private Context context;
    private List<KondisiModel> kondisiModels;

    public KondisiAdapter(Context context, List<KondisiModel> kondisiModels) {
        this.context = context;
        this.kondisiModels = kondisiModels;
    }

    @NonNull
    @Override
    public KondisiAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kondisi, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull KondisiAdapter.HolderData holder, int position) {
        KondisiModel model = kondisiModels.get(position);
        holder.tv.setText("Anda memiliki berat badan "+model.getBerat()+"kg, Terindikasi gejala "+model.getEfek()+", serta memiliki keluhan "+model.getKeluhan());
    }

    @Override
    public int getItemCount() {
        return kondisiModels.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tv;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvKondisi);
        }
    }
}
