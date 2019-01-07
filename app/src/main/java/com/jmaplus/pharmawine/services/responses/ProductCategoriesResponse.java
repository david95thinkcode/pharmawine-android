package com.jmaplus.pharmawine.services.responses;

import com.google.gson.annotations.SerializedName;
import com.jmaplus.pharmawine.models.ProductCategory;

import java.util.ArrayList;

public class ProductCategoriesResponse {

    @SerializedName("status_code")
    private int statusCode;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList<ProductCategory> productCategories;

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

    public ArrayList<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProducts(ArrayList<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }
}
