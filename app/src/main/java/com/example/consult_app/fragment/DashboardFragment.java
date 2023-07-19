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
import androidx.fragment.app.FragmentManager;
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
import com.google.android.gms.tasks.OnSuccessListener;
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
    private final static int REQUEST_CODE = 100;
    private TextView notfound;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<HistoriModel> locationModels = new ArrayList<>();
    private String id;
    String latitude, longitude, tujuan;
    SharedPrefManager sharedPrefManager;

    FragmentManager fragmentManager;

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

        role.setText(sharedPrefManager.getSpRole());

        recyclerView = v.findViewById(R.id.listData);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        getLocation();

        return v;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null){
                                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                    latitude = String.valueOf(addresses.get(0).getLatitude());
                                    longitude = String.valueOf(addresses.get(0).getLongitude());
                                    tujuan = String.valueOf(addresses.get(0).getSubLocality()
                                                    +", "+addresses.get(0).getLocality()
                                                    +", "+addresses.get(0).getSubAdminArea()
                                                    +", "+addresses.get(0).getAdminArea()
                                                    +", "+addresses.get(0).getCountryName()
                                            );
                                    postLokasi(tujuan, id);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
        }else {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]
                {android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation();
            }else {
                Toast.makeText(getActivity(), "Silahkan izinkan terlebih dahulu", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void postLokasi(String tujuan, String id) {
        ApiInterfaces apiInterfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseLokasi> call = apiInterfaces.postLokasi(tujuan, id);
        call.enqueue(new Callback<ResponseLokasi>() {
            @Override
            public void onResponse(Call<ResponseLokasi> call, Response<ResponseLokasi> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(), "Berhasil mengirim laporan perjalanan", Toast.LENGTH_SHORT).show();
//                    fragmentManager = getActivity().getSupportFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.frame_layout, new DashboardFragment()).commit();
                }else {
                    Toast.makeText(getContext(), "Gagal mengirim laporan perjalanan", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseLokasi> call, Throwable t) {
                Toast.makeText(getContext(), "Silahkan periksa koneksi internet anda", Toast.LENGTH_SHORT).show();
            }
        });

    }

}