package dev.tcode.thinmpk.di

import dev.tcode.thinmpk.bridge.SongBridge
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin(songBridge: SongBridge) {
    startKoin {
        modules(
            module {
                single<SongBridge> { songBridge }
            }
        )
    }
}
