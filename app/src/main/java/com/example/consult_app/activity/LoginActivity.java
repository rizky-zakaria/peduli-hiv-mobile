package com.example.consult_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.consult_app.R;
import com.example.consult_app.api.ApiInterfaces;
import com.example.consult_app.api.ApiServer;
import com.example.consult_app.model.ResponseUser;
import com.example.consult_app.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    AppCompatButton btnMasuk;
    EditText username, password;
    String user, pass;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPrefManager = new SharedPrefManager(getApplicationContext());

        if (sharedPrefManager.getSPSudahLogin().equals(true)){
            if (sharedPrefManager.getSpRole().equals("pasien")){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }else {
                Toast.makeText(getApplicationContext(), "Aplikasi Hanya Diperuntukan Pasien Yang Telah Terdaftar", Toast.LENGTH_SHORT).show();
            }
        }
        btnMasuk = findViewById(R.id.masuk);
        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = findViewById(R.id.username);
                password = findViewById(R.id.password);
                user = username.getText().toString();
                pass = password.getText().toString();
                if (user.equals("")){
                    username.setError("Mohon di isi");
                }else if (pass.equals("")){
                    password.setError("Mohon di isi");
                }else {
                    funcLogin(user, pass);
                }
            }
        });

    }

    private void funcLogin(String user, String pass) {
        ApiInterfaces apiInterfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseUser> call = apiInterfaces.dataLogin(user, pass);
        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                Toast.makeText(getApplicationContext(), "Hasilnya", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()){
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, response.body().getUserModel().getName());
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, response.body().getUserModel().getEmail());
                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_ID, String.valueOf(response.body().getUserModel().getId()));
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_TOKEN, "Bearer "+ response.body().getToken());
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_ROLE, response.body().getUserModel().getRole());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "Gagal Login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}