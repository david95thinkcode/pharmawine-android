package com.jmaplus.pharmawine.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class Gift extends RealmObject {

    public static final String PHARMACY = "pharmacie";
    public static final String MEDICAL_TEAM = "medical_team";
    public static final String HOSPITAL = "hospital";

    @SerializedName("id")
    private int id;
    @SerializedName("label")
    private String label;
    @SerializedName("value")
    private int value;
    @SerializedName("who_id")
    private int whoId;
    @SerializedName("who_type")
    private String donatorType;
    @SerializedName("register_on")
    private String registeredOn;

    public Gift() {}

    public Gift(int id, String label, int value, int whoId, String donatorType, String registeredOn) {
        this.id = id;
        this.label = label;
        this.value = value;
        this.whoId = whoId;
        this.donatorType = donatorType;
        this.registeredOn = registeredOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getWhoId() {
        return whoId;
    }

    public void setWhoId(int whoId) {
        this.whoId = whoId;
    }

    public String getDonatorType() {
        return donatorType;
    }

    public void setDonatorType(String donatorType) {
        this.donatorType = donatorType;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }

    public static void saveAll(Realm realm, final ArrayList<Gift> gifts) {
        deleteAll(realm);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {

                Gift gift = null;
                for (int i = 0; i < gifts.size(); i++) {
                    gift = realm.createObject(Gift.class);

                    gift.setId(gifts.get(i).getId());
                    gift.setLabel(gifts.get(i).getLabel());
                    gift.setValue(gifts.get(i).getValue());
                    gift.setWhoId(gifts.get(i).getWhoId());
                    gift.setDonatorType(gifts.get(i).getDonatorType());
                    gift.setRegisteredOn(gifts.get(i).getRegisteredOn());
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
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm bgRealm) {
                RealmResults<Gift> gifts = bgRealm.where(Gift.class).findAll();
                if (gifts != null)
                    gifts.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("Gifts deleted", "ok");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(@NonNull Throwable error) {
                Log.e("Gifts : DeleteError", error.toString());
            }
        });
    }

    public static ArrayList<Gift> getAll(Realm realm) {
        RealmResults<Gift> results = realm.where(Gift.class)
                .findAllAsync();
        if (results != null) results.load();

        ArrayList<Gift> gifts = new ArrayList<>();
        gifts.addAll(results);

        return gifts;
    }

    public static Gift getById(Realm realm, int id) {
        Gift result = realm.where(Gift.class)
                .equalTo("id", id)
                .findFirst();

        if (result != null) result.load();

        return result;
    }
}
