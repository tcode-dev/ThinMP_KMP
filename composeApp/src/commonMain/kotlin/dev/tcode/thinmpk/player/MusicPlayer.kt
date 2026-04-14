package dev.tcode.thinmpk.player

import dev.tcode.thinmpk.config.RepeatState
import dev.tcode.thinmpk.model.SongModel

interface MusicPlayer {
    fun start(songs: List<SongModel>, index: Int)
    fun play()
    fun pause()
    fun prev()
    fun next()
    fun seekTo(ms: Long)
    fun isPlaying(): Boolean
    fun getCurrentSong(): SongModel?
    fun getCurrentPosition(): Long
    fun getRepeat(): RepeatState
    fun changeRepeat()
    fun getShuffle(): Boolean
    fun changeShuffle()
    fun addEventListener(listener: MusicPlayerListener)
    fun removeEventListener(listener: MusicPlayerListener)
}