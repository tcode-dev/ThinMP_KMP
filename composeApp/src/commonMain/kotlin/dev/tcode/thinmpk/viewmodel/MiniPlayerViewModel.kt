package dev.tcode.thinmpk.viewmodel

import androidx.lifecycle.ViewModel
import dev.tcode.thinmpk.player.MusicPlayer
import dev.tcode.thinmpk.player.MusicPlayerListener
import dev.tcode.thinmpk.view.util.CustomLifecycleEventObserverListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

data class MiniPlayerUiState(
    var primaryText: String = "", var imageId: String = "", var isVisible: Boolean = false, var isPlaying: Boolean = false
)

class MiniPlayerViewModel : ViewModel(), KoinComponent, MusicPlayerListener, CustomLifecycleEventObserverListener {
    private val musicPlayer: MusicPlayer by inject()
    private val _uiState = MutableStateFlow(MiniPlayerUiState())
    val uiState: StateFlow<MiniPlayerUiState> = _uiState.asStateFlow()

    private val listener = object : MusicPlayerListener {
        override fun onChange() {
            updateState()
        }

        override fun onError() {
            _uiState.update { it.copy(isVisible = false) }
        }
    }

    init {
        musicPlayer.addEventListener(listener)
        updateState()
    }

    fun toggle() {
        if (musicPlayer.isPlaying()) {
            musicPlayer.pause()
        } else {
            musicPlayer.play()
        }
//        updateState()
    }

    fun next() {
        musicPlayer.next()
    }

    private fun updateState() {
        val song = musicPlayer.getCurrentSong() ?: return

        _uiState.update {
            it.copy(
                primaryText = song.name, imageId = song.imageId, isVisible = true, isPlaying = musicPlayer.isPlaying()
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        musicPlayer.removeEventListener(listener)
    }
}