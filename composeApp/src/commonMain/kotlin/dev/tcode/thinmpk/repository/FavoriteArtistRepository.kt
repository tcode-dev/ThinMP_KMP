package dev.tcode.thinmpk.repository

import dev.tcode.thinmpk.model.room.FavoriteArtistEntity
import dev.tcode.thinmpk.repository.dao.FavoriteArtistDao

class FavoriteArtistRepository(private val dao: FavoriteArtistDao) {
    suspend fun exists(artistId: String): Boolean {
        return dao.exists(artistId)
    }

    suspend fun findAll(): List<String> {
        return dao.findAll().map { it.artistId }
    }

    suspend fun add(artistId: String) {
        dao.insert(FavoriteArtistEntity(artistId = artistId))
    }

    suspend fun update(artistIds: List<String>) {
        dao.deleteAll()
        artistIds.forEach {
            dao.insert(FavoriteArtistEntity(artistId = it))
        }
    }

    suspend fun delete(artistId: String) {
        dao.deleteByArtistId(artistId)
    }

    suspend fun deleteByIds(artistIds: List<String>) {
        dao.deleteByArtistIds(artistIds)
    }
}
