package dev.tcode.thinmpk.repository

import dev.tcode.thinmpk.model.SongModel
import platform.MediaPlayer.MPMediaItem
import platform.MediaPlayer.MPMediaItemPropertyIsCloudItem
import platform.MediaPlayer.MPMediaPropertyPredicate
import platform.MediaPlayer.MPMediaQuery

actual class SongRepository actual constructor() {
    actual fun findAll(): List<SongModel> {
        val query = MPMediaQuery.songsQuery()
        val predicate = MPMediaPropertyPredicate.predicateWithValue(
            value = false,
            forProperty = MPMediaItemPropertyIsCloudItem
        )
        query.addFilterPredicate(predicate)

        val items = query.items ?: return emptyList()

        return items.mapNotNull { item ->
            val mediaItem = item as? MPMediaItem ?: return@mapNotNull null
            SongModel(
                id = mediaItem.persistentID.toString(),
                name = mediaItem.title ?: "",
                artistName = mediaItem.artist ?: "",
                albumId = mediaItem.albumPersistentID.toString(),
                albumName = mediaItem.albumTitle ?: "",
                duration = mediaItem.playbackDuration.toInt()
            )
        }
    }
}
