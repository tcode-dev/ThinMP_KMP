package dev.tcode.thinmpk

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import coil3.compose.setSingletonImageLoaderFactory
import dev.tcode.thinmpk.coil.newImageLoader
import dev.tcode.thinmpk.view.page.MainPage

@Composable
fun App() {
    setSingletonImageLoaderFactory { context ->
        newImageLoader(context)
    }

    val colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    MaterialTheme(colorScheme = colorScheme) {
        MainPage()
    }
}
