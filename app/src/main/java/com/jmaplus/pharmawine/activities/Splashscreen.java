package com.jmaplus.pharmawine.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.utils.Constants;

public class Splashscreen extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        final Context mContext = this;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                /**
                 * This method will be executed once the timer is over
                 * Start your app main activity
                 * Choose the start activity
                 * If user is already authenticated, don't show login activity
                 * Else show it
                 */

                SharedPreferences sharedPref = mContext.getSharedPreferences(Constants.F_PROFIL,
                        Context.MODE_PRIVATE);
                String userToken = sharedPref.getString(Constants.SP_TOKEN_KEY, "");

                if (userToken.isEmpty()) {
                    startActivity(new Intent(Splashscreen.this, LoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(Splashscreen.this, MainActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
