package com.example.consult_app.model;

import java.util.List;

public class ResponseDetailKonsumsi {
    String success, message;
    DetailKonsumsiModel data;

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

    public DetailKonsumsiModel getData() {
        return data;
    }

    public void setData(DetailKonsumsiModel data) {
        this.data = data;
    }
}
