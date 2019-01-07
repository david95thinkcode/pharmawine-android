package com.jmaplus.pharmawine.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class Wholesaler extends RealmObject {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("logo")
    private String logo;

    public Wholesaler() {}

    public Wholesaler(int id, String name, String logo) {
        this.id = id;
        this.name = name;
        this.logo = logo;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public static void saveAll(Realm realm, final ArrayList<Wholesaler> wholesalers, @Nullable Realm.Transaction.OnSuccess onSuccessCallBack) {
        deleteAll(realm);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {

                Wholesaler wholesaler = null;
                for (int i = 0; i < wholesalers.size(); i++) {
                    wholesaler = realm.createObject(Wholesaler.class);

                    wholesaler.setId(wholesalers.get(i).getId());
                    wholesaler.setName(wholesalers.get(i).getName());
                    wholesaler.setLogo(wholesalers.get(i).getLogo());
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
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm bgRealm) {
                RealmResults<Wholesaler> wholesalers = bgRealm.where(Wholesaler.class).findAll();
                if (wholesalers != null)
                    wholesalers.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("Wholesalers deleted", "ok");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(@NonNull Throwable error) {
                Log.e("WholeSale : DeleteError", error.toString());
            }
        });
    }

    public static Wholesaler getWholesaler(Realm realm, int id) {
        Wholesaler result = realm.where(Wholesaler.class)
                .equalTo("id", id)
                .findFirst();

        if (result != null) result.load();

        return result;
    }

    public static ArrayList<Wholesaler> getAll(Realm realm) {
        RealmResults<Wholesaler> results = realm.where(Wholesaler.class)
                .findAllAsync();
        if (results != null) results.load();

        ArrayList<Wholesaler> wholesalers = new ArrayList<>();
        wholesalers.addAll(results);

        return wholesalers;
    }
}
