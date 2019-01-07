package com.jmaplus.pharmawine.services.responses;

import com.google.gson.annotations.SerializedName;
import com.jmaplus.pharmawine.models.NetworkMember;

import java.util.ArrayList;

public class NetworkMembersResponse {

    @SerializedName("status_code")
    private int statusCode;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList<NetworkMember> networkMembers;

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

    public ArrayList<NetworkMember> getNetworkMembers() {
        return networkMembers;
    }

    public void setNetworkMembers(ArrayList<NetworkMember> networkMembers) {
        this.networkMembers = networkMembers;
    }
}
