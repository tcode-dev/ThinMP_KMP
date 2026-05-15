package dev.tcode.thinmpk.repository

import dev.tcode.thinmpk.model.SongModel

interface SongRepository {
    fun findAll(): List<SongModel>
    fun findById(id: String): SongModel?
    fun findByAlbumId(albumId: String): List<SongModel>
    fun findByArtistId(artistId: String): List<SongModel>
}
