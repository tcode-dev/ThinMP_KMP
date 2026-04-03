package dev.tcode.thinmpk.di

import dev.tcode.thinmpk.bridge.SongBridge
import dev.tcode.thinmpk.repository.ArtworkRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin(songBridge: SongBridge, artworkRepository: ArtworkRepository) {
    startKoin {
        modules(
            module {
                single<SongBridge> { songBridge }
                single<ArtworkRepository> { artworkRepository }
            }
        )
    }
}
