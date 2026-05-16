package dev.tcode.thinmpk.repository

import android.provider.MediaStore
import dev.tcode.thinmpk.model.ArtistModel

class ArtistRepositoryImpl : MediaStoreRepository<ArtistModel>(
    MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
    arrayOf(
        MediaStore.Audio.Artists._ID,
        MediaStore.Audio.Artists.ARTIST,
    ),
), ArtistRepository {
    override fun findAll(): List<ArtistModel> {
        selection = null
        selectionArgs = null
        sortOrder = "${MediaStore.Audio.Artists.ARTIST} ASC"

        return getList()
    }

    override fun findById(id: String): ArtistModel? {
        selection = "${MediaStore.Audio.Artists._ID} = ?"
        selectionArgs = arrayOf(id)
        sortOrder = null

        return get()
    }

    override fun fetch(): ArtistModel {
        return ArtistModel(
            id = getId(),
            name = getArtistName(),
            imageId = getId(),
        )
    }

    private fun getId(): String {
        return cursor?.getColumnIndex(MediaStore.Audio.Artists._ID)?.let { cursor?.getString(it) } ?: ""
    }

    private fun getArtistName(): String {
        return cursor?.getColumnIndex(MediaStore.Audio.Artists.ARTIST)?.let { cursor?.getString(it) } ?: ""
    }
}
