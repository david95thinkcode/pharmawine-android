package com.jmaplus.pharmawine.models;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyReportStart {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("start_time")
    @Expose
    private String startTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

//    @androidx.annotation.NonNull
    @Override
    public String toString() {
        Gson g = new Gson();
        return g.toJson(this);
    }
}