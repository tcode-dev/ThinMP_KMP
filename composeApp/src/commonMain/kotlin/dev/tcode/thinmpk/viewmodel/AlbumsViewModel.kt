package dev.tcode.thinmpk.viewmodel

import androidx.lifecycle.ViewModel
import dev.tcode.thinmpk.model.AlbumModel
import dev.tcode.thinmpk.repository.AlbumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class AlbumsUiState(
    val albums: List<AlbumModel> = emptyList(),
)

class AlbumsViewModel : ViewModel(), KoinComponent {
    private val albumRepository: AlbumRepository by inject()
    private val _uiState = MutableStateFlow(AlbumsUiState())
    val uiState: StateFlow<AlbumsUiState> = _uiState.asStateFlow()

    fun load() {
        val albums = albumRepository.findAll()

        _uiState.update { it.copy(albums = albums) }
    }
}
