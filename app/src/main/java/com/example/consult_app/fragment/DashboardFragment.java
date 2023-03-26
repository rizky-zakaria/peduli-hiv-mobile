package com.example.consult_app.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.consult_app.R;
import com.example.consult_app.adapter.HistoriAdapter;
import com.example.consult_app.adapter.LocationAdapter;
import com.example.consult_app.api.ApiInterfaces;
import com.example.consult_app.api.ApiServer;
import com.example.consult_app.model.HistoriModel;
import com.example.consult_app.model.LocationModel;
import com.example.consult_app.model.ResponseHistori;
import com.example.consult_app.model.ResponseLokasi;
import com.example.consult_app.utils.SharedPrefManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {


    TextView role, uName;
    FusedLocationProviderClient fusedLocationProviderClient;
    String username, userid, nama, token;

    private TextView notfound;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<HistoriModel> locationModels = new ArrayList<>();
    private String id;
    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        sharedPrefManager= new SharedPrefManager(getActivity());
        username = sharedPrefManager.getSPNama();
        userid = sharedPrefManager.getSpId();
        nama = sharedPrefManager.getSPNama();
        token = sharedPrefManager.getSPToken();
        uName = v.findViewById(R.id.username);
        uName.setText(nama);
        role = v.findViewById(R.id.role);
        id = sharedPrefManager.getSpId();
        getPerjalanan(id);
        role.setText(sharedPrefManager.getSpRole());

        recyclerView = v.findViewById(R.id.listData);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        return v;
    }

    private void getPerjalanan(String id) {
        ApiInterfaces apiInterfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseHistori> call = apiInterfaces.getHistori(id);
        call.enqueue(new Callback<ResponseHistori>() {
            @Override
            public void onResponse(Call<ResponseHistori> call, Response<ResponseHistori> response) {
                if (response.isSuccessful()){
                    locationModels = response.body().getData();
                    Log.d("TAG", "onResponse: "+locationModels);
                    adapter = new HistoriAdapter(getContext(), locationModels);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHistori> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}