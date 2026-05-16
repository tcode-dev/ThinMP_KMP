package dev.tcode.thinmpk.repository

import android.provider.MediaStore
import dev.tcode.thinmpk.model.AlbumModel

class AlbumRepositoryImpl : MediaStoreRepository<AlbumModel>(
    MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
    arrayOf(
        MediaStore.Audio.Albums._ID,
        MediaStore.Audio.Albums.ALBUM,
        MediaStore.Audio.Albums.ARTIST,
    ),
), AlbumRepository {
    override fun findAll(): List<AlbumModel> {
        selection = null
        selectionArgs = null
        sortOrder = "${MediaStore.Audio.Albums.ALBUM} ASC"

        return getList()
    }

    override fun findById(id: String): AlbumModel? {
        selection = "${MediaStore.Audio.Albums._ID} = ?"
        selectionArgs = arrayOf(id)
        sortOrder = null

        return get()
    }

    override fun findByArtistId(artistId: String): List<AlbumModel> {
        selection = "${MediaStore.Audio.Albums.ARTIST_ID} = ?"
        selectionArgs = arrayOf(artistId)
        sortOrder = "${MediaStore.Audio.Albums.ALBUM} ASC"

        return getList()
    }

    override fun fetch(): AlbumModel {
        return AlbumModel(
            id = getId(),
            name = getAlbumName(),
            artistName = getArtistName(),
            imageId = getId(),
        )
    }

    private fun getId(): String {
        return cursor?.getColumnIndex(MediaStore.Audio.Albums._ID)?.let { cursor?.getString(it) } ?: ""
    }

    private fun getAlbumName(): String {
        return cursor?.getColumnIndex(MediaStore.Audio.Albums.ALBUM)?.let { cursor?.getString(it) } ?: ""
    }

    private fun getArtistName(): String {
        return cursor?.getColumnIndex(MediaStore.Audio.Albums.ARTIST)?.let { cursor?.getString(it) } ?: ""
    }
}
