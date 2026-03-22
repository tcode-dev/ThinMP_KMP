package dev.tcode.thinmpk.converter

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image as SkiaImage

actual fun ByteArray.toImageBitmap(): ImageBitmap? {
    return try {
        SkiaImage.makeFromEncoded(this).toComposeImageBitmap()
    } catch (_: Exception) {
        null
    }
}
