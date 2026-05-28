package dev.tcode.thinmpk.view.nav

import kotlinx.serialization.Serializable

@Serializable
object MainRoute

@Serializable
object ArtistsRoute

@Serializable
object AlbumsRoute

@Serializable
object SongsRoute

@Serializable
data class AlbumDetailRoute(val id: String)

@Serializable
data class ArtistDetailRoute(val id: String)
