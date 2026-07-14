package dev.tcode.thinmpk.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.tcode.thinmpk.player.MusicPlayer
import dev.tcode.thinmpk.player.MusicPlayerImpl
import dev.tcode.thinmpk.repository.AlbumRepository
import dev.tcode.thinmpk.repository.AlbumRepositoryImpl
import dev.tcode.thinmpk.repository.ArtistRepository
import dev.tcode.thinmpk.repository.ArtistRepositoryImpl
import dev.tcode.thinmpk.repository.FavoriteArtistRepository
import dev.tcode.thinmpk.repository.FavoriteSongRepository
import dev.tcode.thinmpk.repository.SongRepository
import dev.tcode.thinmpk.repository.SongRepositoryImpl
import dev.tcode.thinmpk.repository.room.AppDatabase
import dev.tcode.thinmpk.repository.room.getDatabaseBuilder
import org.koin.dsl.module

val androidModule = module {
    single<AppDatabase> {
        getDatabaseBuilder()
            .fallbackToDestructiveMigration(true)
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single<SongRepository> { SongRepositoryImpl() }
    single<AlbumRepository> { AlbumRepositoryImpl() }
    single<ArtistRepository> { ArtistRepositoryImpl() }
    single<MusicPlayer> { MusicPlayerImpl(get()) }
    single<FavoriteSongRepository> { FavoriteSongRepository(get<AppDatabase>().favoriteSongDao()) }
    single<FavoriteArtistRepository> { FavoriteArtistRepository(get<AppDatabase>().favoriteArtistDao()) }
}
