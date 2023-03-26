package com.example.consult_app.model;

public class LocationModel {
    private String id, tgl_kunjungan, tgl_pulang, tujuan, keterangan;

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTgl_kunjungan() {
        return tgl_kunjungan;
    }

    public void setTgl_kunjungan(String tgl_kunjungan) {
        this.tgl_kunjungan = tgl_kunjungan;
    }

    public String getTgl_pulang() {
        return tgl_pulang;
    }

    public void setTgl_pulang(String tgl_pulang) {
        this.tgl_pulang = tgl_pulang;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }
}
