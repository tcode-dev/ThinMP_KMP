package dev.tcode.thinmpk

import androidx.compose.ui.window.ComposeUIViewController
import dev.tcode.thinmpk.di.initKoin
import dev.tcode.thinmpk.player.MusicPlayer
import dev.tcode.thinmpk.repository.AlbumRepository
import dev.tcode.thinmpk.repository.ArtworkRepository
import dev.tcode.thinmpk.repository.SongRepository

fun MainViewController(
    songRepository: SongRepository,
    albumRepository: AlbumRepository,
    artworkRepository: ArtworkRepository,
    musicPlayer: MusicPlayer,
) = ComposeUIViewController(
    configure = { initKoin(songRepository, albumRepository, artworkRepository, musicPlayer) }
) { App() }
