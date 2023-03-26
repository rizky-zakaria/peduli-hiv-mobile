package com.example.consult_app.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.consult_app.R;
import com.example.consult_app.adapter.HistoriAdapter;
import com.example.consult_app.adapter.NotifikasiAdapter;
import com.example.consult_app.api.ApiInterfaces;
import com.example.consult_app.api.ApiServer;
import com.example.consult_app.model.HistoriModel;
import com.example.consult_app.model.NotifikasiModel;
import com.example.consult_app.model.ResponseNotifikasi;
import com.example.consult_app.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifikasiFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<NotifikasiModel> notifikasiModels = new ArrayList<>();
    SharedPrefManager sharedPrefManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notifikasi, container, false);
        sharedPrefManager = new SharedPrefManager(getActivity());
        getData(sharedPrefManager.getSpId());


        recyclerView = v.findViewById(R.id.listData);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        return v;
    }

    private void getData(String spId) {
        ApiInterfaces apiInterfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseNotifikasi> call = apiInterfaces.getNotifikasi(spId);
        call.enqueue(new Callback<ResponseNotifikasi>() {
            @Override
            public void onResponse(Call<ResponseNotifikasi> call, Response<ResponseNotifikasi> response) {
                if (response.isSuccessful()){
                    notifikasiModels = response.body().getData();
                    Log.d("TAG", "onResponse: "+notifikasiModels);
                    adapter = new NotifikasiAdapter(getContext(), notifikasiModels);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseNotifikasi> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}