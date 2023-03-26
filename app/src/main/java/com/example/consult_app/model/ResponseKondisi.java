package com.example.consult_app.model;

import java.util.List;

public class ResponseKondisi {
    String success, message;
    List<KondisiModel> data;

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

    public List<KondisiModel> getData() {
        return data;
    }

    public void setData(List<KondisiModel> data) {
        this.data = data;
    }
}
