package com.jmaplus.pharmawine.database.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jmaplus.pharmawine.database.model.ClientsList;
import com.jmaplus.pharmawine.database.model.DailyReportNotSent;
import com.jmaplus.pharmawine.database.model.UserProduct;
import com.jmaplus.pharmawine.models.Customer;
import com.jmaplus.pharmawine.models.DailyReportEnd;
import com.jmaplus.pharmawine.models.DailyReportEndResponse;
import com.jmaplus.pharmawine.models.DailyReportStart;
import com.jmaplus.pharmawine.models.DailyReportStartResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    //=================================== Table Names==============================================//

    public static final String TABLE_REPORT = "dailyReport";
    public static final String TABLE_CLIENT = "ClientList";
    public static final String TABLE_PRODUCT = "userProduct";
    public static final String TABLE_DAILY_REPORT_START = "dailyReportStart";
    public static final String TABLE_DAILY_REPORT_START_RESPONSE = "dailyReportStartResponse";
    public static final String TABLE_DAILY_REPORT_END = "dailyReportEnd";
    public static final String TABLE_DAILY_REPORT_END_RESPONSE = "dailyReportEndResponse";
    public static final String TABLE_CUSTOMER = "customer";

    //================================================================================================//

    //=============================================COLUMN NAMES ========================================//

    //======================= REPORT Table - column names ================//
    public static final String COLUMN_REPORT_ID = "id";
    public static final String COLUMN_REPORT = "report";
    public static final String COLUMN_REPORT_CREATION_DATE = "creationDate";
    public static final String COLUMN_REPORT_STATUS = "reportStatus";
    public static final String COLUMN_REPORT_START = "dailyReportStart";
    public static final String COLUMN_REPORT_END = "dailyReportEnd";
    public static final String COLUMN_REPORT_IS_START_REQUEST_SENT = "isStartRequestSent";
    public static final String COLUMN_REPORT_IS_END_REQUEST_SENT = "isEndRequestSent";


    // ======================TABLE_DAILY_REPORT_START Column names ===========//

    public static final String COLUMN_DAILY_REPORT_START_ID = "dailyReportStartID";
    public static final String COLUMN_DAILY_REPORT_START_USER_ID = "dailyReportStartUserID";
    public static final String COLUMN_DAILY_REPORT_START_CUSTOMER_ID = "dailyReportStartCustomerID";
    public static final String COLUMN_DAILY_REPORT_START_TIME = "dailyReportStartTime";


    //=======================TABLE_DAILY_REPORT_START_RESPONSE COLUMN NAMES=========//

    public static final String COLUMN_DAILY_REPORT_START_RESPONSE_DAY = "dailyReportStartResponseDay";
    public static final String COLUMN_DAILY_REPORT_START_RESPONSE_START_TIME = "dailyReportStartResponseStartTime";
    public static final String COLUMN_DAILY_REPORT_START_RESPONSE_CUSTOMER_ID = "dailyReportStartResponseCustomerID";
    public static final String COLUMN_DAILY_REPORT_START_RESPONSE_USER_ID = "dailyReportStartResponseUserId";
    public static final String COLUMN_DAILY_REPORT_START_RESPONSE_UPDATEAT = "dailyReportStartResponseUpdateAt";
    public static final String COLUMN_DAILY_REPORT_START_RESPONSE_CREATEDAT = "dailyReportStartResponseCreateAt";
    public static final String COLUMN_DAILY_REPORT_START_RESPONSE_ID = "dailyReportStartResponseID";


    //=======================TABLE_DAILY_REPORT_END Column names==================//
    public static final String COLUMN_DAILY_REPORT_END_GOAL = "dailyReportEndGoal";
    public static final String COLUMN_DAILY_REPORT_END_PROMISES = "dailyReportEndPromises";
    public static final String COLUMN_DAILY_REPORT_END_PRESCRIPTION = "dailyReportEndPrescription";
    public static final String COLUMN_DAILY_REPORT_END_CENTER_ID = "dailyReportEndCenterID";
    public static final String COLUMN_DAILY_REPORT_END_TIME = "dailyReportEndTime";
    public static final String COLUMN_DAILY_REPORT_END_CUSTOMER_ID = "dailyReportEndCustomerID";


    //=====================TABLE_DAILY_REPORT_END_RESPONSE COLUMN NAMES==============//

    public static final String COLUMN_DAILY_REPORT_END_RESPONSE_ID = "dailyReportEndResponseID";
    public static final String COLUMN_DAILY_REPORT_END_RESPONSE_DAY = "dailyReportEndResponseDay";
    public static final String COLUMN_DAILY_REPORT_END_RESPONSE_GOAL = "dailyReportEndResponseGoal";
    public static final String COLUMN_DAILY_REPORT_END_RESPONSE_PROMISES = "dailyReportEndResponsePromise";
    public static final String COLUMN_DAILY_REPORT_END_RESPONSE_PRESCRIPTION = "dailyReportEndResponsePrescription";
    public static final String COLUMN_DAILY_REPORT_END_RESPONSE_START_TIME = "dailyReportEndResponseStartTime";
    public static final String COLUMN_DAILY_REPORT_END_RESPONSE_END_TIME = "dailyReportEndResponseEndTime";
    public static final String COLUMN_DAILY_REPORT_END_RESPONSE_CREATE_AT = "dailyReportEndResponseCreateAt";
    public static final String COLUMN_DAILY_REPORT_END_RESPONSE_UPDATE_AT = "dailyReportEndResponseUpdateAt";
    public static final String COLUMN_DAILY_REPORT_END_RESPONSE_USER_ID = "dailyReportEndResponseUserID";
    public static final String COLUMN_DAILY_REPORT_END_RESPONSE_CENTER_ID = "dailyReportEndResponseCenterID";
    public static final String COLUMN_DAILY_REPORT_END_RESPONSE_NETWORK_ID = "dailyReportEndResponseNetworkID";
    public static final String COLUMN_DAILY_REPORT_END_RESPONSE_HEBDO_DELEGATE_ID = "dailyReportEndResponseHebdoDelegateID";
    public static final String COLUMN_DAILY_REPORT_END_RESPONSE_CUSTOMER_ID = "dailyReportEndResponseCustomerID";
    public static final String COLUMN_DAILY_REPORT_END_RESPONSE_STATUS = "dailyReportEndResponseStatusID";
    public static final String COLUMN_DAILY_REPORT_END_RESPONSE_VIEWED = "dailyReportEndResponseViewedID";


    //========================== CUSTOMER TABLE COLUMN NAMES===============================//

    public static final String COLUMN_CUSTOMER_ID = "customerID";
    public static final String COLUMN_CUSTOMER_LAST_NAME = "customerLastName";
    public static final String COLUMN_CUSTOMER_FIRST_NAME = "customerFirstName";
    public static final String COLUMN_CUSTOMER_SEX = "customerSex";
    public static final String COLUMN_CUSTOMER_TEL = "customerTel";
    public static final String COLUMN_CUSTOMER_BIRTHDAY = "customerBirthday";
    public static final String COLUMN_CUSTOMER_NATIONALITY = "customerNationality";
    public static final String COLUMN_CUSTOMER_MARTIAL_STATUS = "customerMartialStatus";
    public static final String COLUMN_CUSTOMER_RELIGION = "customerReligion";
    public static final String COLUMN_CUSTOMER_PHONE1 = "customerPhone1";
    public static final String COLUMN_CUSTOMER_PHONE2 = "customerPhone2";
    public static final String COLUMN_CUSTOMER_ADRESS = "customerAdress";
    public static final String COLUMN_CUSTOMER_PREF = "customerPref";
    public static final String COLUMN_CUSTOMER_EMAIL = "customerEmail";
    public static final String COLUMN_CUSTOMER_AVATAR = "customerAvatar";
    public static final String COLUMN_CUSTOMER_CREATE_AT = "customerCreateAt";
    public static final String COLUMN_CUSTOMER_UPDATE_AT = "customerUpdateAt";
    public static final String COLUMN_CUSTOMER_SPECIALITY_ID = "customerSpecialityID";
    public static final String COLUMN_CUSTOMER_TYPE_ID = "customerTypeID";
    public static final String COLUMN_CUSTOMER_STATUS_ID = "customerStatusID";
    public static final String COLUMN_CUSTOMER_AREAS = "customerAreas";
    public static final String COLUMN_CUSTOMER_CENTER = "customerCenter";
    public static final String COLUMN_CUSTOMER_TYPE = "customerType";
    public static final String COLUMN_CUSTOMER_STATUS = "customerStatus";
    public static final String COLUMN_CUSTOMER_SPECIALITY = "customerSpeciality";


    //==========================CLIENT Table - column names===================//
    public static final String COLUMN_CLIENT_ID = "id";
    public static final String COLUMN_CLIENT = "client";
    public static final String COLUMN_DATE_PROSPECTION = "dateProspection";


    //==================USER_PRODUCT Table - column names ====================//

    public static final String COLUMN_PRODUCT_ID = "id";
    public static final String COLUMN_PRODUCT = "product";

    //==================================================CREATION OF TABLES ====================================================//

    // =============================REPORT TABLE=========================//

    public static final String CREATE_TABLE_REPORT =
            "CREATE TABLE " + TABLE_REPORT + "("
                    + COLUMN_REPORT_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_REPORT + " TEXT,"
                    + COLUMN_REPORT_CREATION_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + COLUMN_REPORT_STATUS + " BOOLEAN"
                    + ")";

    // ============================= CLIENT TABLE=========================//

    public static final String CREATE_TABLE_CLIENT =
            "CREATE TABLE " + TABLE_CLIENT + "("
                    + COLUMN_CLIENT_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_CLIENT + " TEXT,"
                    + COLUMN_DATE_PROSPECTION + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    // ============================= PRODUCT TABLE=========================//

    public static final String CREATE_TABLE_PRODUCT =
            "CREATE TABLE " + TABLE_PRODUCT + "("
                    + COLUMN_PRODUCT_ID + " INTEGER,"
                    + COLUMN_PRODUCT + " TEXT"
                    + ")";

    // ============================= REPORT START TABLE=========================//

    public static final String CREATE_TABLE_DAILY_REPORT_START =
            "CREATE TABLE " + TABLE_DAILY_REPORT_START + "("
                    + COLUMN_DAILY_REPORT_START_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DAILY_REPORT_START_USER_ID + " INTEGER,"
                    + COLUMN_DAILY_REPORT_START_CUSTOMER_ID + " INTEGER,"
                    + COLUMN_DAILY_REPORT_START_TIME + " INTEGER"
                    + ")";


    // ============================= REPORT START RESPONSE TABLE===================//

    public static final String CREATE_TABLE_DAILY_REPORT_START_RESPONSE =
            "CREATE TABLE " + TABLE_DAILY_REPORT_START_RESPONSE + "("
                    + COLUMN_DAILY_REPORT_START_RESPONSE_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_DAILY_REPORT_START_RESPONSE_USER_ID + " INTEGER,"
                    + COLUMN_DAILY_REPORT_START_RESPONSE_CUSTOMER_ID + " INTEGER,"
                    + COLUMN_DAILY_REPORT_START_RESPONSE_START_TIME + " INTEGER,"
                    + COLUMN_DAILY_REPORT_START_RESPONSE_DAY + " TEXT,"
                    + COLUMN_DAILY_REPORT_START_RESPONSE_CREATEDAT + " TEXT,"
                    + COLUMN_DAILY_REPORT_START_RESPONSE_UPDATEAT + " TEXT"
                    + ")";


    // ============================= REPORT END TABLE=========================//

    public static final String CREATE_TABLE_DAILY_REPORT_END =
            "CREATE TABLE " + TABLE_DAILY_REPORT_END + "("
                    + COLUMN_DAILY_REPORT_END_GOAL + " TEXT,"
                    + COLUMN_DAILY_REPORT_END_PROMISES + " TEXT,"
                    + COLUMN_DAILY_REPORT_END_PRESCRIPTION + " TEXT,"
                    + COLUMN_DAILY_REPORT_END_CENTER_ID + " INTEGER,"
                    + COLUMN_DAILY_REPORT_END_TIME + " INTEGER,"
                    + COLUMN_DAILY_REPORT_END_CUSTOMER_ID + " INTEGER"
                    + ")";
    // ============================= REPORT END RESPONSE TABLE=========================//

    public static final String CREATE_TABLE_DAILY_REPORT_END_RESPONSE =
            "CREATE TABLE " + TABLE_DAILY_REPORT_END_RESPONSE + "("
                    + COLUMN_DAILY_REPORT_END_RESPONSE_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_DAILY_REPORT_END_RESPONSE_DAY + " TEXT,"
                    + COLUMN_DAILY_REPORT_END_RESPONSE_GOAL + " TEXT,"
                    + COLUMN_DAILY_REPORT_END_RESPONSE_PROMISES + " TEXT,"
                    + COLUMN_DAILY_REPORT_END_RESPONSE_PRESCRIPTION + " TEXT,"
                    + COLUMN_DAILY_REPORT_END_RESPONSE_START_TIME + " TEXT,"
                    + COLUMN_DAILY_REPORT_END_RESPONSE_END_TIME + " TEXT,"
                    + COLUMN_DAILY_REPORT_END_RESPONSE_CREATE_AT + " TEXT,"
                    + COLUMN_DAILY_REPORT_END_RESPONSE_UPDATE_AT + " TEXT,"
                    + COLUMN_DAILY_REPORT_END_RESPONSE_USER_ID + " INTEGER,"
                    + COLUMN_DAILY_REPORT_END_RESPONSE_CENTER_ID + " INTEGER,"
                    + COLUMN_DAILY_REPORT_END_RESPONSE_NETWORK_ID + " TEXT,"
                    + COLUMN_DAILY_REPORT_END_RESPONSE_HEBDO_DELEGATE_ID + " TEXT,"
                    + COLUMN_DAILY_REPORT_END_RESPONSE_CUSTOMER_ID + " INTEGER,"
                    + COLUMN_DAILY_REPORT_END_RESPONSE_STATUS + " INTEGER,"
                    + COLUMN_DAILY_REPORT_END_RESPONSE_VIEWED + " INTEGER"
                    + ")";
    //================================= CUSTOMER TABLE===========================================//
    public static final String CREATE_TABLE_CUSTOMER =
            "CREATE TABLE " + TABLE_CUSTOMER + "("
                    + COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_CUSTOMER_LAST_NAME + " TEXT,"
                    + COLUMN_CUSTOMER_FIRST_NAME + " TEXT,"
                    + COLUMN_CUSTOMER_SEX + " TEXT,"
                    + COLUMN_CUSTOMER_TEL + " TEXT,"
                    + COLUMN_CUSTOMER_BIRTHDAY + " TEXT,"
                    + COLUMN_CUSTOMER_NATIONALITY + " TEXT,"
                    + COLUMN_CUSTOMER_MARTIAL_STATUS + " TEXT,"
                    + COLUMN_CUSTOMER_RELIGION + " TEXT,"
                    + COLUMN_CUSTOMER_PHONE1 + " TEXT,"
                    + COLUMN_CUSTOMER_PHONE2 + " TEXT,"
                    + COLUMN_CUSTOMER_ADRESS + " TEXT,"
                    + COLUMN_CUSTOMER_PREF + " TEXT,"
                    + COLUMN_CUSTOMER_EMAIL + " TEXT,"
                    + COLUMN_CUSTOMER_AVATAR + " TEXT,"
                    + COLUMN_CUSTOMER_CREATE_AT + " TEXT,"
                    + COLUMN_CUSTOMER_UPDATE_AT + " TEXT,"
                    + COLUMN_CUSTOMER_SPECIALITY_ID + " INTEGER,"
                    + COLUMN_CUSTOMER_TYPE_ID + " INTEGER,"
                    + COLUMN_CUSTOMER_STATUS_ID + " INTEGER,"
                    + COLUMN_CUSTOMER_AREAS + " TEXT,"
                    + COLUMN_CUSTOMER_CENTER + " TEXT,"
                    + COLUMN_CUSTOMER_TYPE + " TEXT,"
                    + COLUMN_CUSTOMER_STATUS + " TEXT,"
                    + COLUMN_CUSTOMER_SPECIALITY + " TEXT"
                    + ")";




    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Offlinedata";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // =============================HERITED METHODS=========================//

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_REPORT);
        db.execSQL(CREATE_TABLE_CLIENT);
        db.execSQL(CREATE_TABLE_CUSTOMER);
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_DAILY_REPORT_START);
        db.execSQL(CREATE_TABLE_DAILY_REPORT_START_RESPONSE);
        db.execSQL(CREATE_TABLE_DAILY_REPORT_END);
        db.execSQL(CREATE_TABLE_DAILY_REPORT_END_RESPONSE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_REPORT_START);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_REPORT_START_RESPONSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_REPORT_END);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_REPORT_END_RESPONSE);

        onCreate(db);
    }

    // ==============================================================================//


    //=============================================METHODS TO INTERACTE WITH THE DB=============================================//


    //======================save start report==================//

    public Boolean saveToDBDailyReportStart(DailyReportStart dailyReportStart) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DAILY_REPORT_START_USER_ID, dailyReportStart.getUserId());
        values.put(COLUMN_DAILY_REPORT_START_CUSTOMER_ID, dailyReportStart.getCustomerId());
        values.put(COLUMN_DAILY_REPORT_START_TIME, dailyReportStart.getStartTime());

        try {
            db.insert(TABLE_DAILY_REPORT_START, null, values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // DAILY REPORT START RESPONSE
    public Boolean saveToDBDailyReportStartResponse(DailyReportStartResponse dailyReportStartResponse) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DAILY_REPORT_START_RESPONSE_ID, dailyReportStartResponse.getId());
        values.put(COLUMN_DAILY_REPORT_START_RESPONSE_USER_ID, dailyReportStartResponse.getUserId());
        values.put(COLUMN_DAILY_REPORT_START_CUSTOMER_ID, dailyReportStartResponse.getCustomerId());
        values.put(COLUMN_DAILY_REPORT_START_RESPONSE_START_TIME, dailyReportStartResponse.getStartTime());
        values.put(COLUMN_DAILY_REPORT_START_RESPONSE_DAY, dailyReportStartResponse.getDay());
        values.put(COLUMN_DAILY_REPORT_START_RESPONSE_CREATEDAT, dailyReportStartResponse.getCreatedAt());
        values.put(COLUMN_DAILY_REPORT_START_RESPONSE_UPDATEAT, dailyReportStartResponse.getUpdatedAt());

        try {
            db.insert(TABLE_DAILY_REPORT_START_RESPONSE, null, values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //=====================save end report=====================//

    public Boolean saveToDBDailyReportEnd(DailyReportEnd dailyReportEnd) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DAILY_REPORT_END_GOAL, dailyReportEnd.getGoal());
        values.put(COLUMN_DAILY_REPORT_END_PRESCRIPTION, dailyReportEnd.getPrescription());
        values.put(COLUMN_DAILY_REPORT_END_CENTER_ID, dailyReportEnd.getCenterId());
        values.put(COLUMN_DAILY_REPORT_END_CUSTOMER_ID, dailyReportEnd.getCustomerId());
        values.put(COLUMN_DAILY_REPORT_END_PROMISES, dailyReportEnd.getPromise());
        values.put(COLUMN_DAILY_REPORT_END_TIME, dailyReportEnd.getEndTime());

        try {
            db.insert(TABLE_DAILY_REPORT_END, null, values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // DAILY REPORT END RESPONSE

    public Boolean saveToDBDailyReportEndResponse(DailyReportEndResponse dailyReportEndResponse) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(COLUMN_DAILY_REPORT_END_RESPONSE_ID, dailyReportEndResponse.getId());
        values.put(COLUMN_DAILY_REPORT_END_RESPONSE_DAY, dailyReportEndResponse.getDay());
        values.put(COLUMN_DAILY_REPORT_END_RESPONSE_GOAL, dailyReportEndResponse.getGoal());
        values.put(COLUMN_DAILY_REPORT_END_RESPONSE_PROMISES, dailyReportEndResponse.getPromise());
        values.put(COLUMN_DAILY_REPORT_END_RESPONSE_PRESCRIPTION, dailyReportEndResponse.getPrescription());
        values.put(COLUMN_DAILY_REPORT_END_RESPONSE_START_TIME, dailyReportEndResponse.getStartTime());
        values.put(COLUMN_DAILY_REPORT_END_RESPONSE_END_TIME, dailyReportEndResponse.getEndTime());
        values.put(COLUMN_DAILY_REPORT_END_RESPONSE_CREATE_AT, dailyReportEndResponse.getCreatedAt());
        values.put(COLUMN_DAILY_REPORT_END_RESPONSE_UPDATE_AT, dailyReportEndResponse.getUpdatedAt());
        values.put(COLUMN_DAILY_REPORT_END_RESPONSE_USER_ID, dailyReportEndResponse.getUserId());
        values.put(COLUMN_DAILY_REPORT_END_RESPONSE_CENTER_ID, dailyReportEndResponse.getCenterId());
        values.put(COLUMN_DAILY_REPORT_END_RESPONSE_NETWORK_ID, dailyReportEndResponse.getNetworkId().toString());
        values.put(COLUMN_DAILY_REPORT_END_RESPONSE_HEBDO_DELEGATE_ID, dailyReportEndResponse.getHebdoDelegateId().toString());
        values.put(COLUMN_DAILY_REPORT_END_RESPONSE_CUSTOMER_ID, dailyReportEndResponse.getCustomerId());
        values.put(COLUMN_DAILY_REPORT_END_RESPONSE_STATUS, dailyReportEndResponse.getStatus());
        values.put(COLUMN_DAILY_REPORT_END_RESPONSE_VIEWED, dailyReportEndResponse.getViewed());

        try {
            db.beginTransaction();
            db.insert(TABLE_DAILY_REPORT_END_RESPONSE, null, values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    //==========================SAVE TO DB REPORT========================//

    public long saveToDBReport(DailyReportNotSent dailyReportNotSent) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_REPORT_ID, dailyReportNotSent.getId());
        values.put(COLUMN_REPORT_START, dailyReportNotSent.getDailyReportStart());
        values.put(COLUMN_REPORT_END, dailyReportNotSent.getDailyReportEnd());
        values.put(COLUMN_REPORT_IS_START_REQUEST_SENT, dailyReportNotSent.getStartRequestSent());
        values.put(COLUMN_REPORT_IS_END_REQUEST_SENT, dailyReportNotSent.getEndRequestSent());
        values.put(COLUMN_REPORT, dailyReportNotSent.getReport());
        values.put(COLUMN_REPORT_CREATION_DATE, java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
        values.put(COLUMN_REPORT_STATUS, dailyReportNotSent.getStatus());

        return db.insert(TABLE_REPORT, null, values);
    }


    //===================get Not Sent report================================//

    public List<DailyReportNotSent> getOnlyNotSentReport(Boolean status) {

        List<DailyReportNotSent> dailyReportNotSentsList = new ArrayList<DailyReportNotSent>();
        String selectQuery = "SELECT  * FROM " + TABLE_REPORT + " WHERE " + COLUMN_REPORT_STATUS + " = " + status;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                DailyReportNotSent dailyReportNotSent = new DailyReportNotSent();
                dailyReportNotSent.setId(c.getInt((c.getColumnIndex(COLUMN_REPORT_ID))));
                dailyReportNotSent.setDailyReportStart(c.getString(c.getColumnIndex(COLUMN_REPORT_START)));
                dailyReportNotSent.setDailyReportEnd(c.getString(c.getColumnIndex(COLUMN_REPORT_END)));
                dailyReportNotSent.setStartRequestSent(c.getInt(c.getColumnIndex(COLUMN_REPORT_IS_START_REQUEST_SENT)) > 0);
                dailyReportNotSent.setEndRequestSent(c.getInt(c.getColumnIndex(COLUMN_REPORT_IS_END_REQUEST_SENT)) > 0);
                dailyReportNotSent.setReport((c.getString(c.getColumnIndex(COLUMN_REPORT))));
                dailyReportNotSent.setCreationDate(c.getString(c.getColumnIndex(COLUMN_REPORT_CREATION_DATE)));
                dailyReportNotSent.setStatus(c.getInt(c.getColumnIndex(COLUMN_REPORT_STATUS)) > 0);
                dailyReportNotSentsList.add(dailyReportNotSent);
            } while (c.moveToNext());
        }
        return dailyReportNotSentsList;

    }

    //===================get all report======================//

    public List<DailyReportNotSent> getAllReport() {

        List<DailyReportNotSent> dailyReportNotSentsList = new ArrayList<DailyReportNotSent>();
        String selectQuery = "SELECT  * FROM " + TABLE_REPORT;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                DailyReportNotSent dailyReportNotSent = new DailyReportNotSent();
                dailyReportNotSent.setId(c.getInt((c.getColumnIndex(COLUMN_REPORT_ID))));
                dailyReportNotSent.setDailyReportStart(c.getString(c.getColumnIndex(COLUMN_REPORT_START)));
                dailyReportNotSent.setDailyReportStart(c.getString(c.getColumnIndex(COLUMN_REPORT_END)));
                dailyReportNotSent.setStartRequestSent(c.getInt(c.getColumnIndex(COLUMN_REPORT_IS_START_REQUEST_SENT)) > 0);
                dailyReportNotSent.setStartRequestSent(c.getInt(c.getColumnIndex(COLUMN_REPORT_IS_END_REQUEST_SENT)) > 0);
                dailyReportNotSent.setReport((c.getString(c.getColumnIndex(COLUMN_REPORT))));
                dailyReportNotSent.setCreationDate(c.getString(c.getColumnIndex(COLUMN_REPORT_CREATION_DATE)));
                dailyReportNotSent.setStatus(c.getInt(c.getColumnIndex(COLUMN_REPORT_STATUS)) > 0);
                dailyReportNotSentsList.add(dailyReportNotSent);
            } while (c.moveToNext());
        }
        return dailyReportNotSentsList;

    }

    //========================update status of report============================//

    public int updateStatusOfReport(DailyReportNotSent dailyReportNotSent) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_REPORT_STATUS, dailyReportNotSent.getStatus());

        return db.update(TABLE_REPORT, values, COLUMN_REPORT_ID + " = ?",
                new String[]{String.valueOf(dailyReportNotSent.getId())});
    }

    //=======================delete One Report==================================//

    public void deleteOneReport(long reportID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REPORT, COLUMN_REPORT_ID + " = ?",
                new String[]{String.valueOf(reportID)});
    }

    //=======================delete All REPORT==================================//


    //===========================================CLIENT OPERATION==============================================//


    //===========================CUSTOMER=================================//

    public Boolean saveToDBCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CUSTOMER_ID, customer.getId());
        values.put(COLUMN_CUSTOMER_LAST_NAME, customer.getLastname());
        values.put(COLUMN_CUSTOMER_FIRST_NAME, customer.getFirstname());
        values.put(COLUMN_CUSTOMER_SEX, customer.getSex());
        values.put(COLUMN_CUSTOMER_TEL, customer.getTel());
        values.put(COLUMN_CUSTOMER_BIRTHDAY, customer.getBirthday());
        values.put(COLUMN_CUSTOMER_NATIONALITY, customer.getNationality());
        values.put(COLUMN_CUSTOMER_MARTIAL_STATUS, customer.getMaritalStatus());
        values.put(COLUMN_CUSTOMER_RELIGION, customer.getReligion());
        values.put(COLUMN_CUSTOMER_PHONE1, customer.getPhoneNumber1());
        values.put(COLUMN_CUSTOMER_PHONE2, customer.getPhoneNumber2());
        values.put(COLUMN_CUSTOMER_ADRESS, customer.getAddress());
        values.put(COLUMN_CUSTOMER_PREF, customer.getPreference());
        values.put(COLUMN_CUSTOMER_EMAIL, customer.getEmail());
        values.put(COLUMN_CUSTOMER_AVATAR, customer.getAvatar());
        values.put(COLUMN_CUSTOMER_CREATE_AT, customer.getCreatedAt());
        values.put(COLUMN_CUSTOMER_UPDATE_AT, customer.getUpdatedAt());
        values.put(COLUMN_CUSTOMER_SPECIALITY_ID, customer.getSpecialityId());
        values.put(COLUMN_CUSTOMER_TYPE_ID, customer.getCustomerTypeId());
        values.put(COLUMN_CUSTOMER_STATUS_ID, customer.getCustomerStatusId());
        values.put(COLUMN_CUSTOMER_AREAS, customer.getAreas().toString());
        values.put(COLUMN_CUSTOMER_CENTER, customer.getCenters().toString());
        values.put(COLUMN_CUSTOMER_TYPE, customer.getCustomerType().toString());
        values.put(COLUMN_CUSTOMER_STATUS, customer.getCustomerStatus().toString());
        values.put(COLUMN_CUSTOMER_SPECIALITY, customer.getSpeciality().toString());
        try {
            db.beginTransaction();
            db.insert(TABLE_CUSTOMER, null, values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //===========================save Client========================//

    public long saveToDBClient(ClientsList clientsList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_CLIENT, clientsList.getClient());
        values.put(COLUMN_DATE_PROSPECTION, clientsList.getDateProspection());

        return db.insert(TABLE_CLIENT, null, values);
    }

    //===========================Get All Client======================//

    public List<ClientsList> getAllClient() {

        List<ClientsList> clientsLists = new ArrayList<ClientsList>();
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                ClientsList clientsList = new ClientsList();
                clientsList.setId(c.getInt((c.getColumnIndex(COLUMN_CLIENT_ID))));
                clientsList.setClient((c.getString(c.getColumnIndex(COLUMN_CLIENT))));
                clientsList.setDateProspection(c.getString(c.getColumnIndex(COLUMN_DATE_PROSPECTION)));
            } while (c.moveToNext());
        }
        return clientsLists;

    }

    //=======================delete a client from db======================//

    public void deleteClient(long clientId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REPORT, COLUMN_CLIENT_ID + " = ?",
                new String[]{String.valueOf(clientId)});

    }

    //=======================================PRODUCT OPERATION==================================================//

    //==============================Save TO DB Product=====================================//

    public long saveToDBUserProduct(UserProduct userProduct) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_PRODUCT, userProduct.getProduct());

        return db.insert(TABLE_CLIENT, null, values);
    }

    //========================= GET ALL USER PRODUCT FROM DB==========================//

    public List<UserProduct> getAllUserProduct() {
        List<UserProduct> userProductList = new ArrayList<UserProduct>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {
                UserProduct uP = new UserProduct();
                uP.setId(c.getInt(c.getColumnIndex(COLUMN_PRODUCT_ID)));
                uP.setProduct(c.getString(c.getColumnIndex(COLUMN_PRODUCT)));
            } while (c.moveToNext());
        }
        return userProductList;
    }

    //===========================Delete User product=================================//

    public void deleteProduct(long productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_PRODUCT, COLUMN_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(productId)});
    }


    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


}
