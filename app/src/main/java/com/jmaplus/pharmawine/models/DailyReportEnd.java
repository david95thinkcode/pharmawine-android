package com.jmaplus.pharmawine.models;

import android.util.Log;

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
        if (getCenterId() == null) {
            Log.i(getClass().getName(), "CenterId missed");
            return false;
        } else if (getCustomerId() == null) {
            Log.i(getClass().getName(), "CustomerId missed");
            return false;
        } else if (getEndTime() == null || getEndTime().isEmpty()) {
            Log.i(getClass().getName(), "EndTime missed");
            return false;
        } else if (getGoal() == null || getGoal().isEmpty()) {
            Log.i(getClass().getName(), "Goal missed");
            return false;
        } else if (getPromise() == null || getPromise().isEmpty()) {
            Log.i(getClass().getName(), "Promise missed");
            return false;
        } else if (getPrescription() == null || getPrescription().isEmpty()) {
            Log.i(getClass().getName(), "Prescription missed");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        Gson g = new Gson();

        return g.toJson(this);
    }
}