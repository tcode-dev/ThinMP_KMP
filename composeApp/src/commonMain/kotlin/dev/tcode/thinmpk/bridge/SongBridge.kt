package dev.tcode.thinmpk.bridge

import dev.tcode.thinmpk.model.SongModel

interface SongBridge {
    fun findAll(): List<SongModel>
    fun findById(id: String): SongModel?
}
