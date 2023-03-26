package com.example.consult_app.model;

import java.util.List;

public class ResponseHistori {
    String success, message;
    List<HistoriModel> data;

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

    public List<HistoriModel> getData() {
        return data;
    }

    public void setData(List<HistoriModel> data) {
        this.data = data;
    }
}
