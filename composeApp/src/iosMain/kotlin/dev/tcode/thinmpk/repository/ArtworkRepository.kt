package dev.tcode.thinmpk.repository

interface ArtworkRepository {
    fun getArtwork(id: String): ByteArray?
}