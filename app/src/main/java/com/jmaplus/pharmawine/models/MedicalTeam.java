package com.jmaplus.pharmawine.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class MedicalTeam extends RealmObject {

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
    @SerializedName("phone")
    private String phoneNumber2;
    @SerializedName("phone_o")
    private String phoneNumber;
    @SerializedName("address")
    private String address;
    @SerializedName("email")
    private String email;
    @SerializedName("profile_picture")
    private String profilePicture;
    @SerializedName("register_on")
    private String registerOn;
    @SerializedName("nationality")
    private String nationality;
    @SerializedName("speciality")
    private String category;

    public MedicalTeam(){}

    public MedicalTeam(int id, String lastName, String name, String sexe, String birthday, String maritalStatus, String phoneNumber2, String phoneNumber, String address, String email, String profilePicture, String registerOn, String nationality, String category) {
        this.id = id;
        this.lastName = lastName;
        this.name = name;
        this.sexe = sexe;
        this.birthday = birthday;
        this.maritalStatus = maritalStatus;
        this.phoneNumber2 = phoneNumber2;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.profilePicture = profilePicture;
        this.registerOn = registerOn;
        this.nationality = nationality;
        this.category = category;
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

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static void saveAll(Realm realm, final ArrayList<MedicalTeam> medicalTeams, @Nullable Realm.Transaction.OnSuccess onSuccessCallBack) {
        deleteAll(realm);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {


                for (int i = 0; i < medicalTeams.size(); i++) {
                    MedicalTeam medicalTeam = realm.createObject(MedicalTeam.class);

                    medicalTeam.setId(medicalTeams.get(i).getId());
                    medicalTeam.setLastName(medicalTeams.get(i).getLastName());
                    medicalTeam.setName(medicalTeams.get(i).getName());
                    medicalTeam.setSexe(medicalTeams.get(i).getSexe());
                    medicalTeam.setBirthday(medicalTeams.get(i).getBirthday());
                    medicalTeam.setMaritalStatus(medicalTeams.get(i).getMaritalStatus());
                    medicalTeam.setPhoneNumber(medicalTeams.get(i).getPhoneNumber());
                    medicalTeam.setPhoneNumber2(medicalTeams.get(i).getPhoneNumber2());
                    medicalTeam.setAddress(medicalTeams.get(i).getAddress());
                    medicalTeam.setEmail(medicalTeams.get(i).getEmail());
                    medicalTeam.setProfilePicture(medicalTeams.get(i).getProfilePicture());
                    medicalTeam.setRegisterOn(medicalTeams.get(i).getRegisterOn());
                    medicalTeam.setNationality(medicalTeams.get(i).getNationality());
                    medicalTeam.setCategory(medicalTeams.get(i).getCategory());
                }
            }
        });

        /*realm.beginTransaction();
        for (int i = 0; i < medicalTeams.size(); i++) {

            realm.copyToRealm(medicalTeams.get(i));

        }
        realm.commitTransaction();*/
    }

    public static void deleteAll(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm bgRealm) {
                RealmResults<MedicalTeam> medicalTeams = bgRealm.where(MedicalTeam.class).findAll();
                if (medicalTeams != null)
                    medicalTeams.deleteAllFromRealm();
            }
        });
    }

    public static ArrayList<MedicalTeam> getAll(Realm realm) {
        RealmResults<MedicalTeam> results = realm.where(MedicalTeam.class)
                .findAllAsync();
        if (results != null) results.load();

        ArrayList<MedicalTeam> medicalTeams = new ArrayList<>();
        if (results != null) medicalTeams.addAll(results);

        return medicalTeams;
    }

    public static MedicalTeam getById(Realm realm, int id) {

        return realm.where(MedicalTeam.class)
                .equalTo("id", id)
                .findFirst();
    }

    public int getFillingLevel() {
        int totalFieldNumber = 12;
        int filledFieldNumber = 0;

        if(this.lastName != null) {
            filledFieldNumber++;
        }
        if(this.name != null) {
            filledFieldNumber++;
        }
        if(this.sexe != null) {
            filledFieldNumber++;
        }
        if(this.birthday != null) {
            filledFieldNumber++;
        }
        if(this.maritalStatus != null) {
            filledFieldNumber++;
        }
        if(this.phoneNumber != null) {
            filledFieldNumber++;
        }
        if(this.phoneNumber2 != null) {
            filledFieldNumber++;
        }
        if(this.address != null) {
            filledFieldNumber++;
        }
        if(this.email != null) {
            filledFieldNumber++;
        }
        if(this.profilePicture != null) {
            filledFieldNumber++;
        }
        if(this.nationality != null) {
            filledFieldNumber++;
        }
        if(this.category != null && !this.category.isEmpty()) {
            filledFieldNumber++;
        }

        return (filledFieldNumber/totalFieldNumber)*100;
    }
}
