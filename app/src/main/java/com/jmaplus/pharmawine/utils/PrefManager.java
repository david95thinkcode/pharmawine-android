package com.jmaplus.pharmawine.utils;

import android.content.Context;
import android.content.SharedPreferences;

public final class PrefManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "PharmaWine";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private final String TOKEN = "com.jmaplus.pharmawine.userToken";

    public PrefManager(Context context){
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setToken(String token) {
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public String getToken() {
        return pref.getString(TOKEN, "");
    }
}
