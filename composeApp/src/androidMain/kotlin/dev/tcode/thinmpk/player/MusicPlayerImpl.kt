package dev.tcode.thinmpk.player

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import dev.tcode.thinmpk.config.RepeatState
import dev.tcode.thinmpk.model.SongModel

class MusicPlayerImpl(private val context: Context) : MusicPlayer {
    private var musicService: MusicService? = null
    private lateinit var connection: ServiceConnection
    private var isServiceBinding = false
    private var bound = false
    private val listeners: MutableList<MusicPlayerListener> = mutableListOf()
    private val serviceListener = object : MusicPlayerListener {
        override fun onChange() {
            listeners.forEach { it.onChange() }
        }

        override fun onError() {
            listeners.forEach { it.onError() }
        }
    }

    override fun start(songs: List<SongModel>, index: Int) {
        if (isServiceBinding) return

        if (!MusicService.isServiceRunning) {
            context.startForegroundService(Intent(context, MusicService::class.java))
            bindService { musicService?.start(songs, index) }
            return
        }

        if (!bound) {
            bindService { musicService?.start(songs, index) }
            return
        }

        musicService?.start(songs, index)
    }

    override fun play() {
        musicService?.play()
    }

    override fun pause() {
        musicService?.pause()
    }

    override fun prev() {
        musicService?.prev()
    }

    override fun next() {
        musicService?.next()
    }

    override fun seekTo(ms: Long) {
        musicService?.seekTo(ms)
    }

    override fun isPlaying(): Boolean {
        return musicService?.isPlaying() == true
    }

    override fun getCurrentSong(): SongModel? {
        return musicService?.getCurrentSong()
    }

    override fun getCurrentPosition(): Long {
        return musicService?.getCurrentPosition() ?: 0
    }

    override fun getRepeat(): RepeatState {
        return musicService?.getRepeat() ?: RepeatState.OFF
    }

    override fun changeRepeat() {
        musicService?.changeRepeat()
    }

    override fun getShuffle(): Boolean {
        return musicService?.getShuffle() ?: false
    }

    override fun changeShuffle() {
        musicService?.changeShuffle()
    }

    override fun addEventListener(listener: MusicPlayerListener) {
        listeners.add(listener)
    }

    override fun removeEventListener(listener: MusicPlayerListener) {
        listeners.remove(listener)
    }

    private fun bindService(callback: () -> Unit = {}) {
        if (isServiceBinding || bound) return

        isServiceBinding = true
        connection = createConnection(callback)
        context.bindService(
            Intent(context, MusicService::class.java),
            connection,
            Context.BIND_AUTO_CREATE
        )
    }

    private fun createConnection(callback: () -> Unit = {}): ServiceConnection {
        return object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                val binder = service as MusicService.MusicBinder
                musicService = binder.getService()
                musicService!!.addEventListener(serviceListener)
                callback()
                isServiceBinding = false
                bound = true
            }

            override fun onServiceDisconnected(name: ComponentName) {
                musicService = null
                bound = false
            }
        }
    }
}
