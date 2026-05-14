package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.database.CategoryDao
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.database.GameDao
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.model.Category
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.model.Game
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel(
    private val dao: GameDao,
    private val categoryDao: CategoryDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    val categories: StateFlow<List<Category>> = categoryDao.getAllCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun loadGame(id: Long) {
        if (id == -1L) return
        viewModelScope.launch {
            dao.getGameById(id)?.let { game ->
                _uiState.update {
                    it.copy(
                        id = game.id,
                        title = game.title,
                        platform = game.platform,
                        categoryId = game.categoryId,
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
            is DetailAction.OnCategoryChange -> _uiState.update { it.copy(categoryId = action.categoryId) }
            is DetailAction.OnCustomCategoryChange -> _uiState.update { it.copy(customCategoryName = action.name) }
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

        viewModelScope.launch {
            var finalCategoryId = state.categoryId
            val selectedCategory = categories.value.find { it.categoryId == state.categoryId }

            if (selectedCategory?.name == "Other" && state.customCategoryName.isNotBlank()) {
                val existing = categories.value.find { 
                    it.name.equals(state.customCategoryName, ignoreCase = true) 
                }
                finalCategoryId = existing?.categoryId ?: categoryDao.insertCategory(Category(name = state.customCategoryName))
            }

            val game = Game(
                id = if (state.isEdit) state.id else 0,
                title = state.title,
                platform = state.platform,
                categoryId = finalCategoryId,
                rating = state.rating.toInt(),
                playTime = state.playTime.toIntOrNull() ?: 0,
                finished = state.finished,
                imageUrl = state.imageUrl
            )

            if (state.isEdit) dao.update(game) else dao.insert(game)
        }
    }

    private fun deleteGame() {
        val state = _uiState.value
        if (!state.isEdit) return
        viewModelScope.launch {
            dao.moveToTrash(state.id)
        }
    }
}

data class DetailUiState(
    val id: Long = 0,
    val title: String = "",
    val platform: String = "",
    val categoryId: Long = 1,
    val customCategoryName: String = "",
    val rating: Float = 3f,
    val playTime: String = "0",
    val finished: Boolean = false,
    val imageUrl: String = "",
    val isEdit: Boolean = false
)
