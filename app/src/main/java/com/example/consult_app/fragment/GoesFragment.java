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
import com.example.consult_app.adapter.LocationAdapter;
import com.example.consult_app.api.ApiInterfaces;
import com.example.consult_app.api.ApiServer;
import com.example.consult_app.model.KondisiModel;
import com.example.consult_app.model.LocationModel;
import com.example.consult_app.model.ResponseLokasi;
import com.example.consult_app.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoesFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<LocationModel> locationModels = new ArrayList<>();
    private String id;
    SharedPrefManager sharedPrefManager;
    Fragment fragment;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goes, container, false);

        recyclerView = view.findViewById(R.id.goes);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        sharedPrefManager = new SharedPrefManager(getContext());
        id = sharedPrefManager.getSpId();
        getPerjalanan(id);

        AppCompatImageButton btnTambah = view.findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new PostLokasiFragment())
                        .commit();
            }
        });



        return  view;
    }

    private void getPerjalanan(String id) {
        ApiInterfaces apiInterfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseLokasi> call = apiInterfaces.getLokasi(id);
        call.enqueue(new Callback<ResponseLokasi>() {
            @Override
            public void onResponse(Call<ResponseLokasi> call, Response<ResponseLokasi> response) {
                if (response.isSuccessful()){
                    locationModels = response.body().getData();
                    Log.d("TAG", "onResponse: "+locationModels);
                    adapter = new LocationAdapter(getContext(), locationModels);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLokasi> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}