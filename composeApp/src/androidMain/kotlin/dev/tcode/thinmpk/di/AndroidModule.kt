package dev.tcode.thinmpk.di

import dev.tcode.thinmpk.bridge.SongBridge
import dev.tcode.thinmpk.bridge.SongBridgeImpl
import org.koin.dsl.module

val androidModule = module {
    single<SongBridge> { SongBridgeImpl() }
}
