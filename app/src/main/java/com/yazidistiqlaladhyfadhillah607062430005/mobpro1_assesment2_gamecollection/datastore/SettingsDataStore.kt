package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATASTORE_NAME)

class SettingsDataStore(private val context: Context) {
    companion object {
        val IS_LIST_LAYOUT = booleanPreferencesKey("is_list_layout")
        val THEME_MODE = intPreferencesKey("theme_mode") // 0: System, 1: Light, 2: Dark
    }

    val layoutFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LIST_LAYOUT] ?: true
    }

    val themeFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[THEME_MODE] ?: 0
    }

    suspend fun saveLayout(isList: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LIST_LAYOUT] = isList
        }
    }

    suspend fun saveTheme(mode: Int) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE] = mode
        }
    }
}
