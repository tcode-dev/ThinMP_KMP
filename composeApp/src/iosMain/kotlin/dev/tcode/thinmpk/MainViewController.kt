package dev.tcode.thinmpk

import androidx.compose.ui.window.ComposeUIViewController
import dev.tcode.thinmpk.bridge.ArtworkDataBridge
import dev.tcode.thinmpk.bridge.SongBridge
import dev.tcode.thinmpk.di.initKoin

fun MainViewController(songBridge: SongBridge, artworkDataBridge: ArtworkDataBridge) = ComposeUIViewController(
    configure = { initKoin(songBridge, artworkDataBridge) }
) { App() }
