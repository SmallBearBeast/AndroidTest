package com.example.administrator.androidtest.Test.RoomTest;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "room_test")
public class RoomTestData_2 {
    @PrimaryKey
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "sex")
    private int sex;

    @ColumnInfo(name = "ignore_text")
    private String ignoreText;

    private String description;
}
