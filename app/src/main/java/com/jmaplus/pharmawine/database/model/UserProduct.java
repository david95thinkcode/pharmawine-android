package com.jmaplus.pharmawine.database.model;

public class UserProduct {


    private int id;
    private String product;


    public UserProduct() {

    }

    public UserProduct(int id, String product) {
        this.id = id;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
