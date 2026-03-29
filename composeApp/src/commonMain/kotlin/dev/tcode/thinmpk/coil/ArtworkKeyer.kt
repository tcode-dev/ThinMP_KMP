package dev.tcode.thinmpk.coil

import coil3.key.Keyer
import coil3.request.Options
import dev.tcode.thinmpk.model.ArtworkModel

class ArtworkKeyer : Keyer<ArtworkModel> {
    override fun key(data: ArtworkModel, options: Options): String {
        return "artwork_${data.id}"
    }
}
