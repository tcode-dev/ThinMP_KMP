package dev.tcode.thinmpk.model

data class SongModel(
    val id: String,
    val name: String,
    val artistName: String,
    val albumId: String,
    val albumName: String,
    val duration: Int,
)
