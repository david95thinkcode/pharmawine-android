package com.jmaplus.pharmawine.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ObjectifNextWeek implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ObjectifNextWeek> CREATOR = new Parcelable.Creator<ObjectifNextWeek>() {
        @Override
        public ObjectifNextWeek createFromParcel(Parcel in) {
            return new ObjectifNextWeek(in);
        }

        @Override
        public ObjectifNextWeek[] newArray(int size) {
            return new ObjectifNextWeek[size];
        }
    };
    private String prescrpteur;
    private String pharmacy;

    public ObjectifNextWeek() {
    }


    public ObjectifNextWeek(String prescrpteur, String pharmacy) {
        this.prescrpteur = prescrpteur;
        this.pharmacy = pharmacy;
    }

    protected ObjectifNextWeek(Parcel in) {
        prescrpteur = in.readString();
        pharmacy = in.readString();
    }

    public String getPrescrpteur() {
        return prescrpteur;
    }

    public void setPrescrpteur(String prescrpteur) {
        this.prescrpteur = prescrpteur;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(prescrpteur);
        dest.writeString(pharmacy);
    }
}