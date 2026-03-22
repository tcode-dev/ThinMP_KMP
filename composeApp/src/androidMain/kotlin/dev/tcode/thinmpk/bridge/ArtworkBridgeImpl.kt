package dev.tcode.thinmpk.bridge

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import dev.tcode.thinmpk.MainApplication

class ArtworkBridgeImpl : ArtworkBridge {
    override fun getArtwork(id: String): ImageBitmap? {
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
