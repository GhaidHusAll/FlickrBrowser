package com.example.flicker_ghh.roomDB

import androidx.room.*



@Dao
interface PhotoDao {

    @Insert
    suspend fun add(photo:PhotoRoom):Long

    @Delete
    suspend fun delete(photo:PhotoRoom):Int

    @Query("Select * from photo")
    fun fetch(): List<PhotoRoom>
}