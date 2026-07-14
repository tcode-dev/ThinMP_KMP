package dev.tcode.thinmpk.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(tableName = "favorite_artists")
data class FavoriteArtistEntity(
    @PrimaryKey
    val id: String = @OptIn(ExperimentalUuidApi::class) Uuid.random().toString(),
    val artistId: String = ""
)
