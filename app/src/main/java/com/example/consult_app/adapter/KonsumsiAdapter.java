package com.example.consult_app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consult_app.R;
import com.example.consult_app.model.KonsumsiModel;

import java.util.List;

public class KonsumsiAdapter extends RecyclerView.Adapter<KonsumsiAdapter.HolderData> {

    private Context context;
    private List<KonsumsiModel> konsumsiModels;

    public KonsumsiAdapter(Context context, List<KonsumsiModel> konsumsiModels) {
        this.context = context;
        this.konsumsiModels = konsumsiModels;
    }

    @NonNull
    @Override
    public KonsumsiAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_konsumsi, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull KonsumsiAdapter.HolderData holder, int position) {
        KonsumsiModel model = konsumsiModels.get(position);
        holder.lewat.setText(model.getTerlewati());
        holder.konsumsi.setText(model.getKonsumsi());
        holder.periode.setText(model.getPeriode());
        if (Double.parseDouble(model.getKepatuhan()) <= 80){
            holder.kepatuhan.setTextColor(Color.parseColor("#bd0000"));
            holder.kepatuhan.setText(Double.parseDouble(model.getKepatuhan())+"%");
        }else if (Double.parseDouble(model.getKepatuhan()) <= 95){
            holder.kepatuhan.setTextColor(Color.parseColor("#bec91e"));
            holder.kepatuhan.setText(Double.parseDouble(model.getKepatuhan())+"%");
        }else {
            holder.kepatuhan.setTextColor(Color.parseColor("#15ab15"));
            holder.kepatuhan.setText(Double.parseDouble(model.getKepatuhan())+"%");
        }
    }

    @Override
    public int getItemCount() {
        return konsumsiModels.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView periode, konsumsi, lewat, kepatuhan;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            periode = itemView.findViewById(R.id.periode);
            konsumsi = itemView.findViewById(R.id.konsumsi);
            lewat = itemView.findViewById(R.id.lewat);
            kepatuhan = itemView.findViewById(R.id.kepatuhan);
        }
    }
}
