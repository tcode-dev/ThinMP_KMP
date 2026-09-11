package dev.tcode.thinmpk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tcode.thinmpk.model.SongModel
import dev.tcode.thinmpk.repository.FavoriteSongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class FavoriteSongMenuUiState(
    // null は取得前
    val isFavorite: Boolean? = null,
)

class FavoriteSongMenuViewModel : ViewModel(), KoinComponent {
    private val favoriteSongRepository: FavoriteSongRepository by inject()
    private val _uiState = MutableStateFlow(FavoriteSongMenuUiState())
    val uiState: StateFlow<FavoriteSongMenuUiState> = _uiState.asStateFlow()

    fun load(song: SongModel) {
        viewModelScope.launch {
            _uiState.update { it.copy(isFavorite = null) }

            val isFavorite = favoriteSongRepository.exists(song.id)

            _uiState.update { it.copy(isFavorite = isFavorite) }
        }
    }

    fun toggle(song: SongModel, onCompleted: () -> Unit = {}) {
        viewModelScope.launch {
            if (favoriteSongRepository.exists(song.id)) {
                favoriteSongRepository.delete(song.id)
            } else {
                favoriteSongRepository.add(song.id)
            }

            onCompleted()
        }
    }
}
