package com.example.consult_app.model;

public class ResponseBiodata {
    String success, message;
    BiodataModel data;

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

    public BiodataModel getData() {
        return data;
    }

    public void setData(BiodataModel data) {
        this.data = data;
    }
}
