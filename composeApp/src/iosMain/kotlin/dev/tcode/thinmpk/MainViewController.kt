package dev.tcode.thinmpk

import androidx.compose.ui.window.ComposeUIViewController
import dev.tcode.thinmpk.bridge.SongBridge
import dev.tcode.thinmpk.di.initKoin
import dev.tcode.thinmpk.repository.ArtworkRepository

fun MainViewController(songBridge: SongBridge, artworkRepository: ArtworkRepository) = ComposeUIViewController(
    configure = { initKoin(songBridge, artworkRepository) }
) { App() }
