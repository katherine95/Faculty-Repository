package com.example.facultyapp.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.facultyapp.data.model.User;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Insert(onConflict = REPLACE)
    void addUser(User user);

    @Query("SELECT * FROM User WHERE id = :id")
    User getUserById(String id);
}
