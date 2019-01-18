package com.jmaplus.pharmawine.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ActiviteMene implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ActiviteMene> CREATOR = new Parcelable.Creator<ActiviteMene>() {
        @Override
        public ActiviteMene createFromParcel(Parcel in) {
            return new ActiviteMene(in);
        }

        @Override
        public ActiviteMene[] newArray(int size) {
            return new ActiviteMene[size];
        }
    };
    private String gardes;
    private String reunion;
    private String ralationPublique;
    private String actionPharmacy;
    private String parcoursFidelisation;
    private String zonesProfondes;

    public ActiviteMene(String gardes, String reunion, String ralationPublique, String actionPharmacy, String parcoursFidelisation, String zonesProfondes) {
        this.gardes = gardes;
        this.reunion = reunion;
        this.ralationPublique = ralationPublique;
        this.actionPharmacy = actionPharmacy;
        this.parcoursFidelisation = parcoursFidelisation;
        this.zonesProfondes = zonesProfondes;
    }

    protected ActiviteMene(Parcel in) {
        gardes = in.readString();
        reunion = in.readString();
        ralationPublique = in.readString();
        actionPharmacy = in.readString();
        parcoursFidelisation = in.readString();
        zonesProfondes = in.readString();
    }

    public String getGardes() {
        return gardes;
    }

    public String getReunion() {
        return reunion;
    }

    public void setReunion(String reunion) {
        this.reunion = reunion;
    }

    public void setGardes(String gardes) {
        this.gardes = gardes;
    }


    public String getRalationPublique() {
        return ralationPublique;
    }

    public void setRalationPublique(String ralationPublique) {
        this.ralationPublique = ralationPublique;
    }

    public String getActionPharmacy() {
        return actionPharmacy;
    }

    public void setActionPharmacy(String actionPharmacy) {
        this.actionPharmacy = actionPharmacy;
    }

    public String getParcoursFidelisation() {
        return parcoursFidelisation;
    }

    public void setParcoursFidelisation(String parcoursFidelisation) {
        this.parcoursFidelisation = parcoursFidelisation;
    }

    public String getZonesProfondes() {
        return zonesProfondes;
    }

    public void setZonesProfondes(String zonesProfondes) {
        this.zonesProfondes = zonesProfondes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gardes);
        dest.writeString(reunion);
        dest.writeString(ralationPublique);
        dest.writeString(actionPharmacy);
        dest.writeString(parcoursFidelisation);
        dest.writeString(zonesProfondes);
    }
}