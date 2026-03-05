package dev.tcode.thinmpk.repository

import dev.tcode.thinmpk.model.SongModel

expect class SongRepository() {
    fun findAll(): List<SongModel>
}
