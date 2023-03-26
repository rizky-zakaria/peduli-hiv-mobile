package com.example.consult_app.fragment;

import static android.app.Activity.RESULT_OK;
import static com.example.consult_app.R.id.imgHolder;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.consult_app.R;
import com.example.consult_app.api.ApiInterfaces;
import com.example.consult_app.api.ApiServer;
import com.example.consult_app.model.ResponseImage;
import com.example.consult_app.utils.SharedPrefManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormatFormFragment extends Fragment {

    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    ImageView img;
    private String mediaPath;
    private String postPath;
    private Bitmap bitmap;
    SharedPrefManager sharedPrefManager;
    public static String KEY_FRG = "key";
    String idcluster;

    // Akses Izin Ambil Gambar dari Storage
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            insertData(Integer.parseInt(idcluster));
        }
    }

    public void setRequestWritePermission(int requestCode, String[] permissions, int[] grantResults){
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            insertData(Integer.parseInt(idcluster));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_format_form, container, false);
        img = view.findViewById(imgHolder);
        sharedPrefManager = new SharedPrefManager(getActivity());
        AppCompatButton btnPilih = view.findViewById(R.id.pilih);
        AppCompatButton btnSimpan = view.findViewById(R.id.kirim);

        idcluster = getArguments().getString(KEY_FRG);

        btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galeri = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galeri, REQUEST_PICK_PHOTO);
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
                } else {
                    insertData( Integer.parseInt(idcluster));
                }
            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    // Ambil Image Dari Galeri dan Foto
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

//                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null );
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    img.setImageURI(data.getData());
                    cursor.close();
                    postPath = mediaPath;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private void insertData(int id) {
//        Log.d("TAG", "insertData: "+bitmap);
        if (mediaPath == null){
            Toast.makeText(getContext(), "Silahkan pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
        }else {
            File imageFIle = new File(mediaPath);
            ApiInterfaces interfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
            byte[] imageToByte = byteArrayOutputStream.toByteArray();
            String encodeImage = android.util.Base64.encodeToString(imageToByte, Base64.DEFAULT);
            Log.d("TAG", "insertData: "+id);
            Call<ResponseImage> call = interfaces.imagePost(encodeImage, "Pasien", String.valueOf(id));
            call.enqueue(new Callback<ResponseImage>() {
                @Override
                public void onResponse(Call<ResponseImage> call, Response<ResponseImage> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getContext(), "Berhasil mengirim gambar", Toast.LENGTH_SHORT).show();
                        Fragment fragment = new ChatFragment();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_layout, fragment)
                                .commit();
                    }else {
                        Toast.makeText(getContext(), "Gagal mengirim gambar", Toast.LENGTH_SHORT).show();
                        Fragment fragment = new OptionalChatFragment();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_layout, fragment)
                                .commit();
                    }
                }

                @Override
                public void onFailure(Call<ResponseImage> call, Throwable t) {
                    Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}