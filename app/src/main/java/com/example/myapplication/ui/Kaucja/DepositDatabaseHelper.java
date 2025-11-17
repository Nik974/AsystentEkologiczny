package com.example.myapplication.ui.Kaucja;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa pomocnicza do zarządzania bazą danych SQLite dla kaucji.
 * Odpowiada za tworzenie, aktualizowanie i wykonywanie operacji CRUD na bazie danych.
 */
public class DepositDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "deposits.db";
    private static final int DATABASE_VERSION = 1;

    // Nazwy tabeli i kolumn
    public static final String TABLE_DEPOSITS = "deposits";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PACKAGING_TYPE = "packaging_type";
    public static final String COLUMN_DEPOSIT_VALUE = "deposit_value";
    public static final String COLUMN_BARCODE = "barcode";

    public DepositDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_DEPOSITS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PACKAGING_TYPE + " TEXT,"
                + COLUMN_DEPOSIT_VALUE + " REAL,"
                + COLUMN_BARCODE + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPOSITS);
        onCreate(db);
    }

    public boolean addDeposit(Deposit deposit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PACKAGING_TYPE, deposit.getPackagingType());
        values.put(COLUMN_DEPOSIT_VALUE, deposit.getDepositValue());
        values.put(COLUMN_BARCODE, deposit.getBarcode());
        long result = db.insert(TABLE_DEPOSITS, null, values);
        db.close();
        return result != -1;
    }

    public int updateDeposit(Deposit deposit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PACKAGING_TYPE, deposit.getPackagingType());
        values.put(COLUMN_DEPOSIT_VALUE, deposit.getDepositValue());
        values.put(COLUMN_BARCODE, deposit.getBarcode());
        int rows = db.update(TABLE_DEPOSITS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(deposit.getId())});
        db.close();
        return rows;
    }

    public void deleteDeposit(int depositId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DEPOSITS, COLUMN_ID + " = ?", new String[]{String.valueOf(depositId)});
        db.close();
    }

    public List<Deposit> getAllDeposits() {
        List<Deposit> depositList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DEPOSITS, null);
        if (cursor.moveToFirst()) {
            do {
                Deposit deposit = new Deposit();
                deposit.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                deposit.setPackagingType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PACKAGING_TYPE)));
                deposit.setDepositValue(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DEPOSIT_VALUE)));
                deposit.setBarcode(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BARCODE)));
                depositList.add(deposit);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return depositList;
    }
}