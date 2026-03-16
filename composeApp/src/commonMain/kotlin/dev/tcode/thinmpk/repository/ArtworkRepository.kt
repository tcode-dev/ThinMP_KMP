package dev.tcode.thinmpk.repository

import androidx.compose.ui.graphics.ImageBitmap

expect class ArtworkRepository() {
    fun getArtwork(albumId: String): ImageBitmap?
}
