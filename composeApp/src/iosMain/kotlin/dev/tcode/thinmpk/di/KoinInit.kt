package dev.tcode.thinmpk.di

import dev.tcode.thinmpk.repository.ArtworkRepository
import dev.tcode.thinmpk.repository.SongRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin(songRepository: SongRepository, artworkRepository: ArtworkRepository) {
    startKoin {
        modules(
            module {
                single<SongRepository> { songRepository }
                single<ArtworkRepository> { artworkRepository }
            }
        )
    }
}
