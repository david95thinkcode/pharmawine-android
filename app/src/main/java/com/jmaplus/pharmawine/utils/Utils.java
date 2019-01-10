package com.jmaplus.pharmawine.utils;

import android.content.Context;
import android.widget.Toast;

import javax.annotation.Nullable;

public class Utils {

    public static void presentToast(Context mContext, String message, @Nullable Boolean Long_duration) {
        if (Long_duration) {
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }
}
