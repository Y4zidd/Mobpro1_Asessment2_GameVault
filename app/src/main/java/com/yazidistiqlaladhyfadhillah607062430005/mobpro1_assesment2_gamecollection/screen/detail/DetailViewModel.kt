package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.database.GameDao
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.model.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: GameDao) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun loadGame(id: Long) {
        if (id == -1L) return
        viewModelScope.launch {
            dao.getGameById(id)?.let { game ->
                _uiState.update {
                    it.copy(
                        id = game.id,
                        title = game.title,
                        platform = game.platform,
                        rating = game.rating.toFloat(),
                        playTime = game.playTime.toString(),
                        finished = game.finished,
                        imageUrl = game.imageUrl,
                        isEdit = true
                    )
                }
            }
        }
    }

    fun onAction(action: DetailAction) {
        when (action) {
            is DetailAction.OnTitleChange -> _uiState.update { it.copy(title = action.title) }
            is DetailAction.OnPlatformChange -> _uiState.update { it.copy(platform = action.platform) }
            is DetailAction.OnRatingChange -> _uiState.update { it.copy(rating = action.rating) }
            is DetailAction.OnPlayTimeChange -> _uiState.update { it.copy(playTime = action.playTime) }
            is DetailAction.OnFinishedChange -> _uiState.update { it.copy(finished = action.finished) }
            is DetailAction.OnImageUrlChange -> _uiState.update { it.copy(imageUrl = action.url) }
            DetailAction.OnSave -> saveGame()
            DetailAction.OnDelete -> deleteGame()
        }
    }

    private fun saveGame() {
        val state = _uiState.value
        if (state.title.isBlank() || state.platform.isBlank()) return

        val game = Game(
            id = if (state.isEdit) state.id else 0,
            title = state.title,
            platform = state.platform,
            rating = state.rating.toInt(),
            playTime = state.playTime.toIntOrNull() ?: 0,
            finished = state.finished,
            imageUrl = state.imageUrl
        )

        viewModelScope.launch {
            if (state.isEdit) dao.update(game) else dao.insert(game)
        }
    }

    private fun deleteGame() {
        val state = _uiState.value
        if (!state.isEdit) return
        viewModelScope.launch {
            dao.getGameById(state.id)?.let { dao.delete(it) }
        }
    }
}

data class DetailUiState(
    val id: Long = 0,
    val title: String = "",
    val platform: String = "",
    val rating: Float = 3f,
    val playTime: String = "0",
    val finished: Boolean = false,
    val imageUrl: String = "",
    val isEdit: Boolean = false
)
