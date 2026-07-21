package dev.tcode.thinmpk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tcode.thinmpk.model.SongModel
import dev.tcode.thinmpk.player.MusicPlayer
import dev.tcode.thinmpk.repository.FavoriteSongRepository
import dev.tcode.thinmpk.repository.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class FavoriteSongsUiState(
    val songs: List<SongModel> = emptyList(),
)

class FavoriteSongsViewModel : ViewModel(), KoinComponent {
    private val favoriteSongRepository: FavoriteSongRepository by inject()
    private val songRepository: SongRepository by inject()
    private val musicPlayer: MusicPlayer by inject()
    private val _uiState = MutableStateFlow(FavoriteSongsUiState())
    val uiState: StateFlow<FavoriteSongsUiState> = _uiState.asStateFlow()

    fun load() {
        viewModelScope.launch {
            val ids = favoriteSongRepository.findAll()
            val songs = songRepository.findByIds(ids)

            _uiState.update { it.copy(songs = songs) }
        }
    }

    fun start(index: Int) {
        val songs = _uiState.value.songs

        if (songs.isNotEmpty()) {
            musicPlayer.start(songs, index)
        }
    }
}
