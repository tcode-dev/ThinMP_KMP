package dev.tcode.thinmpk.di

import dev.tcode.thinmpk.player.MusicPlayer
import dev.tcode.thinmpk.player.MusicPlayerImpl
import dev.tcode.thinmpk.repository.AlbumRepository
import dev.tcode.thinmpk.repository.AlbumRepositoryImpl
import dev.tcode.thinmpk.repository.SongRepository
import dev.tcode.thinmpk.repository.SongRepositoryImpl
import org.koin.dsl.module

val androidModule = module {
    single<SongRepository> { SongRepositoryImpl() }
    single<AlbumRepository> { AlbumRepositoryImpl() }
    single<MusicPlayer> { MusicPlayerImpl(get()) }
}
