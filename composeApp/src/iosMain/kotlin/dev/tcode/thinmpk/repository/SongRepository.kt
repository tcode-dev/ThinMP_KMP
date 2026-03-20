package dev.tcode.thinmpk.repository

import dev.tcode.thinmpk.model.SongModel
import platform.MediaPlayer.MPMediaItem
import platform.MediaPlayer.MPMediaItemCollection
import platform.MediaPlayer.MPMediaItemPropertyIsCloudItem
import platform.MediaPlayer.MPMediaPropertyPredicate
import platform.MediaPlayer.MPMediaQuery

actual class SongRepository actual constructor() {
    fun findMediaItemById(id: String): MPMediaItem? {
        val query = MPMediaQuery.songsQuery()
        query.addFilterPredicate(
            MPMediaPropertyPredicate.predicateWithValue(
                value = false,
                forProperty = MPMediaItemPropertyIsCloudItem
            )
        )

        val collections = query.collections ?: return null
        val collection = collections.firstOrNull { c ->
            val mediaCollection = c as? MPMediaItemCollection ?: return@firstOrNull false
            id == mediaCollection.representativeItem?.persistentID?.toString()
        } as? MPMediaItemCollection ?: return null

        return collection.representativeItem
    }

    actual fun findById(id: String): SongModel? {
        val mediaItem = findMediaItemById(id) ?: return null

        return SongModel(
            id = mediaItem.persistentID.toString(),
            name = mediaItem.title ?: "",
            artistName = mediaItem.artist ?: "",
            albumId = mediaItem.albumPersistentID.toString(),
            albumName = mediaItem.albumTitle ?: "",
            imageId = mediaItem.persistentID.toString(),
            duration = mediaItem.playbackDuration.toInt()
        )
    }

    actual fun findAll(): List<SongModel> {
        val query = MPMediaQuery.songsQuery()
        val predicate = MPMediaPropertyPredicate.predicateWithValue(
            value = false,
            forProperty = MPMediaItemPropertyIsCloudItem
        )
        query.addFilterPredicate(predicate)

        val collections = query.collections ?: return emptyList()

        return collections.mapNotNull { collection ->
            val mediaCollection = collection as? MPMediaItemCollection ?: return@mapNotNull null
            val mediaItem = mediaCollection.representativeItem ?: return@mapNotNull null
            SongModel(
                id = mediaItem.persistentID.toString(),
                name = mediaItem.title ?: "",
                artistName = mediaItem.artist ?: "",
                albumId = mediaItem.albumPersistentID.toString(),
                albumName = mediaItem.albumTitle ?: "",
                imageId = mediaItem.persistentID.toString(),
                duration = mediaItem.playbackDuration.toInt()
            )
        }
    }
}
