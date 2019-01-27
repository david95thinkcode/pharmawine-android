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
    private Integer networkId;
    @SerializedName("areas")
    @Expose
    private List<Object> areas = null;
    @SerializedName("types")
    @Expose
    private Object types;
    @SerializedName("products")
    @Expose
    private List<Object> products = null;
    @SerializedName("network")
    @Expose
    private Network network;
    @SerializedName("networks")
    @Expose
    private Object networks;
    @SerializedName("goals")
    @Expose
    private List<Object> goals = null;
    @SerializedName("roles")
    @Expose
    private List<AuthUserRole> roles = null;

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

    public static AuthUser getAuthenticatedUser(Context mContext) {
        AuthUser u = new AuthUser();

        SharedPreferences sharedPref = mContext.getSharedPreferences(
                Constants.F_PROFIL, Context.MODE_PRIVATE);

        u.setId(sharedPref.getInt(Constants.SP_ID_KEY, -1));
        u.setTypeId(sharedPref.getInt(Constants.SP_TYPE_KEY, -1));
        u.setNetworkId(sharedPref.getInt(Constants.SP_NETWORK_KEY, -1));
        u.setFirstname(sharedPref.getString(Constants.SP_FIRSTNAME_KEY, ""));
        u.setLastname(sharedPref.getString(Constants.SP_LASTNAME_KEY, ""));
        u.setAvatar(sharedPref.getString(Constants.SP_AVATAR_URL_KEY, ""));
        u.setEmail(sharedPref.getString(Constants.SP_EMAIL_KEY, ""));
        u.setBirthday(sharedPref.getString(Constants.SP_BIRTHDAY_KEY, ""));
        u.setSex(sharedPref.getString(Constants.SP_SEX_KEY, ""));
        u.setNationalite(sharedPref.getString(Constants.SP_NATIONALITY_KEY, ""));
        u.setTelephone1(sharedPref.getString(Constants.SP_PHONE_1_KEY, ""));
        u.setTelephone2(sharedPref.getString(Constants.SP_PHONE_2_KEY, ""));
        u.setMaritalStatus(sharedPref.getString(Constants.SP_MARITAL_STATUS_KEY, ""));

        // Objects
        Gson gson = new Gson();

        String NetworkObjectJson = sharedPref.getString(Constants.SP_NETWORK_OBJECT_KEY, "");
        u.setNetwork(gson.fromJson(NetworkObjectJson, Network.class));

        return u;
    }

    /**
     * Clearing all datas from shared prefrences file used for user profile
     */
    public static Boolean deleteUserDatas(Context mContext) {

        try {
            SharedPreferences sharedPref = mContext.getSharedPreferences(
                    Constants.F_PROFIL, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPref.edit();

            editor.clear();

            editor.commit();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Integer getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Integer networkId) {
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

    public String getFullName() {
        return getFirstname() + " " + getLastname();
    }

    /**
     * Get authenticated user roles
     *
     * @param mContext
     * @return
     */
    public static final int getRoleFromSharedPreferences(Context mContext) {
        SharedPreferences sharedPref = mContext.getSharedPreferences(
                Constants.F_PROFIL, Context.MODE_PRIVATE);

        return sharedPref.getInt(Constants.SP_ROLE_KEY, -1);

    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public List<AuthUserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AuthUserRole> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }


    /**
     * Get authenticated user token
     *
     * @param mContext
     * @return
     */
    public static String getToken(Context mContext) {
        SharedPreferences sharedPref = mContext.getSharedPreferences(
                Constants.F_PROFIL, Context.MODE_PRIVATE);

        return sharedPref.getString(Constants.SP_TOKEN_KEY, "");

    }

    /**
     * Write user data into shared Preferences
     *
     * @param context
     * @return
     */
    public Boolean storeInSharedPreferences(Context context, String token) {

        Integer DEFAULT_DELEGUE_ROLE_ID = 3;

        try {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    Constants.F_PROFIL, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putInt(Constants.SP_ID_KEY, this.id);
            editor.putString(Constants.SP_TOKEN_KEY, token);
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
            editor.putInt(Constants.SP_ROLE_KEY, getFirstRole().getId());
            editor.putInt(Constants.SP_NETWORK_KEY, getNetworkId());

            // Objects
            Gson gson = new Gson();
            String networkObjectToJsonString = gson.toJson(this.network);
            editor.putString(Constants.SP_NETWORK_OBJECT_KEY, networkObjectToJsonString);

            // Un compte de user actif a pour status = 0 sinon 1
            editor.putInt(Constants.SP_ACCOUNT_STATUS_KEY, this.status);

            editor.commit();

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * Recupere le vrai roles de l'utilisatuer
     *
     * @return
     */
    public AuthUserRole getFirstRole() {
        if (this.roles != null && !getRoles().isEmpty())
            return getRoles().get(0);
        else
            return null;
    }


}