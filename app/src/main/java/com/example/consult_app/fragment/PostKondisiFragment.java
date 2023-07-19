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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.consult_app.R;
import com.example.consult_app.api.ApiInterfaces;
import com.example.consult_app.api.ApiServer;
import com.example.consult_app.model.ResponseKondisi;
import com.example.consult_app.utils.SharedPrefManager;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostKondisiFragment extends Fragment {

    EditText berat, keluhan;
    Spinner efek;
    String sBerat, sEfek, sKeluhan, id;
    SharedPrefManager sharedPrefManager;
    FragmentManager fragmentManager;
    TextView berat1, berat2, efek1, efek2, keluhan1, keluhan2;
    LinearLayout cd1, cd2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_post_kondisi, container, false);
        sharedPrefManager = new SharedPrefManager(getContext());

        berat1 = v.findViewById(R.id.berat1);
        berat2 = v.findViewById(R.id.berat2);
        efek1 = v.findViewById(R.id.efek1);
        efek2 = v.findViewById(R.id.efek2);
        keluhan1 = v.findViewById(R.id.keluhan1);
        keluhan2 = v.findViewById(R.id.keluhan2);
        cd1 = v.findViewById(R.id.cd1);
        cd2 = v.findViewById(R.id.cd2);

        AppCompatButton button = v.findViewById(R.id.btnKirim);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                berat = v.findViewById(R.id.berat);
                efek = v.findViewById(R.id.efek);
                keluhan = v.findViewById(R.id.keluhan);
                sBerat = berat.getText().toString();
                sEfek = efek.getSelectedItem().toString();
                sKeluhan = keluhan.getText().toString();
                id = sharedPrefManager.getSpId();
//                Log.d("TAG", "onClick: "+id+" "+sBerat+" "+sEfek+" "+sKeluhan);
                postKondisi(id,sBerat, sEfek, sKeluhan);
            }
        });

        getKeluhan(sharedPrefManager.getSpId());

        return v;
    }

    private void getKeluhan(String id){
        ApiInterfaces apiInterfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseKondisi> call = apiInterfaces.getKeluhan(id);
        call.enqueue(new Callback<ResponseKondisi>() {
            @Override
            public void onResponse(Call<ResponseKondisi> call, Response<ResponseKondisi> response) {
                if (response.isSuccessful()){
                    int countData = response.body().getData().size();
                    if (countData > 0){
                        cd1.setVisibility(View.VISIBLE);
                        cd2.setVisibility(View.VISIBLE);
                        berat1.setText(response.body().getData().get(0).getBerat());
                        berat2.setText(response.body().getData().get(1).getBerat());
                        efek1.setText(response.body().getData().get(0).getEfek());
                        efek2.setText(response.body().getData().get(1).getEfek());
                        keluhan1.setText(response.body().getData().get(0).getKeluhan());
                        keluhan2.setText(response.body().getData().get(1).getKeluhan());
                    }
                }else {
                    Log.d("TAG", "onResponse: "+response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseKondisi> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postKondisi(String id, String sBerat, String sEfek, String sKeluhan) {
        ApiInterfaces apiInterfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseKondisi> call = apiInterfaces.postKondisi(id, sBerat, sEfek, sKeluhan);
        call.enqueue(new Callback<ResponseKondisi>() {
            @Override
            public void onResponse(Call<ResponseKondisi> call, Response<ResponseKondisi> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(), "Berhasil mengirimkan kondisi saat ini", Toast.LENGTH_SHORT).show();
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, new KondisiFragment())
                            .commit();
                }else {
                    Toast.makeText(getActivity(), "Gagal Mengirimkan kondisi saat ini", Toast.LENGTH_SHORT).show();
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, new KondisiFragment())
                            .commit();
                }
            }

            @Override
            public void onFailure(Call<ResponseKondisi> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}