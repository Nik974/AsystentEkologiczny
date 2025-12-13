package com.example.myapplication.ui.Produkty;

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
 * Klasa pomocnicza do zarządzania bazą danych SQLite dla produktów.
 * Odpowiada za tworzenie, aktualizowanie i wykonywanie operacji CRUD na bazie danych.
 */
public class ProductDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_EXPIRY = "expiry_date";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_SHOP = "shop";
    public static final String COLUMN_PURCHASE_DATE = "purchase_date";

    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PRICE + " REAL,"
                + COLUMN_EXPIRY + " TEXT,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_SHOP + " TEXT,"
                + COLUMN_PURCHASE_DATE + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public boolean addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_EXPIRY, product.getExpiryDate());
        values.put(COLUMN_CATEGORY, product.getCategory());
        values.put(COLUMN_DESCRIPTION, product.getDescription());
        values.put(COLUMN_SHOP, product.getShop());
        values.put(COLUMN_PURCHASE_DATE, product.getPurchaseDate());
        long result = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return result != -1;
    }

    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_EXPIRY, product.getExpiryDate());
        values.put(COLUMN_CATEGORY, product.getCategory());
        values.put(COLUMN_DESCRIPTION, product.getDescription());
        values.put(COLUMN_SHOP, product.getShop());
        values.put(COLUMN_PURCHASE_DATE, product.getPurchaseDate());
        int rowsAffected = db.update(TABLE_PRODUCTS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(product.getId())});
        db.close();
        return rowsAffected;
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                product.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                product.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));
                product.setExpiryDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPIRY)));
                product.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                product.setShop(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SHOP)));
                product.setPurchaseDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PURCHASE_DATE)));
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }

    public double getExpensesForCurrentMonth() {
        SQLiteDatabase db = this.getReadableDatabase();
        double totalExpenses = 0;

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        String monthPattern = String.format(Locale.getDefault(), "%d-%02d-%%", year, month);

        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + COLUMN_PRICE + ") FROM " + TABLE_PRODUCTS +
                " WHERE " + COLUMN_PURCHASE_DATE + " LIKE ?",
                new String[]{monthPattern}
        );

        if (cursor.moveToFirst()) {
            totalExpenses = cursor.getDouble(0);
        }
        cursor.close();
        db.close();
        return totalExpenses;
    }

    public void deleteProduct(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?", new String[]{String.valueOf(productId)});
        db.close();
    }
}