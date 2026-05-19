package dev.tcode.thinmpk.viewmodel

import androidx.lifecycle.ViewModel
import dev.tcode.thinmpk.model.ArtistModel
import dev.tcode.thinmpk.repository.ArtistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class ArtistsUiState(
    val artists: List<ArtistModel> = emptyList(),
)

class ArtistsViewModel : ViewModel(), KoinComponent {
    private val artistRepository: ArtistRepository by inject()
    private val _uiState = MutableStateFlow(ArtistsUiState())
    val uiState: StateFlow<ArtistsUiState> = _uiState.asStateFlow()

    fun load() {
        val artists = artistRepository.findAll()

        _uiState.update { it.copy(artists = artists) }
    }
}
