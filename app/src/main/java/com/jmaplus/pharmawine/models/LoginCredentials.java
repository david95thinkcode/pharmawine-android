package com.jmaplus.pharmawine.models;

public class LoginCredentials {

    //    @SerializedName("id")
    private String email;
    //    @SerializedName("name")
    private String password;

    public LoginCredentials(String email, String pwd) {
        this.email = email;
        this.password = pwd;
    }
}
