package com.example.administrator.androidtest.Test.MainTest.RoomTest;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "room_user_entity")
public class RoomUserEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @Ignore
    private String authorAlisa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorAlisa() {
        return authorAlisa;
    }

    public void setAuthorAlisa(String authorAlisa) {
        this.authorAlisa = authorAlisa;
    }
}
