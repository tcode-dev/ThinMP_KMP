package dev.tcode.thinmpk

import androidx.compose.ui.window.ComposeUIViewController
import dev.tcode.thinmpk.di.initKoin
import dev.tcode.thinmpk.repository.ArtworkRepository
import dev.tcode.thinmpk.repository.SongRepository

fun MainViewController(songRepository: SongRepository, artworkRepository: ArtworkRepository) = ComposeUIViewController(
    configure = { initKoin(songRepository, artworkRepository) }
) { App() }
