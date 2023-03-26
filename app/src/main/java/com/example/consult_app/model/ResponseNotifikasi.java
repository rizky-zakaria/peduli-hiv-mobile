package com.example.consult_app.model;

import java.util.List;

public class ResponseNotifikasi {
    String success, message;
    List<NotifikasiModel> data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<NotifikasiModel> getData() {
        return data;
    }

    public void setData(List<NotifikasiModel> data) {
        this.data = data;
    }
}
