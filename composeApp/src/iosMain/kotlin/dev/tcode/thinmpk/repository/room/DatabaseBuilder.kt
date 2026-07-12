package dev.tcode.thinmpk.repository.room

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = NSHomeDirectory() + "/Documents/thinmpk.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath
    )
}
