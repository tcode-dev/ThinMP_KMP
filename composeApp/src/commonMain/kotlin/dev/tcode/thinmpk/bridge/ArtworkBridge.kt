package dev.tcode.thinmpk.bridge

import androidx.compose.ui.graphics.ImageBitmap

interface ArtworkBridge {
    fun getArtwork(id: String): ImageBitmap?
}
