package dev.tcode.thinmpk.view.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.tcode.thinmpk.view.page.AlbumDetailPage
import dev.tcode.thinmpk.view.page.AlbumsPage
import dev.tcode.thinmpk.view.page.ArtistDetailPage
import dev.tcode.thinmpk.view.page.ArtistsPage
import dev.tcode.thinmpk.view.page.MainPage
import dev.tcode.thinmpk.view.page.SongsPage

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavigator provides Navigator(navController)) {
        NavHost(navController = navController, startDestination = MainRoute) {
            composable<MainRoute> { MainPage() }
            composable<ArtistsRoute> { ArtistsPage() }
            composable<AlbumsRoute> { AlbumsPage() }
            composable<SongsRoute> { SongsPage() }
            composable<AlbumDetailRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<AlbumDetailRoute>()
                AlbumDetailPage(id = route.id)
            }
            composable<ArtistDetailRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<ArtistDetailRoute>()
                ArtistDetailPage(id = route.id)
            }
        }
    }
}
