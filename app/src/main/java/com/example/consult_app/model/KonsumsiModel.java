package com.example.consult_app.model;

public class KonsumsiModel {
    String id;
    String konsumsi;
    String terlewati;
    String periode;

    public String getKepatuhan() {
        return kepatuhan;
    }

    public void setKepatuhan(String kepatuhan) {
        this.kepatuhan = kepatuhan;
    }

    String kepatuhan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKonsumsi() {
        return konsumsi;
    }

    public void setKonsumsi(String konsumsi) {
        this.konsumsi = konsumsi;
    }

    public String getTerlewati() {
        return terlewati;
    }

    public void setTerlewati(String terlewati) {
        this.terlewati = terlewati;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }
}
