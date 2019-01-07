package com.jmaplus.pharmawine.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class AuthenticatedUser extends RealmObject {

    @SerializedName("id")
    private int id;
    @SerializedName("lastname")
    private String lastName;
    @SerializedName("name")
    private String name;
    @SerializedName("sex")
    private String sexe;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("marital_status")
    private String maritalStatus;
    @SerializedName("phone_o")
    private String phoneNumber;
    @SerializedName("phone")
    private String phoneNumber2;
    @SerializedName("email")
    private String email;
    @SerializedName("profile_picture")
    private String profilePicture;
    @SerializedName("register_on")
    private String registerOn;
    @SerializedName("nationality")
    private String nationality;
    @SerializedName("country_name")
    private String actualCountryName;
    @SerializedName("role_name")
    private String role;
    @SerializedName("network_id")
    private int networkId;
    @SerializedName("network_name")
    private String networkName;

//    Calculated based on attributes
    private int fillingLevel;

    public AuthenticatedUser(){}

    public AuthenticatedUser(int id, String lastName, String name, String sexe, String birthday, String maritalStatus, String phoneNumber, String phoneNumber2, String email, String profilePicture, String registerOn, String nationality, String actualCountryName, String role, int networkId, String networkName, int fillingLevel) {
        this.id = id;
        this.lastName = lastName;
        this.name = name;
        this.sexe = sexe;
        this.birthday = birthday;
        this.maritalStatus = maritalStatus;
        this.phoneNumber = phoneNumber;
        this.phoneNumber2 = phoneNumber2;
        this.email = email;
        this.profilePicture = profilePicture;
        this.registerOn = registerOn;
        this.nationality = nationality;
        this.actualCountryName = actualCountryName;
        this.role = role;
        this.networkId = networkId;
        this.networkName = networkName;
        this.fillingLevel = fillingLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getRegisterOn() {
        return registerOn;
    }

    public void setRegisterOn(String registerOn) {
        this.registerOn = registerOn;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getActualCountryName() {
        return actualCountryName;
    }

    public void setActualCountryName(String actualCountryName) {
        this.actualCountryName = actualCountryName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public int getFillingLevel() {
        return fillingLevel;
    }

    public void setFillingLevel(int fillingLevel) {
        this.fillingLevel = fillingLevel;
    }

    public static void saveUser(Realm realm, final AuthenticatedUser authenticatedUser) {
        deleteUser(realm);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                AuthenticatedUser user = realm.createObject(AuthenticatedUser.class);
                user.setId(authenticatedUser.getId());
                user.setLastName(authenticatedUser.getLastName());
                user.setName(authenticatedUser.getName());
                user.setSexe(authenticatedUser.getSexe());
                user.setBirthday(authenticatedUser.getBirthday());
                user.setMaritalStatus(authenticatedUser.getMaritalStatus());
                user.setPhoneNumber(authenticatedUser.getPhoneNumber());
                user.setPhoneNumber2(authenticatedUser.getPhoneNumber2());
                user.setEmail(authenticatedUser.getEmail());
                user.setProfilePicture(authenticatedUser.getProfilePicture());
                user.setRegisterOn(authenticatedUser.getRegisterOn());
                user.setNationality(authenticatedUser.getNationality());
                user.setActualCountryName(authenticatedUser.getActualCountryName());
                user.setRole(authenticatedUser.getRole());
                user.setNetworkId(authenticatedUser.getNetworkId());
                user.setNetworkName(authenticatedUser.getNetworkName());
                user.setFillingLevel(authenticatedUser.getFillingLevel());
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(@NonNull Throwable error) {
                error.printStackTrace();
            }
        });
    }

    public static void deleteUser(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm bgRealm) {
                RealmResults<AuthenticatedUser> authenticatedUsers = bgRealm.where(AuthenticatedUser.class).findAll();
                if (authenticatedUsers != null)
                    authenticatedUsers.deleteAllFromRealm();
            }
        });
    }


    public static AuthenticatedUser getAuthenticatedUser(Realm realm) {
        RealmResults<AuthenticatedUser> results = realm.where(AuthenticatedUser.class)
                .findAllAsync();
        if (results != null) results.load();

        return (results != null) ? results.first() : null;
    }

    private int fillingLevel() {
        return 20;
    }
}
