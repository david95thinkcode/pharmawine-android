package com.jmaplus.pharmawine.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class User extends RealmObject {

    public static final String MEDICAL_SUPERVISOR = "MS";
    public static final String MEDICAL_DELEGATE = "MD";
    public static final String PHARMACEUTICAL_SUPERVISOR = "PS";
    public static final String PHARMACEUTICAL_DELEGATE = "PD";
    public static final String ADMINISTRATOR = "ADMIN";

    @SerializedName("id")
    private int id;
    @SerializedName("lastname")
    private String lastName;
    @SerializedName("name")
    private String name;
    @SerializedName("profile_picture")
    private String profilePicture;
    @SerializedName("role")
    private String role;
    @SerializedName("filling_level")
    private int fillingLevel;
    @SerializedName("register_on")
    private String registerOn;

    public User(){}

    public User(int id, String lastName, String name, String profilePicture, String role, int fillingLevel, String registerOn) {
        this.id = id;
        this.lastName = lastName;
        this.name = name;
        this.profilePicture = profilePicture;
        this.role = role;
        this.fillingLevel = fillingLevel;
        this.registerOn = registerOn;
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getFillingLevel() {
        return fillingLevel;
    }

    public void setFillingLevel(int fillingLevel) {
        this.fillingLevel = fillingLevel;
    }

    public String getRegisterOn() {
        return registerOn;
    }

    public void setRegisterOn(String registerOn) {
        this.registerOn = registerOn;
    }

    public static void saveAll(Realm realm, final ArrayList<User> users) {
        deleteAll(realm);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {

                User user = null;
                for (int i = 0; i < users.size(); i++) {
                    user = realm.createObject(User.class);

                    user.setId(users.get(i).getId());
                    user.setLastName(users.get(i).getLastName());
                    user.setName(users.get(i).getName());
                    user.setProfilePicture(users.get(i).getProfilePicture());
                    user.setRole(users.get(i).getRole());
                    user.setFillingLevel(users.get(i).getFillingLevel());
                    user.setRegisterOn(users.get(i).getRegisterOn());
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
                RealmResults<User> users = bgRealm.where(User.class).findAll();
                if (users != null)
                    users.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("Users deleted", "ok");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(@NonNull Throwable error) {
                Log.e("Users : DeleteError", error.toString());
            }
        });
    }

    public static ArrayList<User> getAll(Realm realm) {
        RealmResults<User> results = realm.where(User.class)
                .findAllAsync();
        if (results != null) results.load();

        ArrayList<User> users = new ArrayList<>();
        users.addAll(results);

        return users;
    }

    public static User getById(Realm realm, int id) {
        User result = realm.where(User.class)
                .equalTo("id", id)
                .findFirst();

        if (result != null) result.load();

        return result;
    }
}
