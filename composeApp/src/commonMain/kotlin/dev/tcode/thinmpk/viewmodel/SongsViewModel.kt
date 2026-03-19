package dev.tcode.thinmpk.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import dev.tcode.thinmpk.model.SongModel
import dev.tcode.thinmpk.repository.ArtworkRepository
import dev.tcode.thinmpk.repository.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SongsUiState(
    val songs: List<SongModel> = emptyList(),
    val artworks: Map<String, ImageBitmap> = emptyMap(),
)

class SongsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SongsUiState())
    val uiState: StateFlow<SongsUiState> = _uiState.asStateFlow()

    fun load() {
        val songRepository = SongRepository()
        val artworkRepository = ArtworkRepository()
        val songs = songRepository.findAll()

        val artworks = mutableMapOf<String, ImageBitmap>()
        val loadedAlbumIds = mutableSetOf<String>()

        for (song in songs) {
            if (song.albumId in loadedAlbumIds) continue
            loadedAlbumIds.add(song.albumId)
            artworkRepository.getArtwork(song.albumId)?.let { bitmap ->
                artworks[song.albumId] = bitmap
            }
        }

        _uiState.update { it.copy(songs = songs, artworks = artworks) }
    }
}
