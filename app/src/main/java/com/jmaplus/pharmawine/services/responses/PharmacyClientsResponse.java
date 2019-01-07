package com.jmaplus.pharmawine.services.responses;

import com.google.gson.annotations.SerializedName;
import com.jmaplus.pharmawine.models.Pharmacy;

import java.util.ArrayList;

public class PharmacyClientsResponse {

    @SerializedName("status_code")
    private int statusCode;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList<Pharmacy> pharmacies;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Pharmacy> getPharmacies() {
        return pharmacies;
    }

    public void setPharmacies(ArrayList<Pharmacy> pharmacies) {
        this.pharmacies = pharmacies;
    }
}
