package com.example.myapplication.ui.Kaucja;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Klasa pomocnicza do zarządzania bazą danych SQLite dla kaucji.
 * Odpowiada za tworzenie, aktualizowanie i wykonywanie operacji CRUD na bazie danych.
 */
public class DepositDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "deposits.db";
    private static final int DATABASE_VERSION = 2; // Zwiększona wersja bazy danych

    // Nazwy tabeli i kolumn
    public static final String TABLE_DEPOSITS = "deposits";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PACKAGING_TYPE = "packaging_type";
    public static final String COLUMN_DEPOSIT_VALUE = "deposit_value";
    public static final String COLUMN_BARCODE = "barcode";
    public static final String COLUMN_IS_RETURNED = "is_returned";
    public static final String COLUMN_RETURN_DATE = "return_date";

    public DepositDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_DEPOSITS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PACKAGING_TYPE + " TEXT,"
                + COLUMN_DEPOSIT_VALUE + " REAL,"
                + COLUMN_BARCODE + " TEXT,"
                + COLUMN_IS_RETURNED + " INTEGER DEFAULT 0,"
                + COLUMN_RETURN_DATE + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_DEPOSITS + " ADD COLUMN " + COLUMN_IS_RETURNED + " INTEGER DEFAULT 0");
            db.execSQL("ALTER TABLE " + TABLE_DEPOSITS + " ADD COLUMN " + COLUMN_RETURN_DATE + " TEXT");
        }
    }

    public boolean addDeposit(Deposit deposit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PACKAGING_TYPE, deposit.getPackagingType());
        values.put(COLUMN_DEPOSIT_VALUE, deposit.getDepositValue());
        values.put(COLUMN_BARCODE, deposit.getBarcode());
        values.put(COLUMN_IS_RETURNED, deposit.isReturned() ? 1 : 0);
        values.put(COLUMN_RETURN_DATE, deposit.getReturnDate());
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
        values.put(COLUMN_IS_RETURNED, deposit.isReturned() ? 1 : 0);
        values.put(COLUMN_RETURN_DATE, deposit.getReturnDate());
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
                deposit.setReturned(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_RETURNED)) == 1);
                deposit.setReturnDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RETURN_DATE)));
                depositList.add(deposit);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return depositList;
    }

    public double getReturnedDepositsValueForCurrentMonth() {
        SQLiteDatabase db = this.getReadableDatabase();
        double totalValue = 0;

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        String monthPattern = String.format(Locale.getDefault(), "%d-%02d-%%", year, month);

        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + COLUMN_DEPOSIT_VALUE + ") FROM " + TABLE_DEPOSITS +
                " WHERE " + COLUMN_IS_RETURNED + " = 1 AND " + COLUMN_RETURN_DATE + " LIKE ?",
                new String[]{monthPattern}
        );

        if (cursor.moveToFirst()) {
            totalValue = cursor.getDouble(0);
        }
        cursor.close();
        db.close();
        return totalValue;
    }
}