package com.example.testsmartapps.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "webdata_database")
data class WebDataModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val webviewDate: String
)