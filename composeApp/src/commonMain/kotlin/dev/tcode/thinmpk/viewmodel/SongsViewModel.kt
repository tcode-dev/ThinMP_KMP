package dev.tcode.thinmpk.viewmodel

import androidx.lifecycle.ViewModel
import dev.tcode.thinmpk.model.SongModel
import dev.tcode.thinmpk.repository.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SongsUiState(
    val songs: List<SongModel> = emptyList(),
)

class SongsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SongsUiState())
    val uiState: StateFlow<SongsUiState> = _uiState.asStateFlow()

    fun load() {
        val repository = SongRepository()
        val songs = repository.findAll()
        _uiState.update { it.copy(songs = songs) }
    }
}
