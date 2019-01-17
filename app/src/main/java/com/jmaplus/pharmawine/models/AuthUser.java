package com.jmaplus.pharmawine.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jmaplus.pharmawine.utils.Constants;

import java.util.List;

public class AuthUser {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("telephone1")
    @Expose
    private String telephone1;
    @SerializedName("telephone2")
    @Expose
    private String telephone2;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("nationalite")
    @Expose
    private String nationalite;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("email_verified_at")
    @Expose
    private Object emailVerifiedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("type_id")
    @Expose
    private Integer typeId;
    @SerializedName("network_id")
    @Expose
    private Object networkId;
    @SerializedName("areas")
    @Expose
    private List<Object> areas = null;
    @SerializedName("types")
    @Expose
    private Object types;
    @SerializedName("products")
    @Expose
    private List<Object> products = null;
    @SerializedName("networks")
    @Expose
    private Object networks;
    @SerializedName("goals")
    @Expose
    private List<Object> goals = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Object getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Object networkId) {
        this.networkId = networkId;
    }

    public List<Object> getAreas() {
        return areas;
    }

    public void setAreas(List<Object> areas) {
        this.areas = areas;
    }

    public Object getTypes() {
        return types;
    }

    public void setTypes(Object types) {
        this.types = types;
    }

    public List<Object> getProducts() {
        return products;
    }

    public void setProducts(List<Object> products) {
        this.products = products;
    }

    public Object getNetworks() {
        return networks;
    }

    public void setNetworks(Object networks) {
        this.networks = networks;
    }

    public List<Object> getGoals() {
        return goals;
    }

    public void setGoals(List<Object> goals) {
        this.goals = goals;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }

    /**
     * Write user data into shared Preferences
     *
     * @param context
     * @return
     */
    public Boolean storeInSharedPreferences(Context context) {

        Integer DEFAULT_DELEGUE_ROLE_ID = 3;
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    Constants.F_PROFIL, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putInt(Constants.SP_ID_KEY, this.id);
            editor.putString(Constants.SP_FIRSTNAME_KEY, this.firstname);
            editor.putString(Constants.SP_LASTNAME_KEY, this.lastname);
            editor.putString(Constants.SP_AVATAR_URL_KEY, this.avatar);
            editor.putString(Constants.SP_EMAIL_KEY, this.email);
            editor.putString(Constants.SP_BIRTHDAY_KEY, this.birthday);
            editor.putString(Constants.SP_SEX_KEY, this.sex);
            editor.putString(Constants.SP_NATIONALITY_KEY, this.nationalite);
            editor.putString(Constants.SP_PHONE_1_KEY, this.telephone1);
            editor.putString(Constants.SP_PHONE_2_KEY, this.telephone2);
            editor.putString(Constants.SP_MARITAL_STATUS_KEY, this.maritalStatus);
            editor.putInt(Constants.SP_TYPE_KEY, this.typeId);

            // Un compte de user actif a pour status = 0 sinon 1
            editor.putInt(Constants.SP_ACCOUNT_STATUS_KEY, this.status);

            // TODO : replace the value by the real object role when the api will be updated
            editor.putInt(Constants.SP_ROLE_KEY, DEFAULT_DELEGUE_ROLE_ID);

            editor.commit();

        } catch (Exception e) {
            return false;
        }

        return true;
    }


}