package com.example.consult_app.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.consult_app.R;
import com.example.consult_app.adapter.ChatAdapter;
import com.example.consult_app.api.ApiInterfaces;
import com.example.consult_app.api.ApiServer;
import com.example.consult_app.model.ChatModel;
import com.example.consult_app.model.ResponseChat;
import com.example.consult_app.utils.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OptionalChatFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    FloatingActionButton formatForm;
    private RecyclerView rvChat;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ChatModel> list;
    String id, pesan;
    private SwipeRefreshLayout refreshLayout;
    public static String KEY_FRG = "key";
    EditText etChat;
    SharedPrefManager sharedPrefManager;
    String token;
    FloatingActionButton kirim;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_optional_chat, container, false);
        rvChat = v.findViewById(R.id.chat);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvChat.setLayoutManager(layoutManager);
        sharedPrefManager = new SharedPrefManager(getActivity());
        formatForm = v.findViewById(R.id.btnFormat);
        refreshLayout = v.findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        id = sharedPrefManager.getSpId();
        etChat = v.findViewById(R.id.eChat);
        kirim = v.findViewById(R.id.btnKirim);
        getChat(id);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                refreshLayout.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        refreshLayout.setRefreshing(true);
//                    }
//                });
//            }
//        }, 300000);

        formatForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FormatFormFragment();
                Bundle bundle = new Bundle();
                bundle.putString(KEY_FRG, id);
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .commit();
            }
        });


        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pesan = etChat.getText().toString();
                if (pesan.equals("")){
                    etChat.setError("Mohon kolom pesan diisi!");
                }else {
                    postChat(pesan, id);
                }
            }
        });


        return  v;
    }

    private void postChat(String pesan, String id) {
        ApiInterfaces interfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseChat> call = interfaces.postChat(id, pesan);
        call.enqueue(new Callback<ResponseChat>() {
            @Override
            public void onResponse(Call<ResponseChat> call, Response<ResponseChat> response) {
                if (response.isSuccessful()){
                    getChat(id);
                    etChat.setText("");
                }else {
                    Toast.makeText(getContext(), "Gagal mengirim chat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseChat> call, Throwable t) {

            }
        });
    }

    private void getChat(String id) {
//        Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
        ApiInterfaces apiInterfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseChat> call = apiInterfaces.getChat(id);
        call.enqueue(new Callback<ResponseChat>() {
            @Override
            public void onResponse(Call<ResponseChat> call, Response<ResponseChat> response) {
                if (response.isSuccessful()){
                    list = response.body().getData();
                    adapter = new ChatAdapter(getContext(), list);
                    rvChat.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getActivity(), "Data Tidak Tersedia", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseChat> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        getChat(id);
    }
}