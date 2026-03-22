package dev.tcode.thinmpk.bridge

interface ArtworkDataBridge {
    fun getArtwork(id: String): ByteArray?
}
