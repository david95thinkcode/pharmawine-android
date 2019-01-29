package com.jmaplus.pharmawine.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerCreateRequest {

    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("area_ids")
    @Expose
    private List<Integer> areaIds = null;
    @SerializedName("speciality_id")
    @Expose
    private Integer specialityId;
    @SerializedName("centers")
    @Expose
    private List<String> centers = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nb_employe")
    @Expose
    private Integer nbEmploye;
    @SerializedName("customer_type_id")
    @Expose
    private Integer customerTypeId;
    @SerializedName("customer_status_id")
    @Expose
    private Integer customerStatusId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("tel")
    @Expose
    private String tel;

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public List<Integer> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<Integer> areaIds) {
        this.areaIds = areaIds;
    }

    public Integer getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Integer specialityId) {
        this.specialityId = specialityId;
    }

    public List<String> getCenters() {
        return centers;
    }

    public void setCenters(List<String> centers) {
        this.centers = centers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNbEmploye() {
        return nbEmploye;
    }

    public void setNbEmploye(Integer nbEmploye) {
        this.nbEmploye = nbEmploye;
    }

    public Integer getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(Integer customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public Integer getCustomerStatusId() {
        return customerStatusId;
    }

    public void setCustomerStatusId(Integer customerStatusId) {
        this.customerStatusId = customerStatusId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}