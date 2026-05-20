package dev.tcode.thinmpk.viewmodel

import androidx.lifecycle.ViewModel
import dev.tcode.thinmpk.model.AlbumModel
import dev.tcode.thinmpk.model.ArtistModel
import dev.tcode.thinmpk.model.SongModel
import dev.tcode.thinmpk.player.MusicPlayer
import dev.tcode.thinmpk.repository.AlbumRepository
import dev.tcode.thinmpk.repository.ArtistRepository
import dev.tcode.thinmpk.repository.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class ArtistDetailUiState(
    val artist: ArtistModel? = null,
    val albums: List<AlbumModel> = emptyList(),
    val songs: List<SongModel> = emptyList(),
)

class ArtistDetailViewModel(private val artistId: String) : ViewModel(), KoinComponent {
    private val artistRepository: ArtistRepository by inject()
    private val albumRepository: AlbumRepository by inject()
    private val songRepository: SongRepository by inject()
    private val musicPlayer: MusicPlayer by inject()
    private val _uiState = MutableStateFlow(ArtistDetailUiState())
    val uiState: StateFlow<ArtistDetailUiState> = _uiState.asStateFlow()

    fun load() {
        val artist = artistRepository.findById(artistId)
        val albums = albumRepository.findByArtistId(artistId)
        val songs = songRepository.findByArtistId(artistId)

        _uiState.update { it.copy(artist = artist, albums = albums, songs = songs) }
    }

    fun start(index: Int) {
        val songs = _uiState.value.songs

        if (songs.isNotEmpty()) {
            musicPlayer.start(songs, index)
        }
    }
}
