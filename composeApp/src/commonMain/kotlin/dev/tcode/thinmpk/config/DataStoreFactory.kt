package dev.tcode.thinmpk.config

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

internal const val DATA_STORE_FILE_NAME = "thinmpk.preferences_pb"

internal expect fun getDataStorePath(): String

internal fun createDataStore(): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { getDataStorePath().toPath() }
    )
}
