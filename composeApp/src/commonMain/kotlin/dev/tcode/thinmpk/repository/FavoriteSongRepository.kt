package dev.tcode.thinmpk.repository

import dev.tcode.thinmpk.model.room.FavoriteSongEntity
import dev.tcode.thinmpk.repository.dao.FavoriteSongDao

class FavoriteSongRepository(private val dao: FavoriteSongDao) {
    suspend fun exists(songId: String): Boolean {
        return dao.exists(songId)
    }

    suspend fun findAll(): List<String> {
        return dao.findAll().map { it.songId }
    }

    suspend fun add(songId: String) {
        dao.insert(FavoriteSongEntity(songId = songId))
    }

    suspend fun update(songIds: List<String>) {
        dao.deleteAll()
        songIds.forEach {
            dao.insert(FavoriteSongEntity(songId = it))
        }
    }

    suspend fun delete(songId: String) {
        dao.deleteBySongId(songId)
    }

    suspend fun deleteByIds(songIds: List<String>) {
        dao.deleteBySongIds(songIds)
    }
}
