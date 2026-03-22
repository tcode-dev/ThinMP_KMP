package dev.tcode.thinmpk.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import dev.tcode.thinmpk.model.SongModel
import dev.tcode.thinmpk.bridge.ArtworkBridge
import dev.tcode.thinmpk.bridge.SongBridge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class SongsUiState(
    val songs: List<SongModel> = emptyList(),
    val artworks: Map<String, ImageBitmap> = emptyMap(),
)

class SongsViewModel : ViewModel(), KoinComponent {
    private val songBridge: SongBridge by inject()
    private val artworkBridge: ArtworkBridge by inject()
    private val _uiState = MutableStateFlow(SongsUiState())
    val uiState: StateFlow<SongsUiState> = _uiState.asStateFlow()

    fun load() {
        val songs = songBridge.findAll()

        val artworks = mutableMapOf<String, ImageBitmap>()
        val imageIds = mutableSetOf<String>()

        for (song in songs) {
            if (song.imageId in imageIds) continue
            imageIds.add(song.imageId)
            artworkBridge.getArtwork(song.imageId)?.let { bitmap ->
                artworks[song.imageId] = bitmap
            }
        }

        _uiState.update { it.copy(songs = songs, artworks = artworks) }
    }
}
