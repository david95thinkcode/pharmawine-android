package com.jmaplus.pharmawine.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.jmaplus.pharmawine.PharmaWine;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class NetworkMember extends RealmObject {

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
    @SerializedName("network_name")
    private String networkName;
    @SerializedName("isAdmin")
    private int isAdmin;

    private int dailyGoalLevel;

    public NetworkMember() {
    }

    public NetworkMember(int id, String lastName, String name, String sexe, String birthday, String maritalStatus, String phoneNumber, String phoneNumber2, String email, String profilePicture, String registerOn, String networkName, int isAdmin, int dailyGoalLevel) {
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
        this.networkName = networkName;
        this.isAdmin = isAdmin;
        this.dailyGoalLevel = PharmaWine.mFaker.number.between(10, 100);
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

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getDailyGoalLevel() {
        return PharmaWine.mFaker.number.between(10, 100);
    }

    public void setDailyGoalLevel(int dailyGoalLevel) {
        this.dailyGoalLevel = dailyGoalLevel;
    }

    public static void saveAll(Realm realm, final ArrayList<NetworkMember> networkMembers) {
        deleteAll(realm);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {


                for (int i = 0; i < networkMembers.size(); i++) {
                    NetworkMember networkMember = realm.createObject(NetworkMember.class);

                    networkMember.setId(networkMembers.get(i).getId());
                    networkMember.setLastName(networkMembers.get(i).getLastName());
                    networkMember.setName(networkMembers.get(i).getName());
                    networkMember.setSexe(networkMembers.get(i).getSexe());
                    networkMember.setBirthday(networkMembers.get(i).getBirthday());
                    networkMember.setMaritalStatus(networkMembers.get(i).getMaritalStatus());
                    networkMember.setPhoneNumber(networkMembers.get(i).getPhoneNumber());
                    networkMember.setPhoneNumber2(networkMembers.get(i).getPhoneNumber2());
                    networkMember.setEmail(networkMembers.get(i).getEmail());
                    networkMember.setProfilePicture(networkMembers.get(i).getProfilePicture());
                    networkMember.setRegisterOn(networkMembers.get(i).getRegisterOn());
                    networkMember.setNetworkName(networkMembers.get(i).getNetworkName());
                    networkMember.setIsAdmin(networkMembers.get(i).getIsAdmin());
                    networkMember.setDailyGoalLevel(PharmaWine.mFaker.number.between(10, 100));
                }
            }
        });
    }

    public static void deleteAll(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm bgRealm) {
                RealmResults<NetworkMember> networkMembers = bgRealm.where(NetworkMember.class).findAll();
                if (networkMembers != null)
                    networkMembers.deleteAllFromRealm();
            }
        });
    }

    public static ArrayList<NetworkMember> getAll(Realm realm) {
        RealmResults<NetworkMember> results = realm.where(NetworkMember.class)
                .findAllAsync();
        if (results != null) results.load();

        ArrayList<NetworkMember> networkMembers = new ArrayList<>();
        if (results != null) networkMembers.addAll(results);

        return networkMembers;
    }

    public static NetworkMember getById(Realm realm, int id) {

        return realm.where(NetworkMember.class)
                .equalTo("id", id)
                .findFirst();
    }

    public static NetworkMember getSupervisor(Realm realm) {

        NetworkMember result = realm.where(NetworkMember.class)
                .equalTo("isAdmin", 1)
                .findFirst();

        if(result != null) result.load();
        return result;
    }
}
