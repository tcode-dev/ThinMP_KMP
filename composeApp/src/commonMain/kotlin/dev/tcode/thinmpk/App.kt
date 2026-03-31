package dev.tcode.thinmpk

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.setSingletonImageLoaderFactory
import dev.tcode.thinmpk.coil.newImageLoader
import dev.tcode.thinmpk.view.page.SongsPage

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
                        Text(
                            text = "Library",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp)
                        )
                        Text(
                            text = "Songs",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navController.navigate("songs") }
                                .padding(16.dp)
                        )
                    }
                }
            }
            composable("songs") {
                SongsPage()
            }
        }
    }
}