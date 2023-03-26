package com.example.consult_app.model;

public class ChatModel {
    private String id_cluster;
    private String pengirim;
    private String pesan;
    private String waktu;
    private String tanggal;

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    private String tipe;

    public String getId_cluster() {
        return id_cluster;
    }

    public void setId_cluster(String id_cluster) {
        this.id_cluster = id_cluster;
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
