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
import com.example.consult_app.adapter.KonsumsiAdapter;
import com.example.consult_app.adapter.LocationAdapter;
import com.example.consult_app.api.ApiInterfaces;
import com.example.consult_app.api.ApiServer;
import com.example.consult_app.model.KonsumsiModel;
import com.example.consult_app.model.LocationModel;
import com.example.consult_app.model.ResponseKonsumsi;
import com.example.consult_app.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedkitFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<KonsumsiModel> konsumsiModels = new ArrayList<>();
    private String id;
    SharedPrefManager sharedPrefManager;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medkit, container, false);

        recyclerView = view.findViewById(R.id.list_konsumsi);
        sharedPrefManager = new SharedPrefManager(getContext());
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        id = sharedPrefManager.getSpId();
        getKonsumsi(id);

        AppCompatImageButton btnTambah = view.findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new PostMedicFragment())
                        .commit();
            }
        });


        return view;
    }

    private void getKonsumsi(String id) {
        ApiInterfaces interfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseKonsumsi> call = interfaces.getKonsumsi(id);
        call.enqueue(new Callback<ResponseKonsumsi>() {
            @Override
            public void onResponse(Call<ResponseKonsumsi> call, Response<ResponseKonsumsi> response) {
                if (response.isSuccessful()){
                    konsumsiModels = response.body().getData();
                    Log.d("TAG", "onResponse: "+konsumsiModels);
                    adapter = new KonsumsiAdapter(getContext(), konsumsiModels);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseKonsumsi> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}