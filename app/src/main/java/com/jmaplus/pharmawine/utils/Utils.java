package com.jmaplus.pharmawine.utils;

import android.content.Context;
import android.widget.Toast;

import com.jmaplus.pharmawine.models.TestCountry;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
     * @param date
     * @return Example de reponse : 2019-02-13
     */
    public static final String getFormattedDateForApiRequest(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String MM = String.valueOf(calendar.get(Calendar.MONTH));
        String DD = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            DD = "0" + DD;
        }

        if (calendar.get(Calendar.MONTH) < 10) {
            MM = "0" + MM;
        }

        String formatted = calendar.get(Calendar.YEAR) + "-" + MM + "-" + DD;

        return formatted;
    }

    /**
     * @param date
     * @return Exemple de reponses: 05,23
     */
    public static final String getDayOfMonthFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            return "0" + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        }

        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Retourne le nom du jour de la semaine en francais
     *
     * @param date
     * @return
     */
    public static final String getDayLabelFromDate(Date date) {
        String[] frenchDays = new DateFormatSymbols(Locale.FRENCH).getWeekdays();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return frenchDays[calendar.get(Calendar.DAY_OF_WEEK)];
    }

}
