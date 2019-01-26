package com.jmaplus.pharmawine.models;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyReportEnd {

    @SerializedName("goal")
    @Expose
    private String goal;
    @SerializedName("promise")
    @Expose
    private String promise;
    @SerializedName("prescription")
    @Expose
    private String prescription;
    @SerializedName("center_id")
    @Expose
    private Integer centerId;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getPromise() {
        return promise;
    }

    public void setPromise(String promise) {
        this.promise = promise;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public Integer getCenterId() {
        return centerId;
    }

    public void setCenterId(Integer centerId) {
        this.centerId = centerId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Boolean isCompleted() {
//        if (getCenterId() != null && getCustomerId() != null && !getEndTime().isEmpty() && getEndTime() != null) {
        return true;
//        }
//        else {
//            return false;
//        }
    }

    @Override
    public String toString() {
        Gson g = new Gson();

        return g.toJson(this);
    }
}