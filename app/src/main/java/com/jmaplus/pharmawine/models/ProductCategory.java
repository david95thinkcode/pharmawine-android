package com.jmaplus.pharmawine.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class ProductCategory extends RealmObject {

    @SerializedName("id")
    private int id;
    @SerializedName("label")
    private String label;

    public ProductCategory() {
    }

    public ProductCategory(int id, String label) {
        this.id = id;
        this.label = label;
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

    public static void saveAll(Realm realm, final ArrayList<ProductCategory> productCategories) {
        deleteAll(realm);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {

                for (int i = 0; i < productCategories.size(); i++) {
                    ProductCategory productCategory = realm.createObject(ProductCategory.class);

                    productCategory.setId(productCategories.get(i).getId());
                    productCategory.setLabel(productCategories.get(i).getLabel());
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
                RealmResults<ProductCategory> productCategories = bgRealm.where(ProductCategory.class).findAll();
                if (productCategories != null)
                    productCategories.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("ProdCategories deleted", "ok");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(@NonNull Throwable error) {
                Log.e("ProdCategs : DelError", error.toString());
            }
        });
    }

    public static ArrayList<ProductCategory> getAll(Realm realm) {
        RealmResults<ProductCategory> results = realm.where(ProductCategory.class)
                .findAllAsync();
        if (results != null) results.load();

        ArrayList<ProductCategory> productCategories = new ArrayList<>();
        productCategories.addAll(results);

        return productCategories;
    }
}
