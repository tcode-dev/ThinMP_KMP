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
import platform.MediaPlayer.MPMediaItemCollection
import platform.MediaPlayer.MPMediaItemPropertyIsCloudItem
import platform.MediaPlayer.MPMediaPropertyPredicate
import platform.MediaPlayer.MPMediaQuery
import platform.UIKit.UIImagePNGRepresentation
import platform.posix.memcpy

actual class ArtworkRepository actual constructor() {
    private fun findMediaItemById(id: String): MPMediaItem? {
        val query = MPMediaQuery.songsQuery()
        query.addFilterPredicate(
            MPMediaPropertyPredicate.predicateWithValue(
                value = false,
                forProperty = MPMediaItemPropertyIsCloudItem
            )
        )

        val collections = query.collections ?: return null
        val collection = collections.firstOrNull { c ->
            val mediaCollection = c as? MPMediaItemCollection ?: return@firstOrNull false
            id == mediaCollection.representativeItem?.persistentID?.toString()
        } as? MPMediaItemCollection ?: return null

        return collection.representativeItem
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun getArtwork(id: String): ImageBitmap? {
        val mediaItem = findMediaItemById(id) ?: return null
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
