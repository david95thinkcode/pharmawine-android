package com.jmaplus.pharmawine.services.responses;

import com.google.gson.annotations.SerializedName;
import com.jmaplus.pharmawine.models.MedicalTeam;

import java.util.ArrayList;

public class MedicalClientsResponse {

    @SerializedName("status_code")
    private int statusCode;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList<MedicalTeam> medicalTeams;

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

    public ArrayList<MedicalTeam> getMedicalTeams() {
        return medicalTeams;
    }

    public void setMedicalTeams(ArrayList<MedicalTeam> medicalTeams) {
        this.medicalTeams = medicalTeams;
    }
}
