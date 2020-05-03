package me.aflak.roomexample.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import me.aflak.roomexample.entity.User;

/**
 * Created by root on 17/08/17.
 */

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> all();

    @Query("SELECT COUNT(*) from user")
    int count();

    @Insert
    void insert(User... users);
}
