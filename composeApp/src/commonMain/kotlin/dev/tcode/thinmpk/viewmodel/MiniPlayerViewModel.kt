package dev.tcode.thinmpk.viewmodel

import androidx.lifecycle.ViewModel
import dev.tcode.thinmpk.player.MusicPlayer
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

class MiniPlayerViewModel : ViewModel(), KoinComponent {
    private val musicPlayer: MusicPlayer by inject()
//    private var initialized: Boolean = false
    private val _uiState = MutableStateFlow(MiniPlayerUiState())
    val uiState: StateFlow<MiniPlayerUiState> = _uiState.asStateFlow()

    fun toggle() {
        if (musicPlayer.isPlaying()) {
            musicPlayer.pause()
        } else {
            musicPlayer.play()
        }
    }

    fun next() {
        musicPlayer.next()
    }

//    override fun onChange() {
//        update()
//    }

//    override fun onError() {
//        _uiState.update { currentState ->
//            currentState.copy(
//                isVisible = false
//            )
//        }
//    }

//    private fun update() {
//        val song = musicPlayer.getCurrentSong() ?: return
//
//        _uiState.update { currentState ->
//            currentState.copy(
//                primaryText = song.name, imageId = song.imageId, isVisible = true, isPlaying = musicPlayer.isPlaying()
//            )
//        }
//    }
}