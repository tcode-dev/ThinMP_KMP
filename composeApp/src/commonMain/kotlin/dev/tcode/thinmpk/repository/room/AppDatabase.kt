package dev.tcode.thinmpk.repository.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import dev.tcode.thinmpk.model.room.FavoriteArtistEntity
import dev.tcode.thinmpk.model.room.FavoriteSongEntity
import dev.tcode.thinmpk.repository.dao.FavoriteArtistDao
import dev.tcode.thinmpk.repository.dao.FavoriteSongDao

@Database(entities = [FavoriteSongEntity::class, FavoriteArtistEntity::class], version = 2)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteSongDao(): FavoriteSongDao
    abstract fun favoriteArtistDao(): FavoriteArtistDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase>
