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
import com.example.consult_app.model.ResponseHistori;

import org.w3c.dom.Text;

import java.util.List;

public class HistoriAdapter extends RecyclerView.Adapter<HistoriAdapter.HolderDara> {

    private Context context;
    private List<HistoriModel> historiModels;

    public HistoriAdapter(Context context, List<HistoriModel> historiModels) {
        this.context = context;
        this.historiModels = historiModels;
    }

    @NonNull
    @Override
    public HistoriAdapter.HolderDara onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_histori, parent, false);
        HolderDara holderDara = new HolderDara(view);
        return holderDara;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoriAdapter.HolderDara holder, int position) {
        HistoriModel model = historiModels.get(position);
        holder.tv.setText(model.getHistori());
    }

    @Override
    public int getItemCount() {
        return historiModels.size();
    }

    public class HolderDara extends RecyclerView.ViewHolder{
        TextView tv;
        public HolderDara(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvKondisi);
        }
    }
}
