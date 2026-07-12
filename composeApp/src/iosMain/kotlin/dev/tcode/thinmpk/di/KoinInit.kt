package dev.tcode.thinmpk.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.tcode.thinmpk.player.MusicPlayer
import dev.tcode.thinmpk.repository.AlbumRepository
import dev.tcode.thinmpk.repository.ArtistRepository
import dev.tcode.thinmpk.repository.ArtworkRepository
import dev.tcode.thinmpk.repository.FavoriteSongRepository
import dev.tcode.thinmpk.repository.SongRepository
import dev.tcode.thinmpk.repository.room.AppDatabase
import dev.tcode.thinmpk.repository.room.getDatabaseBuilder
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin(
    songRepository: SongRepository,
    albumRepository: AlbumRepository,
    artistRepository: ArtistRepository,
    artworkRepository: ArtworkRepository,
    musicPlayer: MusicPlayer
) {
    startKoin {
        modules(
            module {
                single<AppDatabase> {
                    getDatabaseBuilder()
                        .setDriver(BundledSQLiteDriver())
                        .build()
                }
                single<SongRepository> { songRepository }
                single<AlbumRepository> { albumRepository }
                single<ArtistRepository> { artistRepository }
                single<ArtworkRepository> { artworkRepository }
                single<MusicPlayer> { musicPlayer }
                single<FavoriteSongRepository> { FavoriteSongRepository(get<AppDatabase>().favoriteSongDao()) }
            }
        )
    }
}
