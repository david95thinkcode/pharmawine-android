package com.jmaplus.pharmawine.utils;

public class Constants {

    // ENVIRONMENT VARIABLE
    // ===================================
    public static final Boolean ENV_TESTMODE = true;

    // SHARED PREFERENCES PRIVATE FILES
    // ===================================
    //
    public static final String F_PROFIL = "f_profil";
    public static final String F_MENU = "f_menu";
    public static final String F_REPORTS = "f_reports";


    // SHARED PREFERENCES KEYS
    // ===================================
    //
    public static final String SP_ID_KEY = "sp_id";
    public static final String SP_SEX_KEY = "sp_sex";
    public static final String SP_FIRSTNAME_KEY = "sp_username";
    public static final String SP_LASTNAME_KEY = "sp_lastname";
    public static final String SP_SPECIALITY_KEY = "sp_speciality";
    public static final String SP_MARITAL_STATUS_KEY = "sp_ms";
    public static final String SP_NATIONALITY_KEY = "sp_nationality";
    public static final String SP_BIRTHDAY_KEY = "sp_birthday";
    public static final String SP_PHONE_1_KEY = "sp_phone1";
    public static final String SP_PHONE_2_KEY = "sp_phone2";
    public static final String SP_EMAIL_KEY = "sp_mail";
    public static final String SP_TYPE_KEY = "sp_type";
    public static final String SP_ROLE_KEY = "sp_role";
    public static final String SP_NETWORK_KEY = "sp_network_id";
    public static final String SP_NETWORK_OBJECT_KEY = "sp_network_name";
    public static final String SP_ACCOUNT_STATUS_KEY = "sp_status";
    public static final String SP_AVATAR_URL_KEY = "sp_avatar";
    public static final String SP_TOKEN_KEY = "sp_token";

    // MENU ITEMS ================================
    public static final String MENU_PLANNING_KEY = "menu_planning";
    public static final String MENU_CLIENTS_KEY = "menu_clients";
    public static final String MENU_RESEAUX_KEY = "menu_reseaux";
    public static final String MENU_PRODUITS_KEY = "menu_produits";

    // USERS ROLES ================================
    public static final int ROLE_ADMIN_KEY = 1;
    public static final int ROLE_SUPERVISEUR_KEY = 2;
    public static final int ROLE_DELEGUE_KEY = 3;

    // CUSTOMER TYPES ========================
    public static final int TYPE_PHARMACEUTICAL_KEY = 1;
    public static final int TYPE_MEDICAL_KEY = 2;

    // CLIENT TYPES KEY
    public static String CLIENT_MEDICAL_TEAM_TYPE_KEY = "medical_team";
    public static String CLIENT_PHARMACY_TYPE_KEY = "pharmacy";

    public static String CLIENT_ID_KEY = "client_id";
    public static String CLIENT_FIRSTNAME_KEY = "client_fn";
    public static String CLIENT_LASTNAME_KEY = "client_ln";
    public static String CLIENT_FULLNAME_KEY = "client_fullname";
    public static String CLIENT_SPECIALITY_KEY = "client_spec";
    public static String CLIENT_CUSTOMER_STATUS_KEY = "client_stat";
    public static String CLIENT_CUSTOMER_TYPE_KEY = "client_type";
    public static String CLIENT_SEX_KEY = "client_sex";
    public static String CLIENT_AVATAR_URL_KEY = "client_av_url";


    // NEW PROSPECTS
    public static final String PROSPECT_UNKNOWN_MEDICAL_TEAM_TYPE_KEY = "pukn_medical_team";
    public static final String PROSPECT_UNKNOWN_CLIENT_PHARMACY_TYPE_KEY = "pukn_pharmacy";
    public static final String PROSPECT_KNOWN_MEDICAL_TEAM_TYPE_KEY = "pkn_medical_team";
    public static final String PROSPECT_KNOWN_CLIENT_PHARMACY_TYPE_KEY = "pkn_pharmacy";

    // REPORT HEBDO

    public static final String REPORT_HEBDO_OBJECTIF_NEXT_WEEK_PRESCRIPTEUR = "rhonw_prescripteur";
    public static final String REPORT_HEBDO_OBJECTIF_NEXT_WEEK_PHARMACY = "rhonw_pharmacy";
    public static final String REPORT_HEBDO_OBJECTION = "rh_objection";
    public static final String REPORT_HEBDO_AM_GARDE = "rham_garde";
    public static final String REPORT_HEBDO_AM_PHARMACY = "rham_pharmacy";
    public static final String REPORT_HEBDO_AM_REUNIONS = "rham_reunions";
    public static final String REPORT_HEBDO_AM_RELATION_PUBLIQ = "rham_relation_publiq";
    public static final String REPORT_HEBDO_AM_PARCOURS_FIDEL = "rham_parcours_fide";
    public static final String REPORT_HEBDO_AM_ZONE_PROFOND = "rham_zone_profond";
    public static final String REPORT_HEBDO_SEE_STATISTIQUE = "rh_see_satistique";
}
