package com.jmaplus.pharmawine.utils;

import android.content.Context;
import android.widget.Toast;

import com.jmaplus.pharmawine.models.TestCountry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    public static List<TestCountry> getCountries() {
        List<TestCountry> countries = new ArrayList<TestCountry>();
        String[] isoCountries = Locale.getISOCountries();

        for (String country : isoCountries) {
            Locale locale = new Locale(Locale.FRANCE.getLanguage(), country);
            TestCountry testCountryObj = new TestCountry();

            testCountryObj.setName(locale.getDisplayCountry());
            testCountryObj.setIso(locale.getISO3Country());
            testCountryObj.setCode(locale.getCountry());

            if (!"".equals(testCountryObj.getIso()) && !"".equals(testCountryObj.getCode()) && !"".equals(testCountryObj.getName())) {
                countries.add(testCountryObj);
            }
        }

        return countries;
    }

    /**
     * Retourne la description d'un role d'utilisateur en fonction de son ID recu
     *
     * @param roleID
     * @return
     */
    public static String getRoleLabel(int roleID) {
        String role = "";

        switch (roleID) {
            case 1:
                role = "Admin";
                break;
            case 2:
                role = "Superviseur";
                break;
            case 3:
                role = "Visiteur";
                break;
            default:
                role = "Utilisateur quelconque";
                break;
        }

        return role;
    }

    public static final String getBarearTokenString(String token) {
        return "Bearer " + token;
    }

    public static final String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());

        return strDate;
    }

    public static final String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strTime = mdformat.format(calendar.getTime());

        return strTime;
    }

    /**
     * Retourne la liste des jours de la meme semaine que @startDate
     *
     * @param startDate
     * @param onlyDaysOfSameMonth
     */
    public static WeekDays getDaysOfTheWeek(String startDate, Boolean onlyDaysOfSameMonth) {

        WeekDays w = new WeekDays();

        if (!startDate.isEmpty()) {
            // TODO TODO TODO : Finish it

            w.setSameMonth(onlyDaysOfSameMonth);
            w.setStartDate(startDate);
            // todo: pour l'instant mais c'est a changer
            w.setEndDate(startDate);

            if (onlyDaysOfSameMonth) {
                // todo: get only days that got the same month with start date
                // ...
            } else {
                // todo: return all days of the week
                // ...
            }

            return w;
        }

        return null;

    }



}
