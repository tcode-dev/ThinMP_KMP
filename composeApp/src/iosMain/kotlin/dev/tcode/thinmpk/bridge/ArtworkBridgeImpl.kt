package dev.tcode.thinmpk.bridge

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image as SkiaImage

class ArtworkBridgeImpl(private val dataBridge: ArtworkDataBridge) : ArtworkBridge {
    override fun getArtwork(id: String): ImageBitmap? {
        val bytes = dataBridge.getArtwork(id) ?: return null
        return try {
            SkiaImage.makeFromEncoded(bytes).toComposeImageBitmap()
        } catch (_: Exception) {
            null
        }
    }
}
