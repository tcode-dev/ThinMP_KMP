package dev.tcode.thinmpk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tcode.thinmpk.model.ArtistModel
import dev.tcode.thinmpk.repository.ArtistRepository
import dev.tcode.thinmpk.repository.FavoriteArtistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class FavoriteArtistsUiState(
    val artists: List<ArtistModel> = emptyList(),
)

class FavoriteArtistsViewModel : ViewModel(), KoinComponent {
    private val favoriteArtistRepository: FavoriteArtistRepository by inject()
    private val artistRepository: ArtistRepository by inject()
    private val _uiState = MutableStateFlow(FavoriteArtistsUiState())
    val uiState: StateFlow<FavoriteArtistsUiState> = _uiState.asStateFlow()

    fun load() {
        viewModelScope.launch {
            val ids = favoriteArtistRepository.findAll()
            val artists = ids.mapNotNull { artistRepository.findById(it) }

            _uiState.update { it.copy(artists = artists) }
        }
    }
}
