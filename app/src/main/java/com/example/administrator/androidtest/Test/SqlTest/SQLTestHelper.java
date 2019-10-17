package com.example.administrator.androidtest.Test.SqlTest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLTestHelper extends SQLiteOpenHelper {
    public SQLTestHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTestTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static final String ID = "id";
    public static final String TEST = "test";
    public static final String NAME_1 = "name_1";
    public static final String NAME_2 = "name_2";
    public static final String NAME_3 = "name_3";
    public static final String NAME_4 = "name_4";
    public static final String NAME_5 = "name_5";
    public static final String NAME_6 = "name_6";
    public static final String NAME_7 = "name_7";
    public static final String NAME_8 = "name_8";
    public static final String NAME_9 = "name_9";

    private void createTestTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TEST + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME_1 + " TEXT NOT NULL,"
                + NAME_2 + " TEXT NOT NULL,"
                + NAME_3 + " TEXT NOT NULL,"
                + NAME_4 + " TEXT NOT NULL,"
                + NAME_5 + " TEXT NOT NULL,"
                + NAME_6 + " TEXT NOT NULL,"
                + NAME_7 + " TEXT NOT NULL,"
                + NAME_8 + " TEXT NOT NULL,"
                + NAME_9 + " TEXT NOT NULL" + ");");
    }
}
