package com.example.consult_app.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.consult_app.R;
import com.example.consult_app.activity.LoginActivity;
import com.example.consult_app.api.ApiInterfaces;
import com.example.consult_app.api.ApiServer;
import com.example.consult_app.model.ResponseBiodata;
import com.example.consult_app.model.ResponseUser;
import com.example.consult_app.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    AppCompatButton btnLogout;
    SharedPrefManager sharedPrefManager;
    TextView pekerjaan, nik, noregnas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        sharedPrefManager = new SharedPrefManager(getActivity());
        TextView nama = view.findViewById(R.id.nama);
        pekerjaan = view.findViewById(R.id.username);
        nik = view.findViewById(R.id.nik);
        noregnas = view.findViewById(R.id.noregnas);
        TextView email = view.findViewById(R.id.email);
        nama.setText(sharedPrefManager.getSPNama());
        email.setText(sharedPrefManager.getSPEmail());
        String id = sharedPrefManager.getSpId();
        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(sharedPrefManager.getSPToken());
            }
        });
        getBiodata(id);
        return view;
    }

    private void getBiodata(String id) {
        ApiInterfaces apiInterfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseBiodata> call = apiInterfaces.getBiodata(id);
        call.enqueue(new Callback<ResponseBiodata>() {
            @Override
            public void onResponse(Call<ResponseBiodata> call, Response<ResponseBiodata> response) {
                if (response.isSuccessful()){
                    nik.setText(response.body().getData().getNik());
                    noregnas.setText(response.body().getData().getNo_reg_nas());
                    pekerjaan.setText(response.body().getData().getPekerjaan());
                }else {
                    Toast.makeText(getContext(), "Tidak mendapatkan data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBiodata> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logout(String spToken) {
        ApiInterfaces apiEndpoint = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseUser> call = apiEndpoint.logout(spToken);
        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(), "Berhasil logout", Toast.LENGTH_SHORT).show();
                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                    startActivity(new Intent(getContext(), LoginActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    getActivity().finish();
                }else {
                    Toast.makeText(getActivity(), "Gagal logout", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {

            }
        });
    }
}