package dev.tcode.thinmpk

import androidx.compose.ui.window.ComposeUIViewController
import dev.tcode.thinmpk.di.initKoin
import dev.tcode.thinmpk.repository.SongRepository

fun MainViewController(songRepository: SongRepository) = ComposeUIViewController(
    configure = { initKoin(songRepository) }
) { App() }
