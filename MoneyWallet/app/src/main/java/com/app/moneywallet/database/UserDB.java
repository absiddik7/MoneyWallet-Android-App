package com.app.moneywallet.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;

public class UserDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "userDB";
    public static final String TABLE_NAME = "UserInfo";

    public UserDB(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDB) {
        try {
            sqLiteDB.execSQL(
                    "CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY, name text)"
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

    public void insert(String userName) {
        SQLiteDatabase sqLiteDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", userName);
        sqLiteDB.replace(TABLE_NAME, null, contentValues);
    }

    @SuppressLint("Range")
    public String getUserName() {
        SQLiteDatabase sqLiteDB = this.getReadableDatabase();
        Cursor cursor = sqLiteDB.rawQuery("select * from " + TABLE_NAME, null);
        cursor.moveToNext();
        String name = cursor.getString( cursor.getColumnIndex("name") );
        //cursor.moveToNext();

        return name;
    }

    public void update(String userName) {
        SQLiteDatabase sqLiteDB = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", userName);

        sqLiteDB.update(TABLE_NAME, contentValues, "id=?", new String[] {"1"});
        sqLiteDB.close();
    }

    public boolean isExist() {
        SQLiteDatabase sqLiteDB = this.getReadableDatabase();
        Cursor mCursor = sqLiteDB.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        boolean rowExists;

        if (mCursor.moveToFirst())
        {
            rowExists = true;

        } else
        {
            // I AM EMPTY
            rowExists = false;
        }

        return rowExists;
    }

}
