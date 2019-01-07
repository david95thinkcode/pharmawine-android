package com.jmaplus.pharmawine.services.responses;

import com.google.gson.annotations.SerializedName;
import com.jmaplus.pharmawine.models.Product;

import java.util.ArrayList;

public class ProductsResponse {

    @SerializedName("status_code")
    private int statusCode;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList<Product> products;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
