package com.example.administrator.androidtest.demo.RoomTest;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface RoomHomeDao {
    // 设置主键冲突之后的策略，这里选择直接覆盖原数据
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHome(RoomHomeEntity homeEntity);
}
