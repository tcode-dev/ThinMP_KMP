package dev.tcode.thinmpk.repository

import dev.tcode.thinmpk.model.AlbumModel

interface AlbumRepository {
    fun findAll(): List<AlbumModel>
}
