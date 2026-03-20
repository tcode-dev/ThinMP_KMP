package dev.tcode.thinmpk.repository

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import dev.tcode.thinmpk.MainApplication

actual class ArtworkRepository actual constructor() {
    actual fun getArtwork(id: String): ImageBitmap? {
        val context = MainApplication.appContext
        val uri = Uri.parse("content://media/external/audio/albumart/$id")

        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)?.asImageBitmap()
            }
        } catch (_: Exception) {
            null
        }
    }
}
