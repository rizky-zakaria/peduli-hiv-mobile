package com.example.consult_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consult_app.R;
import com.example.consult_app.model.HistoriModel;
import com.example.consult_app.model.NotifikasiModel;

import java.util.List;

public class NotifikasiAdapter extends RecyclerView.Adapter<NotifikasiAdapter.HolderDara>{

    private Context context;
    private List<NotifikasiModel> notifikasiModels;

    public NotifikasiAdapter(Context context, List<NotifikasiModel> notifikasiModels) {
        this.context = context;
        this.notifikasiModels = notifikasiModels;
    }

    @NonNull
    @Override
    public NotifikasiAdapter.HolderDara onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_histori, parent, false);
        NotifikasiAdapter.HolderDara holderDara = new NotifikasiAdapter.HolderDara(view);
        return holderDara;
    }

    @Override
    public void onBindViewHolder(@NonNull NotifikasiAdapter.HolderDara holder, int position) {
        NotifikasiModel model = notifikasiModels.get(position);
        holder.tv.setText(model.getNotif());
    }

    @Override
    public int getItemCount() {
        return notifikasiModels.size();
    }

    public class HolderDara extends RecyclerView.ViewHolder{
        TextView tv;
        public HolderDara(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvKondisi);
        }
    }
}
