package dev.tcode.thinmpk.coil

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.decode.DataSource
import coil3.decode.ImageSource
import coil3.fetch.FetchResult
import coil3.fetch.Fetcher
import coil3.fetch.SourceFetchResult
import coil3.memory.MemoryCache
import coil3.request.Options
import dev.tcode.thinmpk.model.ArtworkModel
import dev.tcode.thinmpk.repository.ArtworkRepository
import okio.Buffer
import okio.FileSystem
import org.koin.mp.KoinPlatform

class ArtworkFetcher(
    private val artworkRepository: ArtworkRepository,
    private val model: ArtworkModel,
) : Fetcher {
    override suspend fun fetch(): FetchResult? {
        val bytes = artworkRepository.getArtwork(model.id) ?: return null
        return SourceFetchResult(
            source = ImageSource(Buffer().apply { write(bytes) }, FileSystem.SYSTEM),
            mimeType = null,
            dataSource = DataSource.DISK,
        )
    }

    class Factory(private val artworkRepository: ArtworkRepository) : Fetcher.Factory<ArtworkModel> {
        override fun create(data: ArtworkModel, options: Options, imageLoader: ImageLoader): Fetcher {
            return ArtworkFetcher(artworkRepository, data)
        }
    }
}

actual fun newImageLoader(context: PlatformContext): ImageLoader {
    val artworkRepository: ArtworkRepository = KoinPlatform.getKoin().get()
    return ImageLoader.Builder(context)
        .components {
            add(ArtworkKeyer())
            add(ArtworkFetcher.Factory(artworkRepository))
        }
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, 0.25)
                .build()
        }
        .build()
}
