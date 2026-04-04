package dev.tcode.thinmpk.repository

import android.database.Cursor
import android.net.Uri
import dev.tcode.thinmpk.MainApplication

abstract class MediaStoreRepository<T>(
    private val uri: Uri,
    private val projection: Array<String>,
) {
    protected var cursor: Cursor? = null
    var selection: String? = null
    var selectionArgs: Array<String>? = null
    var sortOrder: String? = null

    abstract fun fetch(): T

    protected fun get(): T? {
        initialize()

        if (cursor?.moveToNext() != true) return null

        val item = fetch()

        destroy()

        return item
    }

    protected fun getList(): List<T> {
        initialize()

        val list = mutableListOf<T>()

        while (cursor?.moveToNext() == true) {
            list.add(fetch())
        }

        destroy()

        return list
    }

    private fun initialize() {
        val context = MainApplication.appContext

        cursor = context.contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs,
            sortOrder,
        )
    }

    private fun destroy() {
        cursor?.close()
        cursor = null
    }
}
