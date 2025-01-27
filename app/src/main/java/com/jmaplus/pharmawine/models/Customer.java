package com.jmaplus.pharmawine.models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.utils.Constants;

import java.util.List;

public class Customer implements Cloneable {

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
    @SerializedName("tel")
    @Expose
    private String tel;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("maritalStatus")
    @Expose
    private String maritalStatus;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("phoneNumber1")
    @Expose
    private String phoneNumber1;
    @SerializedName("phoneNumber2")
    @Expose
    private String phoneNumber2;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("preference")
    @Expose
    private String preference;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nb_employe")
    @Expose
    private String nbEmploye;
    @SerializedName("speciality_id")
    @Expose
    private Integer specialityId;
    @SerializedName("customer_type_id")
    @Expose
    private Integer customerTypeId;
    @SerializedName("customer_status_id")
    @Expose
    private Integer customerStatusId;
    @SerializedName("areas")
    @Expose
    private List<Area> areas = null;
    @SerializedName("centers")
    @Expose
    private List<Center> centers = null;
    @SerializedName("customer_type")
    @Expose
    private CustomerType customerType;
    @SerializedName("customer_status")
    @Expose
    private CustomerStatus customerStatus;
    @SerializedName("speciality")
    @Expose
    private Speciality speciality;

    public String getFullName() {
        try {
            return getFirstname() + " " + getLastname();

        } catch (NullPointerException e) {
            return "Aucun non defini";
        }
    }

    public String getRedabledate() {
        return getBirthday();
    }

    /**
     * Retourne true s'il s'agit d'un client connu et false sinon
     *
     * @return
     */
    public Boolean isKnown() {
        /**
         * Les id 8 et 7 representent ceux
         * des status des clients inconnus
         */

        try {
            return this.getCustomerStatusId() != 8 && this.getCustomerStatusId() != 7;
        } catch (Exception e) {
            Log.e(getClass().getName(), "isKnown() " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    public Integer getFillingLevel() {
        Integer totalFieldNumber = 11;
        Integer filledFieldNumber = 0;

        if (getCustomerTypeId() == Constants.TYPE_MEDICAL_KEY) {
            if (this.lastname != null && !this.lastname.isEmpty()) {
                filledFieldNumber++;
            }
            if (this.firstname != null && !this.firstname.isEmpty()) {
                filledFieldNumber++;
            }
            if (this.sex != null && !this.sex.isEmpty()) {
                filledFieldNumber++;
            }
            if (this.maritalStatus != null && !this.maritalStatus.isEmpty()) {
                filledFieldNumber++;
                Log.i("Customer", filledFieldNumber + " : " + "maritalStatus ==> : " + maritalStatus);
            }
            if (this.nationality != null && !this.nationality.isEmpty()) {
                filledFieldNumber++;
            }
            if (this.getSpecialityId() != null || this.getSpecialityId() != 0) {
                filledFieldNumber++;
            }
            if (this.religion != null && !this.religion.isEmpty()) {
                filledFieldNumber++;
            }
        } else {
            filledFieldNumber += 7;
        }

        if (this.birthday != null && !this.birthday.isEmpty()) {
            filledFieldNumber++;
        }

        if (this.phoneNumber1 != null && !this.phoneNumber1.isEmpty()) {
            filledFieldNumber++;
        }

        if (this.address != null && !this.address.isEmpty()) {
            filledFieldNumber++;
        }
        if (this.email != null && !this.email.isEmpty()) {
            filledFieldNumber++;
        }
        // Avatar is not required
        // if (this.avatar != null && !this.avatar.isEmpty()) {
        //     filledFieldNumber++;
        // }
        // Phone number 2 is not required
        // if (this.phoneNumber2 != null && !this.phoneNumber2.isEmpty()) {
        //filledFieldNumber++;
        // }


        double level = filledFieldNumber.doubleValue() / totalFieldNumber * 100;

        return (int) level;
    }

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public Integer getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Integer specialityId) {
        this.specialityId = specialityId;
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

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public List<Center> getCenters() {
        return centers;
    }

    public void setCenters(List<Center> centers) {
        this.centers = centers;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(CustomerStatus customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNbEmploye() {
        return nbEmploye;
    }

    public void setNbEmploye(String nbEmploye) {
        this.nbEmploye = nbEmploye;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public int getDefaultAvatar() {
        int ic_doctor_man = R.drawable.ic_doctor_man;
        int ic_doctor_woman = R.drawable.ic_doctor_woman;

        if (getCustomerTypeId() == Constants.TYPE_MEDICAL_KEY && (getSex() != null)) {
            if (getSex().toUpperCase().equals("F"))
                return ic_doctor_woman;
            else
                return ic_doctor_man;
        } else {
            return R.drawable.ic_pharmacy;
        }

    }

    public int getBigDefaultAvatar() {
        int ic_doctor_man = R.drawable.bg_doctor_man;
        int ic_doctor_woman = R.drawable.bg_doctor_woman;

        if (getSex() != null) {
            if (getSex().toUpperCase().equals("F"))
                return ic_doctor_woman;
            else
                return ic_doctor_man;
        } else {
            return R.drawable.bg_avatar_pharmacy;
        }
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}