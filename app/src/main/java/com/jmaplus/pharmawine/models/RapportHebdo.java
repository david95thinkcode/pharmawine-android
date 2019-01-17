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
    private ActiviteMene Am;
    private String objections;
    private ObjectifNextWeek ObNW;

    public RapportHebdo() {
    }

    public RapportHebdo(boolean seeStat, ActiviteMene am, String objections, ObjectifNextWeek obNW) {
        this.seeStat = seeStat;
        Am = am;
        this.objections = objections;
        ObNW = obNW;
    }

    protected RapportHebdo(Parcel in) {
        seeStat = in.readByte() != 0x00;
        Am = (ActiviteMene) in.readValue(ActiviteMene.class.getClassLoader());
        objections = in.readString();
        ObNW = (ObjectifNextWeek) in.readValue(ObjectifNextWeek.class.getClassLoader());
    }

    public boolean isSeeStat() {
        return seeStat;
    }

    public void setSeeStat(boolean seeStat) {
        this.seeStat = seeStat;
    }

    public ActiviteMene getAm() {
        return Am;
    }

    public void setAm(ActiviteMene am) {
        Am = am;
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
        dest.writeValue(Am);
        dest.writeString(objections);
        dest.writeValue(ObNW);
    }
}