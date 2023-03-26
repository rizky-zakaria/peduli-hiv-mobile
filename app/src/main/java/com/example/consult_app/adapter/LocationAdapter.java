package com.example.consult_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consult_app.R;
import com.example.consult_app.model.LocationModel;
import com.example.consult_app.utils.SharedPrefManager;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.HolderData> {

    private Context context;
    private List<LocationModel> locationModels;
    private String id;
    SharedPrefManager sharedPrefManager;

    public LocationAdapter(Context context, List<LocationModel> locationModels) {
        this.context = context;
        this.locationModels = locationModels;
    }

    @NonNull
    @Override
    public LocationAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_location, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.HolderData holder, int position) {
        LocationModel model = locationModels.get(position);
        holder.tujuan.setText(model.getTujuan());
        holder.kunjungan.setText(model.getTgl_kunjungan());
        holder.pulang.setText(model.getTgl_pulang());
        holder.keterangan.setText(model.getKeterangan());
    }

    @Override
    public int getItemCount() {
        return locationModels.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tujuan, kunjungan, pulang, keterangan;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            tujuan = itemView.findViewById(R.id.tujuan);
            kunjungan = itemView.findViewById(R.id.kunjungan);
            pulang = itemView.findViewById(R.id.pulang);
            keterangan = itemView.findViewById(R.id.lokasi);
        }
    }
}
