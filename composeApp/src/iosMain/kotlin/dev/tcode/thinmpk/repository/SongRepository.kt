package dev.tcode.thinmpk.repository

import dev.tcode.thinmpk.model.SongModel

actual class SongRepository actual constructor() {
    actual fun findAll(): List<SongModel> {
        // TODO: Implement iOS song fetching using MPMediaQuery
        return emptyList()
    }
}
