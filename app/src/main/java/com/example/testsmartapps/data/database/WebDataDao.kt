package com.example.testsmartapps.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WebDataDao {
    @Query("SELECT * FROM webdata_database")
    suspend fun getWebData(): List<WebDataModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebData(webData: WebDataModel)

    @Update
    suspend fun updateWebdata(webData: WebDataModel)
}