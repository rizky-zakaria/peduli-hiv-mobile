package com.example.consult_app.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consult_app.R;
import com.example.consult_app.fragment.OptionalChatFragment;
import com.example.consult_app.model.ChatModel;
import com.example.consult_app.model.ClusterModel;
import com.example.consult_app.utils.SharedPrefManager;

import java.util.List;

public class ClusterAdapter extends RecyclerView.Adapter<ClusterAdapter.HolderData> {
    private Context context;
    private List<ClusterModel> clusterModels;
    private String id;
    SharedPrefManager sessionManager;

    private RecyclerView rvChat;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ChatModel> list;

    public ClusterAdapter(Context context, List<ClusterModel> clusterModels) {
        this.context = context;
        this.clusterModels = clusterModels;
    }

    @NonNull
    @Override
    public ClusterAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cluster, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull ClusterAdapter.HolderData holder, int position) {
        ClusterModel model = clusterModels.get(position);
        holder.nama.setText("dokter " + model.getNama_dokter());
        id = model.getId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id", model.getId());
                Fragment fragment = new OptionalChatFragment();
                fragment.setArguments(bundle);
                ((FragmentActivity)context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .commit();
            }
        });
    }



    @Override
    public int getItemCount() {
        return clusterModels.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView nama;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);

        }
    }
}
