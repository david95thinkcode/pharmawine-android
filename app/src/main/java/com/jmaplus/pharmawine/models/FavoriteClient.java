package com.jmaplus.pharmawine.models;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class FavoriteClient extends RealmObject {

    public static final String MEDICAL = "corps_medical";
    public static final String PHARMACY = "pharmacy";

    @PrimaryKey
    private int id;
    @Required
    private String name;
    private String title;
    private String profilePicture;
    private String type;

    public FavoriteClient() {
    }

    public FavoriteClient(int id, String name, String title, String profilePicture, String type) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.profilePicture = profilePicture;
        this.type = type;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static void save(Realm realm, final FavoriteClient client) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {

                FavoriteClient favoriteClient = realm.createObject(FavoriteClient.class);

                favoriteClient.setId(client.getId());
                favoriteClient.setName(client.getName());
                favoriteClient.setTitle(client.getTitle());
                favoriteClient.setProfilePicture(client.getProfilePicture());
                favoriteClient.setType(client.getType());
            }
        });
    }

    public static void deleteAll(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm bgRealm) {
                RealmResults<FavoriteClient> favoriteClients = bgRealm.where(FavoriteClient.class).findAll();
                if (favoriteClients != null)
                    favoriteClients.deleteAllFromRealm();
            }
        });
    }

    public static void delById(Realm realm, final int id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm bgRealm) {
                FavoriteClient favoriteClient = bgRealm.where(FavoriteClient.class).equalTo("id", id).findFirst();
                if (favoriteClient != null)
                    favoriteClient.deleteFromRealm();
            }
        });
    }

    public static ArrayList<FavoriteClient> getAll(Realm realm) {
        RealmResults<FavoriteClient> results = realm.where(FavoriteClient.class).findAllAsync();
        if (results != null) results.load();

        ArrayList<FavoriteClient> favoriteClients = new ArrayList<>();
        favoriteClients.addAll(results);

        return favoriteClients;
    }

    public static FavoriteClient getById(Realm realm, int id) {
        FavoriteClient result = realm.where(FavoriteClient.class)
                .equalTo("id", id)
                .findFirst();
        if (result != null) result.load();

        return result;
    }
}
