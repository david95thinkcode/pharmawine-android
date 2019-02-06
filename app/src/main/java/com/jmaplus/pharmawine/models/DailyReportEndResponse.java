package com.jmaplus.pharmawine.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyReportEndResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("goal")
    @Expose
    private String goal;
    @SerializedName("promise")
    @Expose
    private String promise;
    @SerializedName("prescription")
    @Expose
    private String prescription;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("center_id")
    @Expose
    private Integer centerId;
    @SerializedName("network_id")
    @Expose
    private Object networkId;
    @SerializedName("hebdo_delegate_id")
    @Expose
    private Object hebdoDelegateId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("viewed")
    @Expose
    private Integer viewed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCenterId() {
        return centerId;
    }

    public void setCenterId(Integer centerId) {
        this.centerId = centerId;
    }

    public Object getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Object networkId) {
        this.networkId = networkId;
    }

    public Object getHebdoDelegateId() {
        return hebdoDelegateId;
    }

    public void setHebdoDelegateId(Object hebdoDelegateId) {
        this.hebdoDelegateId = hebdoDelegateId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getViewed() {
        return viewed;
    }

    public void setViewed(Integer viewed) {
        this.viewed = viewed;
    }

}