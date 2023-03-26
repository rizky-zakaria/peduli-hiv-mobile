package com.example.consult_app.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.consult_app.R;
import com.example.consult_app.api.ApiInterfaces;
import com.example.consult_app.api.ApiServer;
import com.example.consult_app.model.ResponseLokasi;
import com.example.consult_app.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostLokasiFragment extends Fragment {

    EditText tujuan, pergi, pulang, keterangan;
    AppCompatButton btnKirim;
    String sTujuan, sPergi, sPulang, sKet, id;
    SharedPrefManager sharedPrefManager;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_post_lokasi, container, false);

        sharedPrefManager = new SharedPrefManager(getContext());
        id = sharedPrefManager.getSpId();
        btnKirim = v.findViewById(R.id.btnKirim);
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tujuan = v.findViewById(R.id.tujuan);
                pergi = v.findViewById(R.id.waktu_pergi);
                pulang = v.findViewById(R.id.waktu_pulang);
                keterangan = v.findViewById(R.id.keterangan);

                sTujuan = tujuan.getText().toString();
                sPergi = pergi.getText().toString();
                sPulang = pulang.getText().toString();
                sKet = keterangan.getText().toString();
                postLokasi(id, sPergi, sPulang, sTujuan, sKet);

            }
        });

        return v;
    }

    private void postLokasi(String id, String sPergi, String sPulang, String sTujuan, String sKet) {
        ApiInterfaces apiInterfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseLokasi> call = apiInterfaces.postLokasi(id, sPergi, sPulang, sTujuan, sKet);
        call.enqueue(new Callback<ResponseLokasi>() {
            @Override
            public void onResponse(Call<ResponseLokasi> call, Response<ResponseLokasi> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(), "Berhasil mengirim laporan perjalanan", Toast.LENGTH_SHORT).show();
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_layout, new GoesFragment()).commit();
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