package dev.tcode.thinmpk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tcode.thinmpk.player.MusicPlayer
import dev.tcode.thinmpk.player.MusicPlayerListener
import dev.tcode.thinmpk.view.util.CustomLifecycleEventObserverListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

private fun formatTime(millis: Long): String {
    val totalSeconds = millis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}
const val START_TIME = "00:00"

data class PlayerUiState(
    var songId: String = "",
    var primaryText: String = "",
    var secondaryText: String = "",
    var imageId: String = "",
    var sliderPosition: Float = 0f,
    var currentTime: String = START_TIME,
    var durationTime: String = START_TIME,
    var isPlaying: Boolean = false,
)

class PlayerViewModel : ViewModel(), KoinComponent, MusicPlayerListener,
    CustomLifecycleEventObserverListener {
    private val musicPlayer: MusicPlayer by inject()
    private val INTERVAL_MS = 1000L
    private var initialized: Boolean = false
    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()
    private var seekBarJob: Job? = null

    init {
        update()
    }

    fun toggle() {
        if (musicPlayer.isPlaying()) {
            musicPlayer.pause()
        } else {
            musicPlayer.play()
        }
    }

    fun prev() {
        musicPlayer.prev()
    }

    fun next() {
        musicPlayer.next()
    }

    fun seek(value: Float) {
        cancelSeekBarProgressTask()

        val song = musicPlayer.getCurrentSong() ?: return
        val ms = (song.duration.toFloat() * value).toLong()

        musicPlayer.seekTo(ms)

        seekBarProgress()
    }

    fun seekFinished() {
        setSeekBarProgressTask()
    }

//    fun changeRepeat() {
//        musicPlayer.changeRepeat()
//    }
//
//    fun changeShuffle() {
//        musicPlayer.changeShuffle()
//    }

//    fun favoriteArtist() {
//        val song = musicPlayer.getCurrentSong() ?: return
//
//        if (exists(song.artistId)) {
//            delete(song.artistId)
//        } else {
//            add(song.artistId)
//        }
//
//        update()
//    }

//    fun favoriteSong() {
//        val song = musicPlayer.getCurrentSong() ?: return
//
//        if (exists(song.songId)) {
//            delete(song.songId)
//        } else {
//            add(song.songId)
//        }
//
//        update()
//    }

    override fun onChange() {
        cancelSeekBarProgressTask()
        update()
    }

    override fun onStop() {
        cancelSeekBarProgressTask()
    }

    private fun seekBarProgress() {
        _uiState.update { currentState ->
            currentState.copy(
                sliderPosition = getSliderPosition(),
                currentTime = formatTime(musicPlayer.getCurrentPosition()),
            )
        }
    }

    private fun setSeekBarProgressTask() {
        if (!musicPlayer.isPlaying()) return

        cancelSeekBarProgressTask()
        seekBarJob = viewModelScope.launch {
            while (true) {
                seekBarProgress()
                delay(INTERVAL_MS)
            }
        }
    }

    private fun cancelSeekBarProgressTask() {
        seekBarJob?.cancel()
        seekBarJob = null
    }

    private fun update() {
        val song = musicPlayer.getCurrentSong() ?: return

        _uiState.update { currentState ->
            currentState.copy(
                songId = song.id,
                primaryText = song.name,
                secondaryText = song.artistName,
                imageId = song.imageId,
                sliderPosition = getSliderPosition(),
                currentTime = formatTime(musicPlayer.getCurrentPosition()),
                durationTime = formatTime(song.duration.toLong() * 1000),
                isPlaying = musicPlayer.isPlaying(),
            )
        }

        setSeekBarProgressTask()
    }

    private fun getSliderPosition(): Float {
        val song = musicPlayer.getCurrentSong() ?: return 0f

        return musicPlayer.getCurrentPosition().toFloat() / (song.duration.toFloat() * 1000)
    }
}