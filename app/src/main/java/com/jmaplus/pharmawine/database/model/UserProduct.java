package com.jmaplus.pharmawine.database.model;

public class UserProduct {


    private long id;
    private String product;


    public UserProduct() {

    }

    public UserProduct(long id, String product) {
        this.id = id;
        this.product = product;
    }

    public long getId() {
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
