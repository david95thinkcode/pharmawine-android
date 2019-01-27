package com.jmaplus.pharmawine.models;

import android.os.Parcel;
import android.os.Parcelable;

public class RapportHebdo implements Parcelable {

    // We must transform ActiviteMene and ObjectifNextWeek to Nested Class After

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RapportHebdo> CREATOR = new Parcelable.Creator<RapportHebdo>() {
        @Override
        public RapportHebdo createFromParcel(Parcel in) {
            return new RapportHebdo(in);
        }

        @Override
        public RapportHebdo[] newArray(int size) {
            return new RapportHebdo[size];
        }
    };
    private boolean seeStat;
    private ActiviteMene activiteMene;
    private String objections;
    private ObjectifNextWeek ObNW;

    public RapportHebdo() {
    }

    public RapportHebdo(boolean seeStat, ActiviteMene activiteMene, String objections, ObjectifNextWeek obNW) {
        this.seeStat = seeStat;
        this.activiteMene = activiteMene;
        this.objections = objections;
        ObNW = obNW;
    }

    protected RapportHebdo(Parcel in) {
        seeStat = in.readByte() != 0x00;
        activiteMene = (ActiviteMene) in.readValue(ActiviteMene.class.getClassLoader());
        objections = in.readString();
        ObNW = (ObjectifNextWeek) in.readValue(ObjectifNextWeek.class.getClassLoader());
    }

    public boolean isSeeStat() {
        return seeStat;
    }

    public void setSeeStat(boolean seeStat) {
        this.seeStat = seeStat;
    }

    public ActiviteMene getActiviteMene() {
        return activiteMene;
    }

    public void setActiviteMene(ActiviteMene activiteMene) {
        this.activiteMene = activiteMene;
    }

    public String getObjections() {
        return objections;
    }

    public void setObjections(String objections) {
        this.objections = objections;
    }

    public ObjectifNextWeek getObNW() {
        return ObNW;
    }

    public void setObNW(ObjectifNextWeek obNW) {
        ObNW = obNW;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (seeStat ? 0x01 : 0x00));
        dest.writeValue(activiteMene);
        dest.writeString(objections);
        dest.writeValue(ObNW);
    }
}