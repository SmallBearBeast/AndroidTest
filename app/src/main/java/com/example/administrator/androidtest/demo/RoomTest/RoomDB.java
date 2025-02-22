package com.example.administrator.androidtest.demo.RoomTest;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {RoomUserEntity.class}, version = 1, exportSchema = false)
abstract class RoomDB extends RoomDatabase {
    abstract RoomUserDao getUserDao();

    private Migration addAgeMigration = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE user_entity add column age INTEGER NOT NULL DEFAULT 0");
        }
    };

}
