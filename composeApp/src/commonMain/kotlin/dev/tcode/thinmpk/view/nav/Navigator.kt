package dev.tcode.thinmpk.view.nav

import androidx.navigation.NavController

interface INavigator {
    fun back()
    fun artists()
    fun albums()
    fun songs()
    fun artistDetail(id: String)
    fun albumDetail(id: String)
    fun player()
}

class Navigator(private val navController: NavController) : INavigator {
    override fun back() {
        navController.popBackStack()
    }

    override fun artists() {
        navController.navigate(ArtistsRoute)
    }

    override fun albums() {
        navController.navigate(AlbumsRoute)
    }

    override fun songs() {
        navController.navigate(SongsRoute)
    }

    override fun artistDetail(id: String) {
        navController.navigate(ArtistDetailRoute(id))
    }

    override fun albumDetail(id: String) {
        navController.navigate(AlbumDetailRoute(id))
    }

    override fun player() {
        navController.navigate(PlayerRoute)
    }
}
