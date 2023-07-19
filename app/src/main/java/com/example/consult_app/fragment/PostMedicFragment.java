package com.example.consult_app.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.consult_app.R;
import com.example.consult_app.api.ApiInterfaces;
import com.example.consult_app.api.ApiServer;
import com.example.consult_app.model.ResponseDetailKonsumsi;
import com.example.consult_app.model.ResponseKonsumsi;
import com.example.consult_app.model.ResponseObat;
import com.example.consult_app.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostMedicFragment extends Fragment {

    private EditText jumlah;
    String sJumlah, id;
    SharedPrefManager sharedPrefManager;
    FragmentManager fragmentManager;
    AppCompatButton btnKirim;
    TextView textView, namaObat, jumlahObat, dosisObat, waktuMinum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_medic, container, false);

        sharedPrefManager = new SharedPrefManager(getContext());
        jumlah = view.findViewById(R.id.jumlah);
        jumlahObat = view.findViewById(R.id.jumlahObat);
        dosisObat = view.findViewById(R.id.dosisObat);
        waktuMinum = view.findViewById(R.id.waktuMinum);
        namaObat = view.findViewById(R.id.namaObat);

        btnKirim = view.findViewById(R.id.btnKirim);
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sJumlah = jumlah.getText().toString();
                id = sharedPrefManager.getSpId();
//                Toast.makeText(getContext(), sJumlah+sTerlewati+sPeriode, Toast.LENGTH_SHORT).show();
                postKonsumsi(id, sJumlah);
            }
        });

        textView = view.findViewById(R.id.namaObat);

        getNamaObat(sharedPrefManager.getSpId());

        return  view;
    }

    private void getNamaObat(String spId) {
        ApiInterfaces apiInterfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseDetailKonsumsi> call = apiInterfaces.getDetailObat(spId);
        call.enqueue(new Callback<ResponseDetailKonsumsi>() {
            @Override
            public void onResponse(Call<ResponseDetailKonsumsi> call, Response<ResponseDetailKonsumsi> response) {
                Toast.makeText(getContext(), response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()){
                    textView.setText("Nama Obat: "+response.body().getData().getNama());
                    jumlahObat.setText("Jumlah Obat: "+response.body().getData().getJumlah());
                    dosisObat.setText("Dosis Obat: "+response.body().getData().getDosis());
                    waktuMinum.setText("Waktu Minum: "+response.body().getData().getJam()+":"+response.body().getData().getMenit());
                }else {
                    Toast.makeText(getContext(), "Obat tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetailKonsumsi> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postKonsumsi(String id, String sJumlah) {
        ApiInterfaces apiInterfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseKonsumsi> call = apiInterfaces.postKonsumsi(id, sJumlah);
        call.enqueue(new Callback<ResponseKonsumsi>() {
            @Override
            public void onResponse(Call<ResponseKonsumsi> call, Response<ResponseKonsumsi> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(), "Berhasil mengirimkan data konsumsi obat", Toast.LENGTH_SHORT).show();
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, new MedkitFragment())
                            .commit();
                }else {
                    Toast.makeText(getContext(), "Gagal mengirimkan data konsumsi obat", Toast.LENGTH_SHORT).show();
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, new MedkitFragment())
                            .commit();
                }
            }

            @Override
            public void onFailure(Call<ResponseKonsumsi> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}