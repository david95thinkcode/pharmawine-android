package com.jmaplus.pharmawine.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class Bonus extends RealmObject{

    @SerializedName("id")
    private int id;
    @SerializedName("description")
    private String description;
    @SerializedName("value")
    private int value;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("register_on")
    private String registeredOn;

    public Bonus() {}

    public Bonus(int id, String description, int value, int userId, String registeredOn) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.userId = userId;
        this.registeredOn = registeredOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }

    public static void saveAll(Realm realm, final ArrayList<Bonus> bonuses) {
        deleteAll(realm);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {

                Bonus bonus = null;
                for (int i = 0; i < bonuses.size(); i++) {
                    bonus = realm.createObject(Bonus.class);

                    bonus.setId(bonuses.get(i).getId());
                    bonus.setDescription(bonuses.get(i).getDescription());
                    bonus.setValue(bonuses.get(i).getValue());
                    bonus.setUserId(bonuses.get(i).getUserId());
                    bonus.setRegisteredOn(bonuses.get(i).getRegisteredOn());
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
                RealmResults<Bonus> bonuses = bgRealm.where(Bonus.class).findAll();
                if (bonuses != null)
                    bonuses.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("Bonuses deleted", "ok");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(@NonNull Throwable error) {
                Log.e("Bonuses : DeleteError", error.toString());
            }
        });
    }

    public static ArrayList<Bonus> getAll(Realm realm) {
        RealmResults<Bonus> results = realm.where(Bonus.class)
                .findAllAsync();
        if (results != null) results.load();

        ArrayList<Bonus> bonuses = new ArrayList<>();
        bonuses.addAll(results);

        return bonuses;
    }

    public User getBeneficiary(Realm realm) {
        User result = realm.where(User.class)
                .equalTo("id", getUserId())
                .findFirst();

        if (result != null) result.load();

        return result;
    }

    public static Bonus getById(Realm realm, int id) {
        Bonus result = realm.where(Bonus.class)
                .equalTo("id", id)
                .findFirst();

        if (result != null) result.load();

        return result;
    }
}
