package dev.tcode.thinmpk.config

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ConfigStore internal constructor(private val dataStore: DataStore<Preferences>) {
    private val repeatKey = intPreferencesKey("repeat")
    private val shuffleKey = booleanPreferencesKey("shuffle")

    val repeat: Flow<RepeatState> = dataStore.data.map { RepeatState.fromValue(it[repeatKey]) }
    val shuffle: Flow<Boolean> = dataStore.data.map { it[shuffleKey] ?: false }

    suspend fun getRepeat(): RepeatState {
        return repeat.first()
    }

    suspend fun saveRepeat(value: RepeatState) {
        dataStore.edit { it[repeatKey] = value.value }
    }

    suspend fun getShuffle(): Boolean {
        return shuffle.first()
    }

    suspend fun saveShuffle(value: Boolean) {
        dataStore.edit { it[shuffleKey] = value }
    }

    companion object {
        // DataStore は同一ファイルに対して 1 インスタンスしか作れないため、ここで一元管理する
        val instance: ConfigStore by lazy { ConfigStore(createDataStore()) }
    }
}
