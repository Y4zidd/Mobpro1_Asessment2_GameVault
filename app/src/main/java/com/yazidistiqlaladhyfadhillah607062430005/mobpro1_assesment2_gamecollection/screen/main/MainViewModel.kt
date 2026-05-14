package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.database.GameDao
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.datastore.SettingsDataStore
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.model.Game
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val dao: GameDao,
    private val dataStore: SettingsDataStore
) : ViewModel() {

    val data: StateFlow<List<Game>> = dao.getAllGames().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val deletedData: StateFlow<List<Game>> = dao.getDeletedGames().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val layoutMode: StateFlow<Boolean> = dataStore.layoutFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    val themeMode: StateFlow<Int> = dataStore.themeFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    val themeColor: StateFlow<String> = dataStore.colorFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "Blue"
    )

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.OnLayoutToggle -> {
                viewModelScope.launch {
                    dataStore.saveLayout(action.isList)
                }
            }
            is MainAction.OnDeleteGame -> {
                viewModelScope.launch {
                    dao.moveToTrash(action.id)
                }
            }
            is MainAction.OnRestoreGame -> {
                viewModelScope.launch {
                    dao.restoreFromTrash(action.id)
                }
            }
            is MainAction.OnDeletePermanently -> {
                viewModelScope.launch {
                    dao.deletePermanentlyById(action.id)
                }
            }
            is MainAction.OnThemeChange -> {
                viewModelScope.launch {
                    dataStore.saveTheme(action.mode)
                }
            }
            is MainAction.OnColorChange -> {
                viewModelScope.launch {
                    dataStore.saveColor(action.color)
                }
            }
        }
    }
}
