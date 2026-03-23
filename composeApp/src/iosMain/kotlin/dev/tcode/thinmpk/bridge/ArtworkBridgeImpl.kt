package dev.tcode.thinmpk.bridge

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import dev.tcode.thinmpk.repository.ArtworkRepository
import org.jetbrains.skia.Image as SkiaImage

class ArtworkBridgeImpl(private val artworkRepository: ArtworkRepository) : ArtworkBridge {
    override fun getArtwork(id: String): ImageBitmap? {
        val bytes = artworkRepository.getArtwork(id) ?: return null
        return try {
            SkiaImage.makeFromEncoded(bytes).toComposeImageBitmap()
        } catch (_: Exception) {
            null
        }
    }
}
