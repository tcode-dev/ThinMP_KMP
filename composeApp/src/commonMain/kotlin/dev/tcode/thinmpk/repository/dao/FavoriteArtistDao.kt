package dev.tcode.thinmpk.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.tcode.thinmpk.model.room.FavoriteArtistEntity

@Dao
interface FavoriteArtistDao {
    @Query("SELECT * FROM favorite_artists")
    suspend fun findAll(): List<FavoriteArtistEntity>

    @Query("SELECT COUNT(*) > 0 FROM favorite_artists WHERE artistId = :artistId")
    suspend fun exists(artistId: String): Boolean

    @Insert
    suspend fun insert(entity: FavoriteArtistEntity)

    @Query("DELETE FROM favorite_artists")
    suspend fun deleteAll()

    @Query("DELETE FROM favorite_artists WHERE artistId = :artistId")
    suspend fun deleteByArtistId(artistId: String)

    @Query("DELETE FROM favorite_artists WHERE artistId IN (:artistIds)")
    suspend fun deleteByArtistIds(artistIds: List<String>)
}
