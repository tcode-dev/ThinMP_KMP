package dev.tcode.thinmpk.repository

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import org.jetbrains.skia.Image as SkiaImage
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSData
import platform.MediaPlayer.MPMediaItem
import platform.MediaPlayer.MPMediaItemPropertyAlbumPersistentID
import platform.MediaPlayer.MPMediaItemPropertyIsCloudItem
import platform.MediaPlayer.MPMediaPropertyPredicate
import platform.MediaPlayer.MPMediaQuery
import platform.UIKit.UIImagePNGRepresentation
import platform.posix.memcpy

actual class ArtworkRepository actual constructor() {
    @OptIn(ExperimentalForeignApi::class)
    actual fun getArtwork(albumId: String): ImageBitmap? {
        val albumPersistentID = albumId.toULongOrNull() ?: return null

        val query = MPMediaQuery.albumsQuery()
        query.addFilterPredicate(
            MPMediaPropertyPredicate.predicateWithValue(
                value = albumPersistentID,
                forProperty = MPMediaItemPropertyAlbumPersistentID
            )
        )
        query.addFilterPredicate(
            MPMediaPropertyPredicate.predicateWithValue(
                value = false,
                forProperty = MPMediaItemPropertyIsCloudItem
            )
        )

        val item = query.items?.firstOrNull() as? MPMediaItem ?: return null
        val artwork = item.artwork ?: return null
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
