package com.jmaplus.pharmawine.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class Laboratory extends RealmObject {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("representative")
    private String representative;
    @SerializedName("address")
    private String address;
    @SerializedName("contact")
    private String contact;
    @SerializedName("logo")
    private String logo;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;


    public Laboratory() {
    }

    public Laboratory(int id, String name, String representative, String address, String contact, String logo, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.representative = representative;
        this.address = address;
        this.contact = contact;
        this.logo = logo;
        this.createdAt = created_at;
        this.updatedAt = updated_at;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public static void saveAll(Realm realm, final ArrayList<Laboratory> laboratories, @Nullable Realm.Transaction.OnSuccess onSuccessCallBack) {
        deleteAll(realm);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {

                Laboratory laboratory = null;
                for (int i = 0; i < laboratories.size(); i++) {
                    laboratory = realm.createObject(Laboratory.class);

                    laboratory.setId(laboratories.get(i).getId());
                    laboratory.setName(laboratories.get(i).getName());
                    laboratory.setRepresentative(laboratories.get(i).getRepresentative());
                    laboratory.setAddress(laboratories.get(i).getAddress());
                    laboratory.setContact(laboratories.get(i).getContact());
                    laboratory.setLogo(laboratories.get(i).getLogo());
                    laboratory.setCreatedAt(laboratories.get(i).getCreatedAt());
                    laboratory.setUpdatedAt(laboratories.get(i).getUpdatedAt());
                }
            }
        }, onSuccessCallBack, new Realm.Transaction.OnError() {
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
                RealmResults<Laboratory> laboratories = bgRealm.where(Laboratory.class).findAll();
                if (laboratories != null)
                    laboratories.deleteAllFromRealm();
            }
        });
    }

    public static Laboratory getLaboratory(Realm realm, int id) {
        Laboratory result = realm.where(Laboratory.class)
                .equalTo("id", id)
                .findFirst();

        if (result != null) result.load();

        return result;
    }

    public static ArrayList<Laboratory> getAll(Realm realm) {
        RealmResults<Laboratory> results = realm.where(Laboratory.class)
                .findAllAsync();
        if (results != null) results.load();

        ArrayList<Laboratory> laboratories = new ArrayList<>();
        laboratories.addAll(results);

        return laboratories;
    }
}
