package com.jmaplus.pharmawine.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiProduct {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("prix_ght")
    @Expose
    private String prixGht;
    @SerializedName("prix_cession")
    @Expose
    private String prixCession;
    @SerializedName("prix_public")
    @Expose
    private String prixPublic;
    @SerializedName("amm")
    @Expose
    private String amm;
    @SerializedName("fin_amm")
    @Expose
    private String finAmm;
    @SerializedName("num_amm")
    @Expose
    private String numAmm;
    @SerializedName("archived")
    @Expose
    private Integer archived;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("laboratory_id")
    @Expose
    private Integer laboratoryId;
    @SerializedName("pharmaceutical_class_id")
    @Expose
    private Integer pharmaceuticalClassId;
    @SerializedName("pharmaceutical_class")
    @Expose
    private PharmaceuticalClass pharmaceuticalClass;
    @SerializedName("laboratory")
    @Expose
    private ApiLaboratory laboratory;
    @SerializedName("specialities")
    @Expose
    private List<Speciality> specialities = null;
    @SerializedName("sale_goals")
    @Expose
    private List<SaleGoal> saleGoals = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPrixGht() {
        return prixGht;
    }

    public void setPrixGht(String prixGht) {
        this.prixGht = prixGht;
    }

    public String getPrixCession() {
        return prixCession;
    }

    public void setPrixCession(String prixCession) {
        this.prixCession = prixCession;
    }

    public String getPrixPublic() {
        return prixPublic;
    }

    public void setPrixPublic(String prixPublic) {
        this.prixPublic = prixPublic;
    }

    public String getAmm() {
        return amm;
    }

    public void setAmm(String amm) {
        this.amm = amm;
    }

    public String getFinAmm() {
        return finAmm;
    }

    public void setFinAmm(String finAmm) {
        this.finAmm = finAmm;
    }

    public String getNumAmm() {
        return numAmm;
    }

    public void setNumAmm(String numAmm) {
        this.numAmm = numAmm;
    }

    public Integer getArchived() {
        return archived;
    }

    public void setArchived(Integer archived) {
        this.archived = archived;
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

    public Integer getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(Integer laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    public Integer getPharmaceuticalClassId() {
        return pharmaceuticalClassId;
    }

    public void setPharmaceuticalClassId(Integer pharmaceuticalClassId) {
        this.pharmaceuticalClassId = pharmaceuticalClassId;
    }

    public PharmaceuticalClass getPharmaceuticalClass() {
        return pharmaceuticalClass;
    }

    public void setPharmaceuticalClass(PharmaceuticalClass pharmaceuticalClass) {
        this.pharmaceuticalClass = pharmaceuticalClass;
    }

    public ApiLaboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(ApiLaboratory laboratory) {
        this.laboratory = laboratory;
    }

    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
        this.specialities = specialities;
    }

    public List<SaleGoal> getSaleGoals() {
        return saleGoals;
    }

    public void setSaleGoals(List<SaleGoal> saleGoals) {
        this.saleGoals = saleGoals;
    }
}
