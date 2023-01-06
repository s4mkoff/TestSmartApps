package com.example.testsmartapps

import android.app.Application
import com.example.testsmartapps.data.database.WebDataDatabase

class BaseApplication: Application() {
    val webdatadatabase: WebDataDatabase by lazy { WebDataDatabase.getDatabase(this)}
}