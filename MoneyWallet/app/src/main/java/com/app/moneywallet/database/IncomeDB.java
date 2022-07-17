package com.app.moneywallet.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.moneywallet.IncomeInfo;

import java.io.IOException;
import java.util.ArrayList;

public class IncomeDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "incomeDB";
    public static final String TABLE_NAME = "IncomeInfo";

    public IncomeDB(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDB) {
        try {
            sqLiteDB.execSQL(
                    "CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY, amount INT, category text)"
            );
        } catch (SQLiteException e) {
            try {
                throw new IOException(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDB, int i, int i1) {
        sqLiteDB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDB);
    }

    public void insert(int amount, String category) {
        SQLiteDatabase sqLiteDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount", amount);
        contentValues.put("category", category);
        sqLiteDB.replace(TABLE_NAME, null, contentValues);
    }

    @SuppressLint("Range")
    public ArrayList<IncomeInfo> getAllIncome() {
        SQLiteDatabase sqLiteDB = this.getReadableDatabase();
        ArrayList<IncomeInfo> array_list = new ArrayList<>();
        Cursor res = sqLiteDB.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToNext();
        while (!res.isAfterLast()) {
            array_list.add(new IncomeInfo(
                    res.getInt(res.getColumnIndex("amount")),
                    res.getString(res.getColumnIndex("category"))));
            res.moveToNext();
        }
        return array_list;
    }

    public void update(String catName, int amount) {
        SQLiteDatabase sqLiteDB = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount", amount);

        sqLiteDB.update(TABLE_NAME, contentValues, "category=?", new String[]{catName});
        sqLiteDB.close();
    }

    @SuppressLint("Range")
    public int totalIncome() {
        SQLiteDatabase sqLiteDB = this.getReadableDatabase();
        Cursor cursor = sqLiteDB.rawQuery("SELECT SUM(" + "amount" + ") as Total FROM " + TABLE_NAME, null);
        int total = 0;
        if (cursor.moveToFirst()) {
            total += cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return total;
    }

    @SuppressLint("Range")
    public int catIncome(String catName) {
        SQLiteDatabase sqLiteDB = this.getReadableDatabase();
        String query = " SELECT amount as Money FROM " + TABLE_NAME + " WHERE " + "category" + "=? ";
        Cursor cursor = sqLiteDB.rawQuery(query, new String[]{catName});
        int mIncome = 0;
        if (cursor.moveToFirst()) {
            mIncome += cursor.getInt(cursor.getColumnIndex("Money"));
        }
        return mIncome;
    }

    public boolean isExist(String CatName) {
        SQLiteDatabase sqLiteDB = this.getReadableDatabase();
        String query = " SELECT  * FROM " + TABLE_NAME + " WHERE " + "category" + "=? ";
        Cursor cursor = sqLiteDB.rawQuery(query, new String[]{CatName});

        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }

        return true;
    }
}