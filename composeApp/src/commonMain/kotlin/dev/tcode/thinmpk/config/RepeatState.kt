package dev.tcode.thinmpk.config

enum class RepeatState(val value: Int) {
    OFF(0), ONE(1), ALL(2);

    companion object {
        fun fromValue(value: Int?): RepeatState = entries.find { it.value == value } ?: OFF
    }
}
