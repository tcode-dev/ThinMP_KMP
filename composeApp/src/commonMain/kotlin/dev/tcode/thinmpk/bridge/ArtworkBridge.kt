package dev.tcode.thinmpk.bridge

interface ArtworkBridge {
    fun getArtwork(id: String): ByteArray?
}
