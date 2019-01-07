package com.jmaplus.pharmawine.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class LabAssistance extends RealmObject {

    @SerializedName("id")
    private int id;
    @SerializedName("username")
    private String username;
    @SerializedName("contact")
    private String contact;
    @SerializedName("laboratory_id")
    private int laboratoryId;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public LabAssistance(){}

    public LabAssistance(int id, String username, String contact, int laboratoryId, String createdAt, String updatedAt) {
        this.id = id;
        this.username = username;
        this.contact = contact;
        this.laboratoryId = laboratoryId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(int laboratoryId) {
        this.laboratoryId = laboratoryId;
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
}
