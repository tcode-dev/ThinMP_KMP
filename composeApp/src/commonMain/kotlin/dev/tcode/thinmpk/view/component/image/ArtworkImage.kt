package dev.tcode.thinmpk.view.component.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.tcode.thinmpk.bridge.ArtworkBridge
import org.koin.compose.koinInject

@Composable
fun ArtworkImage(
    imageId: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 50.dp,
    artworkBridge: ArtworkBridge = koinInject(),
) {
    var artwork by remember(imageId) { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(imageId) {
        artwork = artworkBridge.getArtwork(imageId)
    }

    if (artwork != null) {
        Image(
            bitmap = artwork!!,
            contentDescription = contentDescription,
            modifier = modifier.size(size),
            contentScale = ContentScale.Crop,
        )
    } else {
        Box(
            modifier = modifier
                .size(size)
                .background(MaterialTheme.colorScheme.surfaceVariant),
        )
    }
}
