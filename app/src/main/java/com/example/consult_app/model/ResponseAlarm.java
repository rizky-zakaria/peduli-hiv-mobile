package com.example.consult_app.model;

public class ResponseAlarm {
    String success, message;
    AlarmModel data;

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

    public AlarmModel getData() {
        return data;
    }

    public void setData(AlarmModel data) {
        this.data = data;
    }
}
