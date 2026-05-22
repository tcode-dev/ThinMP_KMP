package dev.tcode.thinmpk

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.savedstate.read
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.setSingletonImageLoaderFactory
import dev.tcode.thinmpk.coil.newImageLoader
import dev.tcode.thinmpk.view.page.AlbumDetailPage
import dev.tcode.thinmpk.view.page.AlbumsPage
import dev.tcode.thinmpk.view.page.ArtistDetailPage
import dev.tcode.thinmpk.view.page.ArtistsPage
import dev.tcode.thinmpk.view.page.SongsPage
import dev.tcode.thinmpk.view.text.PlainTextView
import dev.tcode.thinmpk.view.text.PrimaryTextView

@Composable
fun App() {
    setSingletonImageLoaderFactory { context ->
        newImageLoader(context)
    }

    val colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    MaterialTheme(colorScheme = colorScheme) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                Scaffold { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        PlainTextView(
                            text = "Library",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp)
                        )
                        PrimaryTextView(
                            text = "Songs",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navController.navigate("songs") }
                                .padding(16.dp)
                        )
                        PrimaryTextView(
                            text = "Artists",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navController.navigate("artists") }
                                .padding(16.dp)
                        )
                        PrimaryTextView(
                            text = "Albums",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navController.navigate("albums") }
                                .padding(16.dp)
                        )
                    }
                }
            }
            composable("songs") {
                SongsPage()
            }
            composable("artists") {
                ArtistsPage(onArtistClick = { artistId -> navController.navigate("artistDetail/$artistId") })
            }
            composable("artistDetail/{artistId}") { backStackEntry ->
                val artistId = backStackEntry.arguments?.read { getStringOrNull("artistId") } ?: ""
                ArtistDetailPage(
                    artistId = artistId,
                    onAlbumClick = { albumId -> navController.navigate("albumDetail/$albumId") },
                )
            }
            composable("albums") {
                AlbumsPage(onAlbumClick = { albumId -> navController.navigate("albumDetail/$albumId") })
            }
            composable("albumDetail/{albumId}") { backStackEntry ->
                val albumId = backStackEntry.arguments?.read { getStringOrNull("albumId") } ?: ""
                AlbumDetailPage(albumId = albumId)
            }
        }
    }
}