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
import com.example.consult_app.model.ResponseKonsumsi;
import com.example.consult_app.model.ResponseObat;
import com.example.consult_app.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostMedicFragment extends Fragment {

    private EditText jumlah, terlewati;
    private Spinner periode;
    String sJumlah, sTerlewati, sPeriode, id;
    SharedPrefManager sharedPrefManager;
    FragmentManager fragmentManager;
    AppCompatButton btnKirim;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_medic, container, false);

        sharedPrefManager = new SharedPrefManager(getContext());
        jumlah = view.findViewById(R.id.jumlah);
        terlewati = view.findViewById(R.id.terlewati);
        periode = view.findViewById(R.id.bulan);

        btnKirim = view.findViewById(R.id.btnKirim);
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sJumlah = jumlah.getText().toString();
                sTerlewati = terlewati.getText().toString();
                sPeriode = periode.getSelectedItem().toString();
                id = sharedPrefManager.getSpId();
//                Toast.makeText(getContext(), sJumlah+sTerlewati+sPeriode, Toast.LENGTH_SHORT).show();
                postKonsumsi(id, sJumlah, sTerlewati, sPeriode);
            }
        });

        textView = view.findViewById(R.id.namaObat);

        getNamaObat(sharedPrefManager.getSpId());

        return  view;
    }

    private void getNamaObat(String spId) {
        ApiInterfaces apiInterfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseObat> call = apiInterfaces.getObat(spId);
        call.enqueue(new Callback<ResponseObat>() {
            @Override
            public void onResponse(Call<ResponseObat> call, Response<ResponseObat> response) {
                if (response.isSuccessful()){
                    Log.d("NAMA OBAT", "onResponse: "+response.body().getData());
                    for (int i = 0; i < response.body().getData().size(); i++){
                        Log.d("OBAT", "onResponse: "+response.body().getData().get(i).getNama());
                        textView.setText(response.body().getData().get(i).getNama());
                    }
                }else {
                    Toast.makeText(getContext(), "Obat tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObat> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postKonsumsi(String id, String sJumlah, String sTerlewati, String sPeriode) {
        ApiInterfaces apiInterfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseKonsumsi> call = apiInterfaces.postKonsumsi(id, sJumlah, sTerlewati, sPeriode);
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