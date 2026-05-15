package dev.tcode.thinmpk.repository

import dev.tcode.thinmpk.model.ArtistModel

interface ArtistRepository {
    fun findAll(): List<ArtistModel>
    fun findById(id: String): ArtistModel?
}
