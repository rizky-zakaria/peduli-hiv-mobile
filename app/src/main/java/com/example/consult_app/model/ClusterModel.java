package com.example.consult_app.model;

public class ClusterModel {
    private String id, id_pengirim, id_dokter, timestamp, nama_dokter;

    public String getNama_dokter() {
        return nama_dokter;
    }

    public void setNama_dokter(String nama_dokter) {
        this.nama_dokter = nama_dokter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_pengirim() {
        return id_pengirim;
    }

    public void setId_pengirim(String id_pengirim) {
        this.id_pengirim = id_pengirim;
    }

    public String getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(String id_dokter) {
        this.id_dokter = id_dokter;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
