package dev.tcode.thinmpk.repository

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import org.jetbrains.skia.Image as SkiaImage
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSData
import platform.UIKit.UIImagePNGRepresentation
import platform.posix.memcpy

actual class ArtworkRepository actual constructor() {
    private val songRepository = SongRepository()

    @OptIn(ExperimentalForeignApi::class)
    actual fun getArtwork(id: String): ImageBitmap? {
        val mediaItem = songRepository.findMediaItemById(id) ?: return null
        val artwork = mediaItem.artwork ?: return null
        val uiImage = artwork.imageWithSize(CGSizeMake(100.0, 100.0)) ?: return null
        val nsData: NSData = UIImagePNGRepresentation(uiImage) ?: return null

        val bytes = ByteArray(nsData.length.toInt())
        bytes.usePinned { pinned ->
            memcpy(pinned.addressOf(0), nsData.bytes, nsData.length)
        }

        return try {
            SkiaImage.makeFromEncoded(bytes).toComposeImageBitmap()
        } catch (_: Exception) {
            null
        }
    }
}
