package com.example.administrator.androidtest.Test.MainTest.RoomTest;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class RoomDBHelper {

    private final Migration addAgeMigration = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE user_entity add column age INTEGER NOT NULL DEFAULT 0");
        }
    };

    private final Migration addHomeEntityMigration = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS room_home_entity (id INTEGER NOT NULL, name TEXT NOT NULL, PRIMARY KEY(id))");
        }
    };

    public void init(Context context) {
        RoomDB roomDB = Room.databaseBuilder(context, RoomDB.class, "room_db")
                .addMigrations(addAgeMigration)
                .addMigrations(addHomeEntityMigration)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                    }
                })
                .build();
        roomDB.getUserDao();
    }
}
