package com.example.administrator.androidtest.Test.MainTest.RoomTest;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoomUserDao {
    // 设置主键冲突之后的策略，这里选择直接覆盖原数据
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(RoomUserEntity userEntity);

    // 删除某条数据
    @Delete
    void deleteUser(RoomUserEntity userEntity);

    // 更新某条数据
    @Update
    void updateUser(RoomUserEntity userEntity);

    // 根据id查找数据
    @Query("select * from room_user_entity where id=:id")
    List<RoomUserEntity> findUser(int id);
}
