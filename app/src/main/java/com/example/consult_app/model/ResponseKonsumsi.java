package com.example.consult_app.model;

import java.util.List;

public class ResponseKonsumsi {
    String success, message;
    List<KonsumsiModel> data;

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

    public List<KonsumsiModel> getData() {
        return data;
    }

    public void setData(List<KonsumsiModel> data) {
        this.data = data;
    }
}
