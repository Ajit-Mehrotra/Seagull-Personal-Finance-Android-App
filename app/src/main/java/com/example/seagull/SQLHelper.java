package com.example.seagull;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashSet;

public class SQLHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "bank.db";
    public static final int DATABASE_VERSION = 4;

    // Table names
    private static final String TABLE_BANK = "bank";
    private static final String TABLE_REP = "rep";

    // attributes/cols for bank table
    private static final String COL_BANK_ID = "bid";
    private static final String COL_BANK_NAME = "bname";
    private static final String COL_BANK_WEBSITE = "website";

    // Column names for representative table
    private static final String COL_REP_ID = "rid";
    private static final String COL_REP_NAME = "rname";
    private static final String COL_REP_BANK_ID = "bank_id";
    private static final String COL_REP_EMAIL = "name";
    private static final String COL_REP_PHONE = "phone";

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATE BANK TABLE
        String bank_sql = "CREATE TABLE " + TABLE_BANK + "(" +
                COL_BANK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_BANK_NAME + " TEXT, " +
                COL_BANK_WEBSITE + " TEXT)";
        db.execSQL(bank_sql);
        //CREATE REP TABLE
        String createRepresentativeTableQuery = "CREATE TABLE " + TABLE_REP + "(" +
                COL_REP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_REP_NAME + " TEXT, " +
                COL_REP_BANK_ID + " INTEGER, " +
                COL_REP_EMAIL + " TEXT, " +
                COL_REP_PHONE + " INTEGER, " +
                "FOREIGN KEY (" + COL_REP_BANK_ID + ") REFERENCES " +
                TABLE_BANK + "(" + COL_BANK_ID + "))";
        db.execSQL(createRepresentativeTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion >= newVersion) return;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BANK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REP);
        onCreate(db);
    }

    // Method to add a new bank
    public void addBank(Bank bank) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BANK_NAME, bank.getName());
        values.put(COL_BANK_WEBSITE, bank.getWebsite());
        db.insert(TABLE_BANK, null, values);
        db.close();
    }

    // Method to add a new representative
    public void addRep(Rep rep) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_REP_NAME, rep.getName());
        values.put(COL_REP_BANK_ID, rep.getBankId());
        values.put(COL_REP_EMAIL, rep.getEmail());
        values.put(COL_REP_PHONE, rep.getPhone());
        db.insert(TABLE_REP, null, values);
        db.close();
    }

    public HashSet<String> getBanks(){
        SQLiteDatabase db = this.getWritableDatabase();
        HashSet<String> banks = new HashSet<>();
        String query = "SELECT " +
                TABLE_BANK + "." + COL_BANK_NAME +
                " FROM " + TABLE_BANK;

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String str = cursor.getString(cursor.getColumnIndexOrThrow(COL_BANK_NAME));
            banks.add(str);
        }
        ;
        db.close();
        return banks;
    }


    // Method to get bank and representative data using INNER JOIN
    public ArrayList<BankRep> getJoinData() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<BankRep> bankRepList = new ArrayList<>();

        String query = "SELECT " +
                TABLE_BANK + "." + COL_BANK_NAME + ", " +
                TABLE_BANK + "." + COL_BANK_WEBSITE +", " +
                TABLE_REP + "." + COL_REP_NAME +", " +
                TABLE_REP + "." + COL_REP_EMAIL +", " +
                TABLE_REP + "." + COL_REP_PHONE +
                " FROM " + TABLE_BANK +
                " INNER JOIN " + TABLE_REP +
                " ON " + TABLE_BANK + "." + COL_BANK_ID + " = " + TABLE_REP + "." + COL_REP_BANK_ID;

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            String bankName = cursor.getString(cursor.getColumnIndexOrThrow(COL_BANK_NAME));
            String bankWebsite = cursor.getString(cursor.getColumnIndexOrThrow(COL_BANK_WEBSITE));
            String repName = cursor.getString(cursor.getColumnIndexOrThrow(COL_REP_NAME));
            String repEmail = cursor.getString(cursor.getColumnIndexOrThrow(COL_REP_EMAIL));
            int repPhone = cursor.getInt(cursor.getColumnIndexOrThrow(COL_REP_PHONE));

            BankRep bankRep = new BankRep(bankName, bankWebsite, repName, repEmail, repPhone);
            bankRepList.add(bankRep);
        }

        cursor.close();
        db.close();

        return bankRepList;
    }
}
