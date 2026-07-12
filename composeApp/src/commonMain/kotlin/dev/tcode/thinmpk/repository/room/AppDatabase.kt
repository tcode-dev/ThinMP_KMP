package dev.tcode.thinmpk.repository.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import dev.tcode.thinmpk.model.room.FavoriteSongEntity
import dev.tcode.thinmpk.repository.dao.FavoriteSongDao

@Database(entities = [FavoriteSongEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteSongDao(): FavoriteSongDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase>
