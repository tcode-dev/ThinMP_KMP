package dev.tcode.thinmpk.config

import android.content.Context
import android.content.SharedPreferences

class ConfigStore(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("thinmpk_preferences", Context.MODE_PRIVATE)

    fun getRepeat(): RepeatState {
        val values = RepeatState.entries.toTypedArray()
        val value = prefs.getInt("repeat", -1)

        return if (value in values.indices) values[value] else RepeatState.OFF
    }

    fun saveRepeat(value: RepeatState) {
        prefs.edit().putInt("repeat", value.ordinal).apply()
    }

    fun getShuffle(): Boolean {
        return prefs.getBoolean("shuffle", false)
    }

    fun saveShuffle(value: Boolean) {
        prefs.edit().putBoolean("shuffle", value).apply()
    }
}