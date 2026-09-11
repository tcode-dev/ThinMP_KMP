package dev.tcode.thinmpk.viewmodel

import androidx.lifecycle.ViewModel
import dev.tcode.thinmpk.model.SongModel
import dev.tcode.thinmpk.player.MusicPlayer
import dev.tcode.thinmpk.repository.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class SongsUiState(
    val songs: List<SongModel> = emptyList(),
)

class SongsViewModel : ViewModel(), KoinComponent {
    private val songRepository: SongRepository by inject()
    private val musicPlayer: MusicPlayer by inject()
    private val _uiState = MutableStateFlow(SongsUiState())
    val uiState: StateFlow<SongsUiState> = _uiState.asStateFlow()

    fun load() {
        val songs = songRepository.findAll()

        _uiState.update { it.copy(songs = songs) }
    }

    fun start(index: Int) {
        val songs = _uiState.value.songs

        if (songs.isNotEmpty()) {
            musicPlayer.start(songs, index)
        }
    }
}
