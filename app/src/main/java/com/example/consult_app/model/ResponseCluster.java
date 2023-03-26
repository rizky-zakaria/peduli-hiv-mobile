package com.example.consult_app.model;

import java.util.List;

public class ResponseCluster {

    private String status, message;
    private List<ClusterModel> data;

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

    public List<ClusterModel> getData() {
        return data;
    }

    public void setData(List<ClusterModel> data) {
        this.data = data;
    }
}
