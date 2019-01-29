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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    // Table Names
    public static final String TABLE_REPORT = "dailyReport";
    public static final String TABLE_CLIENT = "ClientList";
    public static final String TABLE_PRODUCT = "userProduct";
    // REPORT Table - column names
    public static final String COLUMN_REPORT_ID = "id";
    public static final String COLUMN_REPORT = "report";
    public static final String COLUMN_REPORT_CREATION_DATE = "creationDate";
    public static final String COLUMN_REPORT_STATUS = "reportStatus";
    // CLIENT Table - column names
    public static final String COLUMN_CLIENT_ID = "id";
    public static final String COLUMN_CLIENT = "client";
    public static final String COLUMN_DATE_PROSPECTION = "dateProspection";
    // USER_PRODUCT Table - column names
    public static final String COLUMN_PRODUCT_ID = "id";
    public static final String COLUMN_PRODUCT = "product";
    // CREATION OF REPORT TABLE
    public static final String CREATE_TABLE_REPORT =
            "CREATE TABLE " + TABLE_REPORT + "("
                    + COLUMN_REPORT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_REPORT + " TEXT,"
                    + COLUMN_REPORT_CREATION_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + COLUMN_REPORT_STATUS + " BOOLEAN"
                    + ")";
    public static final String CREATE_TABLE_CLIENT =
            "CREATE TABLE " + TABLE_CLIENT + "("
                    + COLUMN_CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CLIENT + " TEXT,"
                    + COLUMN_DATE_PROSPECTION + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";
    public static final String CREATE_TABLE_PRODUCT =
            "CREATE TABLE " + TABLE_PRODUCT + "("
                    + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PRODUCT + " TEXT"
                    + ")";


    //TABLES CREATIONS
    // Logcat tag
    private static final String LOG = "DatabaseHelper";


    // CREATION OF CLIENT TABLE
    // Database Version
    private static final int DATABASE_VERSION = 1;


    // CREATION OF PRODUCT TABLE
    // Database Name
    private static final String DATABASE_NAME = "Offlinedata";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_REPORT);
        db.execSQL(CREATE_TABLE_CLIENT);
        db.execSQL(CREATE_TABLE_PRODUCT);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT);

        onCreate(db);
    }


    /*
     * REPORT OPERATION
     *
     */

    // Create Report

    public long createReport(DailyReportNotSent dailyReportNotSent) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_REPORT, dailyReportNotSent.getReport());
        values.put(COLUMN_REPORT_CREATION_DATE, java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
        values.put(COLUMN_REPORT_STATUS, dailyReportNotSent.getStatus());

        return db.insert(TABLE_REPORT, null, values);
    }

    // get Not Sent report
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
                dailyReportNotSent.setReport((c.getString(c.getColumnIndex(COLUMN_REPORT))));
                dailyReportNotSent.setCreationDate(c.getString(c.getColumnIndex(COLUMN_REPORT_CREATION_DATE)));
                dailyReportNotSent.setStatus(c.getInt(c.getColumnIndex(COLUMN_REPORT_STATUS)) > 0);

                dailyReportNotSentsList.add(dailyReportNotSent);
            } while (c.moveToNext());
        }
        return dailyReportNotSentsList;

    }

    // get all report
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
                dailyReportNotSent.setReport((c.getString(c.getColumnIndex(COLUMN_REPORT))));
                dailyReportNotSent.setCreationDate(c.getString(c.getColumnIndex(COLUMN_REPORT_CREATION_DATE)));
                dailyReportNotSent.setStatus(c.getInt(c.getColumnIndex(COLUMN_REPORT_STATUS)) > 0);
                dailyReportNotSentsList.add(dailyReportNotSent);
            } while (c.moveToNext());
        }
        return dailyReportNotSentsList;

    }

    // update status of report

    public int updateStatusOfReport(DailyReportNotSent dailyReportNotSent) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_REPORT_STATUS, dailyReportNotSent.getStatus());

        return db.update(TABLE_REPORT, values, COLUMN_REPORT_ID + " = ?",
                new String[]{String.valueOf(dailyReportNotSent.getId())});
    }

    //delete One Report
    public void deleteOneReport(long reportID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REPORT, COLUMN_REPORT_ID + " = ?",
                new String[]{String.valueOf(reportID)});
    }

    // delete All REPORT


    /*
     * CLIENT OPERATION
     */
    // Create Client
    public long createClient(ClientsList clientsList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_CLIENT, clientsList.getClient());
        values.put(COLUMN_DATE_PROSPECTION, clientsList.getDateProspection());

        return db.insert(TABLE_CLIENT, null, values);
    }

    // Get All Client
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

    // delete a client from db

    public void deleteClient(long clientId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REPORT, COLUMN_CLIENT_ID + " = ?",
                new String[]{String.valueOf(clientId)});

    }

    //

    /*
     * PRODUCT OPERATION
     */

    //Create Product

    public long createUserProduct(UserProduct userProduct) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_PRODUCT, userProduct.getProduct());

        return db.insert(TABLE_CLIENT, null, values);
    }

    // Get All User Product

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

    // Delete User product

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
