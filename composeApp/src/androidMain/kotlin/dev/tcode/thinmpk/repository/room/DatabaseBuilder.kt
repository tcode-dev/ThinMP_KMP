package dev.tcode.thinmpk.repository.room

import androidx.room.Room
import androidx.room.RoomDatabase
import dev.tcode.thinmpk.MainApplication

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val context = MainApplication.appContext
    val dbFile = context.getDatabasePath("thinmpk.db")
    return Room.databaseBuilder<AppDatabase>(
        context = context,
        name = dbFile.absolutePath
    )
}
