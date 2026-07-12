package dev.tcode.thinmpk.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(tableName = "favorite_songs")
data class FavoriteSongEntity(
    @PrimaryKey
    val id: String = @OptIn(ExperimentalUuidApi::class) Uuid.random().toString(),
    val songId: String = ""
)
