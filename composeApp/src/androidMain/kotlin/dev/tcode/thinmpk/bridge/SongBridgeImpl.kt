package dev.tcode.thinmpk.bridge

import dev.tcode.thinmpk.model.SongModel
import dev.tcode.thinmpk.repository.SongRepository

class SongBridgeImpl : SongBridge {
    private val songRepository = SongRepository()

    override fun findAll(): List<SongModel> {
        return songRepository.findAll()
    }

    override fun findById(id: String): SongModel? {
        return songRepository.findById(id)
    }
}
