package com.example.testsmartapps.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [WebDataModel::class], version = 1, exportSchema = false)
abstract class WebDataDatabase: RoomDatabase() {

    abstract fun webDataDao(): WebDataDao

    companion object {
        @Volatile
        private var INSTANCE: WebDataDatabase? = null

        fun getDatabase(context: Context): WebDataDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WebDataDatabase::class.java,
                    "webdata_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}