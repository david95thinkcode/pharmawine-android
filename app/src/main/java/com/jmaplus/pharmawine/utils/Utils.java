package com.jmaplus.pharmawine.utils;

import android.content.Context;
import android.widget.Toast;

import com.jmaplus.pharmawine.models.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

public class Utils {

    public static void presentToast(Context mContext, String message, @Nullable Boolean Long_duration) {
        if (Long_duration) {
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static List<Country> getCountries() {
        List<Country> countries = new ArrayList<Country>();
        String[] isoCountries = Locale.getISOCountries();

        for (String country : isoCountries) {
            Locale locale = new Locale(Locale.FRANCE.getLanguage(), country);
            Country countryObj = new Country();

            countryObj.setName(locale.getDisplayCountry());
            countryObj.setIso(locale.getISO3Country());
            countryObj.setCode(locale.getCountry());

            if (!"".equals(countryObj.getIso()) && !"".equals(countryObj.getCode()) && !"".equals(countryObj.getName())) {
                countries.add(countryObj);
            }
        }

        return countries;
    }

    /**
     * Retourne la description d'un role d'utilisateur en fonction de son ID recu
     * @param roleID
     * @return
     */
    public static String getRoleLabel(int roleID) {
        String role = "";

        switch (roleID) {
            case 1: role = "Admin"; break;
            case 2: role = "Superviseur"; break;
            case 3: role = "Visiteur"; break;
            default : role = "Utilisateur quelconque"; break;
        }

        return role;
    }


}
