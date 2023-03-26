package com.example.consult_app.model;

import java.util.List;

public class ResponseLokasi {
    private String status, message;
    private List<LocationModel> data;

    public List<LocationModel> getData() {
        return data;
    }

    public void setData(List<LocationModel> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
