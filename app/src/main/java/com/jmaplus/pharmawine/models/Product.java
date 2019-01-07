package com.jmaplus.pharmawine.models;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class Product extends RealmObject {

    @SerializedName("id")
    private int id;
    @SerializedName("reference")
    private String reference;
    @SerializedName("product_name")
    private String name;
    @SerializedName("price")
    private int price;
    @SerializedName("price_p")
    private int priceP;
    @SerializedName("price_m")
    private int priceM;
    @SerializedName("laboratory")
    private String laboratory;
    @SerializedName("category")
    private String category;

    public Product() {
    }

    public Product(int id, String reference, String name, int price, int priceP, int priceM, String laboratory, String category) {
        this.id = id;
        this.reference = reference;
        this.name = name;
        this.price = price;
        this.priceP = priceP;
        this.priceM = priceM;
        this.laboratory = laboratory;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceP() {
        return priceP;
    }

    public void setPriceP(int priceP) {
        this.priceP = priceP;
    }

    public int getPriceM() {
        return priceM;
    }

    public void setPriceM(int priceM) {
        this.priceM = priceM;
    }

    public String getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static void saveAll(Realm realm, final ArrayList<Product> products) {
        deleteAll(realm);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {

                for (int i = 0; i < products.size(); i++) {
                    Product product = realm.createObject(Product.class);

                    product.setId(products.get(i).getId());
                    product.setReference(products.get(i).getReference());
                    product.setName(products.get(i).getName());
                    product.setPrice(products.get(i).getPrice());
                    product.setPriceP(products.get(i).getPriceP());
                    product.setPriceM(products.get(i).getPriceM());
                    product.setLaboratory(products.get(i).getLaboratory());
                    product.setCategory(products.get(i).getCategory());
                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(@NonNull Throwable error) {
                error.printStackTrace();
            }
        });
    }

    private static void deleteAll(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm bgRealm) {
                RealmResults<Product> products = bgRealm.where(Product.class).findAll();
                if (products != null)
                    products.deleteAllFromRealm();
            }
        });
    }

    public static ArrayList<Product> getAll(Realm realm) {
        RealmResults<Product> results = realm.where(Product.class)
                .findAllAsync();
        if (results != null) results.load();

        ArrayList<Product> products = new ArrayList<>();
        products.addAll(results);

        return products;
    }

    public static ArrayList<Product> getAllByCategory(Realm  realm, String category) {
        RealmResults<Product> results = realm.where(Product.class)
                .equalTo("category", category)
                .findAllAsync();
        if (results != null) results.load();

        ArrayList<Product> products = new ArrayList<>();
        products.addAll(results);

        return products;
    }
}
