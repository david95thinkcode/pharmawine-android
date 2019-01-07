package com.jmaplus.pharmawine;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.bloco.faker.Faker;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PharmaWine extends Application {

    private static PharmaWine mInstance;
    public static Realm mRealm;
    public static Faker mFaker;


    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        mRealm = Realm.getDefaultInstance();
        mFaker = new Faker();
    }

    public static synchronized PharmaWine getInstance() {
        return mInstance;
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            NetworkInfo[] info = connMgr.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return (networkInfo != null && networkInfo.isConnected());
    }
}
