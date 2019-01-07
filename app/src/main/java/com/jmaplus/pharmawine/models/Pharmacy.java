package com.jmaplus.pharmawine.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class Pharmacy extends RealmObject{

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("representative")
    private String representative;
    @SerializedName("founder")
    private String founder;
    @SerializedName("created_on")
    private String createdOn;
    @SerializedName("employees")
    private int employees;
    @SerializedName("annex")
    private int annexe;
    @SerializedName("address")
    private String address;
    @SerializedName("contact")
    private String contact;

    public Pharmacy(){}

    public Pharmacy(int id, String name, String representative, String founder, String createdOn, int employees, int annexe, String address, String contact) {
        this.id = id;
        this.name = name;
        this.representative = representative;
        this.founder = founder;
        this.createdOn = createdOn;
        this.employees = employees;
        this.annexe = annexe;
        this.address = address;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public int getEmployees() {
        return employees;
    }

    public void setEmployees(int employees) {
        this.employees = employees;
    }

    public int getAnnexe() {
        return annexe;
    }

    public void setAnnexe(int annexe) {
        this.annexe = annexe;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public static void saveAll(Realm realm, final ArrayList<Pharmacy> pharmacies) {
        deleteAll(realm);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {

                Pharmacy pharmacy = null;
                for (int i = 0; i < pharmacies.size(); i++) {
                    pharmacy = realm.createObject(Pharmacy.class);

                    pharmacy.setId(pharmacies.get(i).getId());
                    pharmacy.setName(pharmacies.get(i).getName());
                    pharmacy.setRepresentative(pharmacies.get(i).getRepresentative());
                    pharmacy.setFounder(pharmacies.get(i).getFounder());
                    pharmacy.setCreatedOn(pharmacies.get(i).getCreatedOn());
                    pharmacy.setEmployees(pharmacies.get(i).getEmployees());
                    pharmacy.setAnnexe(pharmacies.get(i).getAnnexe());
                    pharmacy.setAddress(pharmacies.get(i).getAddress());
                    pharmacy.setContact(pharmacies.get(i).getContact());
                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(@NonNull Throwable error) {
                error.printStackTrace();
            }
        });
    }

    public static void deleteAll(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm bgRealm) {
                RealmResults<Pharmacy> pharmacies = bgRealm.where(Pharmacy.class).findAll();
                if (pharmacies != null)
                    pharmacies.deleteAllFromRealm();
            }
        });
    }

    public static ArrayList<Pharmacy> getAll(Realm realm) {
        RealmResults<Pharmacy> results = realm.where(Pharmacy.class)
                .findAllAsync();
        if (results != null) results.load();

        ArrayList<Pharmacy> pharmacies = new ArrayList<>();
        pharmacies.addAll(results);

        return pharmacies;
    }

    public static Pharmacy getById(Realm realm, int id) {
        Pharmacy result = realm.where(Pharmacy.class)
                .equalTo("id", id)
                .findFirst();

        if (result != null) result.load();

        return result;
    }

    public int getFillingLevel() {
        int totalFieldNumber = 6;
        int filledFieldNumber = 0;

        if(this.name != null) {
            filledFieldNumber++;
        }
        if(this.representative != null) {
            filledFieldNumber++;
        }
        if(this.founder != null) {
            filledFieldNumber++;
        }
        if(this.employees > 0) {
            filledFieldNumber++;
        }
        if(this.address != null) {
            filledFieldNumber++;
        }
        if(this.contact != null) {
            filledFieldNumber++;
        }

        return (filledFieldNumber/totalFieldNumber)*100;
    }
}
