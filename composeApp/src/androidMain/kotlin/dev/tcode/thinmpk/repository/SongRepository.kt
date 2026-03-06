package dev.tcode.thinmpk.repository

import android.provider.MediaStore
import dev.tcode.thinmpk.MainApplication
import dev.tcode.thinmpk.model.SongModel

actual class SongRepository actual constructor() {
    actual fun findAll(): List<SongModel> {
        val context = MainApplication.appContext
        val songs = mutableListOf<SongModel>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION,
        )
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} = ?"
        val selectionArgs = arrayOf("1")
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        context.contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)
            ?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
                val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

                while (cursor.moveToNext()) {
                    songs.add(
                        SongModel(
                            id = cursor.getLong(idColumn).toString(),
                            name = cursor.getString(titleColumn) ?: "",
                            artistName = cursor.getString(artistColumn) ?: "",
                            albumId = cursor.getLong(albumIdColumn).toString(),
                            albumName = cursor.getString(albumColumn) ?: "",
                            duration = cursor.getInt(durationColumn),
                        )
                    )
                }
            }

        return songs
    }
}
