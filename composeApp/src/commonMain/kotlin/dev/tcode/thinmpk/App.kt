package dev.tcode.thinmpk

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.tcode.thinmpk.view.page.SongsPage

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
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
            composable("songs") {
                SongsPage()
            }
        }
    }
}