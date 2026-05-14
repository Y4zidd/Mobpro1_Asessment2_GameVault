package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.database.CategoryDao
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.database.GameDao
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.datastore.SettingsDataStore
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.screen.main.MainViewModel
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.screen.detail.DetailViewModel

class ViewModelFactory(
    private val dao: GameDao,
    private val categoryDao: CategoryDao,
    private val dataStore: SettingsDataStore
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao, dataStore) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao, categoryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
