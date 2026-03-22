package dev.tcode.thinmpk.bridge

import android.net.Uri
import dev.tcode.thinmpk.MainApplication

class ArtworkBridgeImpl : ArtworkBridge {
    override fun getArtwork(id: String): ByteArray? {
        val context = MainApplication.appContext
        val uri = Uri.parse("content://media/external/audio/albumart/$id")

        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.readBytes()
            }
        } catch (_: Exception) {
            null
        }
    }
}
