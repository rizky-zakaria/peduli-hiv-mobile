package com.example.consult_app.model;

import java.util.List;

public class ResponseObat {
    String success, message;
    List<ObatModel> data;

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

    public List<ObatModel> getData() {
        return data;
    }

    public void setData(List<ObatModel> data) {
        this.data = data;
    }
}
