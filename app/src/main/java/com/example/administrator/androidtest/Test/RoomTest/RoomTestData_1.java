package com.example.administrator.androidtest.Test.RoomTest;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "room_test")
public class RoomTestData_1 {
    @PrimaryKey
    private long id;
}
