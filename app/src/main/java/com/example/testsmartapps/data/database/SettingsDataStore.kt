package com.example.testsmartapps.data.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCES_NAME
)

class SettingsDataStore(context: Context) {
    private val LAST_URL = stringPreferencesKey("last_url")

    val preferenceFlow: Flow<String> = context.dataStore.data.catch {
        if (it is IOException) {
            it.printStackTrace()
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map{
            preferences ->
        preferences[LAST_URL] ?: "https://www.google.com/"
    }

    suspend fun saveUrlToPreferencesStore(lastUrl: String, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[LAST_URL] = lastUrl
        }
    }
}