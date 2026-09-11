package dev.tcode.thinmpk.viewmodel

import androidx.lifecycle.ViewModel
import dev.tcode.thinmpk.model.AlbumModel
import dev.tcode.thinmpk.model.SongModel
import dev.tcode.thinmpk.player.MusicPlayer
import dev.tcode.thinmpk.repository.AlbumRepository
import dev.tcode.thinmpk.repository.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class AlbumDetailUiState(
    val album: AlbumModel? = null,
    val songs: List<SongModel> = emptyList(),
)

class AlbumDetailViewModel(private val albumId: String) : ViewModel(), KoinComponent {
    private val albumRepository: AlbumRepository by inject()
    private val songRepository: SongRepository by inject()
    private val musicPlayer: MusicPlayer by inject()
    private val _uiState = MutableStateFlow(AlbumDetailUiState())
    val uiState: StateFlow<AlbumDetailUiState> = _uiState.asStateFlow()

    fun load() {
        val album = albumRepository.findById(albumId)
        val songs = songRepository.findByAlbumId(albumId)

        _uiState.update { it.copy(album = album, songs = songs) }
    }

    fun start(index: Int) {
        val songs = _uiState.value.songs

        if (songs.isNotEmpty()) {
            musicPlayer.start(songs, index)
        }
    }
}
