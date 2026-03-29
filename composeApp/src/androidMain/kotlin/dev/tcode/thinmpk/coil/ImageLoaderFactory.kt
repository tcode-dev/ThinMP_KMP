package dev.tcode.thinmpk.coil

import android.net.Uri
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.decode.DataSource
import coil3.decode.ImageSource
import coil3.fetch.FetchResult
import coil3.fetch.Fetcher
import coil3.fetch.SourceFetchResult
import coil3.memory.MemoryCache
import coil3.request.Options
import dev.tcode.thinmpk.MainApplication
import dev.tcode.thinmpk.model.ArtworkModel
import okio.Buffer
import okio.FileSystem

class ArtworkFetcher(
    private val model: ArtworkModel,
) : Fetcher {
    override suspend fun fetch(): FetchResult? {
        val context = MainApplication.appContext
        val uri = Uri.parse("content://media/external/audio/albumart/${model.id}")
        val bytes = try {
            context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
        } catch (_: Exception) {
            null
        } ?: return null
        return SourceFetchResult(
            source = ImageSource(Buffer().apply { write(bytes) }, FileSystem.SYSTEM),
            mimeType = null,
            dataSource = DataSource.DISK,
        )
    }

    class Factory : Fetcher.Factory<ArtworkModel> {
        override fun create(data: ArtworkModel, options: Options, imageLoader: ImageLoader): Fetcher {
            return ArtworkFetcher(data)
        }
    }
}

actual fun newImageLoader(context: PlatformContext): ImageLoader {
    return ImageLoader.Builder(context)
        .components {
            add(ArtworkKeyer())
            add(ArtworkFetcher.Factory())
        }
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, 0.25)
                .build()
        }
        .build()
}
