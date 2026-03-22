package dev.tcode.thinmpk.di

import dev.tcode.thinmpk.bridge.ArtworkBridge
import dev.tcode.thinmpk.bridge.ArtworkBridgeImpl
import dev.tcode.thinmpk.bridge.ArtworkDataBridge
import dev.tcode.thinmpk.bridge.SongBridge
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin(songBridge: SongBridge, artworkDataBridge: ArtworkDataBridge) {
    startKoin {
        modules(
            module {
                single<SongBridge> { songBridge }
                single<ArtworkBridge> { ArtworkBridgeImpl(artworkDataBridge) }
            }
        )
    }
}
