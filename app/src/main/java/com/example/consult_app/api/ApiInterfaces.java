package com.example.consult_app.api;

import com.example.consult_app.model.ResponseAlarm;
import com.example.consult_app.model.ResponseBiodata;
import com.example.consult_app.model.ResponseChat;
import com.example.consult_app.model.ResponseCluster;
import com.example.consult_app.model.ResponseHistori;
import com.example.consult_app.model.ResponseImage;
import com.example.consult_app.model.ResponseKondisi;
import com.example.consult_app.model.ResponseKonsumsi;
import com.example.consult_app.model.ResponseLokasi;
import com.example.consult_app.model.ResponseNotifikasi;
import com.example.consult_app.model.ResponseObat;
import com.example.consult_app.model.ResponseUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterfaces {

    @FormUrlEncoded
    @POST("api/login")
    Call<ResponseUser> dataLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("api/logout")
    Call<ResponseUser> logout(
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("api/laporan-konsumsi/id")
    Call<ResponseKonsumsi> getKonsumsi(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("api/laporan-konsumsi")
    Call<ResponseKonsumsi> postKonsumsi(
            @Field("id") String id,
            @Field("konsumsi") String konsumsi,
            @Field("terlewati") String terlewati,
            @Field("periode") String periode
    );

    @FormUrlEncoded
    @POST("api/laporan-kondisi/id")
    Call<ResponseKondisi> getKondisi(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("api/laporan-kondisi")
    Call<ResponseKondisi> postKondisi(
            @Field("id") String id,
            @Field("berat") String berat,
            @Field("efek") String efek,
            @Field("keluhan") String keluhan
    );

    @FormUrlEncoded
    @POST("api/laporan-perjalanan/id")
    Call<ResponseLokasi> getLokasi(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("api/laporan-perjalanan")
    Call<ResponseLokasi> postLokasi(
            @Field("id") String id,
            @Field("tgl_kunjungan") String tgl_kunjungan,
            @Field("tgl_pulang") String tgl_pulang,
            @Field("tujuan") String tujuan,
            @Field("keterangan") String keterangan
    );

    @FormUrlEncoded
    @POST("api/ClusterController")
    Call<ResponseCluster> dataCluster(
        @Field("id") String id
    );

    @FormUrlEncoded
    @POST("api/ChatController")
    Call<ResponseChat> dataChat(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("api/LokasiController")
    Call<ResponseLokasi> insertLokasi(
            @Field("provinsi") String provinsi,
            @Field("kabupaten") String kabupaten,
            @Field("kecamatan") String kecamatan,
            @Field("desa") String desa,
            @Field("user_id") String user_id
            );

    @FormUrlEncoded
    @POST("api/ChatController/image")
    Call<ResponseImage> imagePost(
            @Field("image") String image,
            @Field("pengirim") String pengirim,
            @Field("id") String id
    );

    @GET("api/LokasiController")
    Call<ResponseLokasi> logGet(
            @Query("id") String id
    );

    @FormUrlEncoded
    @POST("api/chat/post-chat")
    Call<ResponseChat> postChat(
            @Field("id") String id,
            @Field("pesan") String pesan
    );

    @FormUrlEncoded
    @POST("api/chat/get-chat")
    Call<ResponseChat> getChat(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("api/histori")
    Call<ResponseHistori> getHistori(
        @Field("id") String id
    );

    @FormUrlEncoded
    @POST("api/get/alarm")
    Call<ResponseAlarm> getAlarm(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("api/get/user")
    Call<ResponseBiodata> getBiodata(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("api/get-notif")
    Call<ResponseNotifikasi> getNotifikasi(
        @Field("id") String id
    );

    @FormUrlEncoded
    @POST("api/get-nama-obat")
    Call<ResponseObat> getObat(
            @Field("id") String id
    );
}
