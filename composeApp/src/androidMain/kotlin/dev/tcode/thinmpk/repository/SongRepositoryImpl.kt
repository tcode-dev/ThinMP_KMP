package dev.tcode.thinmpk.repository

import android.provider.MediaStore
import dev.tcode.thinmpk.model.SongModel

class SongRepositoryImpl : MediaStoreRepository<SongModel>(
    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
    arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.ALBUM_ID,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.Audio.Media.DURATION,
    ),
), SongRepository {
    override fun findById(id: String): SongModel? {
        selection = "${MediaStore.Audio.Media._ID} = ?"
        selectionArgs = arrayOf(id)
        sortOrder = null

        return get()
    }

    override fun findAll(): List<SongModel> {
        selection = "${MediaStore.Audio.Media.IS_MUSIC} = ?"
        selectionArgs = arrayOf("1")
        sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        return getList()
    }

    override fun findByAlbumId(albumId: String): List<SongModel> {
        selection = "${MediaStore.Audio.Media.ALBUM_ID} = ?"
        selectionArgs = arrayOf(albumId)
        sortOrder = "${MediaStore.Audio.Media.TRACK} ASC"

        return getList()
    }

    override fun findByArtistId(artistId: String): List<SongModel> {
        selection = "${MediaStore.Audio.Media.ARTIST_ID} = ?"
        selectionArgs = arrayOf(artistId)
        sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        return getList()
    }

    override fun fetch(): SongModel {
        return SongModel(
            id = getId(),
            name = getTitle(),
            artistName = getArtistName(),
            albumId = getAlbumId(),
            albumName = getAlbumName(),
            imageId = getAlbumId(),
            duration = getDuration(),
        )
    }

    private fun getId(): String {
        return cursor?.getColumnIndex(MediaStore.Audio.Media._ID)?.let { cursor?.getString(it) } ?: ""
    }

    private fun getTitle(): String {
        return cursor?.getColumnIndex(MediaStore.Audio.Media.TITLE)?.let { cursor?.getString(it) } ?: ""
    }

    private fun getArtistName(): String {
        return cursor?.getColumnIndex(MediaStore.Audio.Media.ARTIST)?.let { cursor?.getString(it) } ?: ""
    }

    private fun getAlbumId(): String {
        return cursor?.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)?.let { cursor?.getString(it) } ?: ""
    }

    private fun getAlbumName(): String {
        return cursor?.getColumnIndex(MediaStore.Audio.Media.ALBUM)?.let { cursor?.getString(it) } ?: ""
    }

    private fun getDuration(): Int {
        return cursor?.getColumnIndex(MediaStore.Audio.Media.DURATION)?.let { cursor?.getInt(it) } ?: 0
    }
}
