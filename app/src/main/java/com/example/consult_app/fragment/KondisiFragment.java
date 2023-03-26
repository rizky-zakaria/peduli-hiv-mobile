package com.example.consult_app.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.consult_app.R;
import com.example.consult_app.adapter.KondisiAdapter;
import com.example.consult_app.api.ApiInterfaces;
import com.example.consult_app.api.ApiServer;
import com.example.consult_app.model.ClusterModel;
import com.example.consult_app.model.KondisiModel;
import com.example.consult_app.model.ResponseKondisi;
import com.example.consult_app.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KondisiFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<KondisiModel> kondisiModels = new ArrayList<>();
    private String id;
    SharedPrefManager sharedPrefManager;
    AppCompatImageButton btnTambah;
    FragmentManager fragmentManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_kondisi, container, false);
        recyclerView = v.findViewById(R.id.rvData);
        sharedPrefManager = new SharedPrefManager(getContext());
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        id = sharedPrefManager.getSpId();
        btnTambah = v.findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new PostKondisiFragment())
                        .commit();
            }
        });

        getKondisi(id);
        return v;
    }

    private void getKondisi(String id) {
        ApiInterfaces apiInterfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseKondisi> call = apiInterfaces.getKondisi(id);
        call.enqueue(new Callback<ResponseKondisi>() {
            @Override
            public void onResponse(Call<ResponseKondisi> call, Response<ResponseKondisi> response) {
                Log.d("TAG", "onResponse: "+response.isSuccessful());
                if (response.isSuccessful()){
                    kondisiModels = response.body().getData();
                    Log.d("TAG", "onResponse: "+kondisiModels);
                    adapter = new KondisiAdapter(getContext(), kondisiModels);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseKondisi> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}