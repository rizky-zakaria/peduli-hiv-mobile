package com.example.consult_app.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.consult_app.R;
import com.example.consult_app.adapter.ClusterAdapter;
import com.example.consult_app.api.ApiInterfaces;
import com.example.consult_app.api.ApiServer;
import com.example.consult_app.model.ClusterModel;
import com.example.consult_app.model.ResponseCluster;
import com.example.consult_app.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends Fragment {

    private TextView notfound;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ClusterModel> clusterModels = new ArrayList<>();
    private String id;
    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.rvCluster);
        sharedPrefManager = new SharedPrefManager(getContext());
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        id = sharedPrefManager.getSpId();
        getCluster(id);
//        Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();

        notfound = view.findViewById(R.id.notfound);
        return view;
    }

    private void getCluster(String id) {
//        Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
        ApiInterfaces apiInterfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseCluster> responseClusterCall = apiInterfaces.dataCluster(id);
        responseClusterCall.enqueue(new Callback<ResponseCluster>() {
            @Override
            public void onResponse(Call<ResponseCluster> call, Response<ResponseCluster> response) {
                if (response.isSuccessful()){
                    clusterModels = response.body().getData();
                    adapter = new ClusterAdapter(getContext(), clusterModels);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    notfound.setVisibility(View.INVISIBLE);
                }else {
//                    Toast.makeText(getActivity(), "Gagal Menampilkan Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCluster> call, Throwable t) {

            }
        });

    }
}