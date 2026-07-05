package dev.tcode.thinmpk.view.image

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import dev.tcode.thinmpk.model.ArtworkModel

@Composable
fun ArtworkImage(
    imageId: String,
    modifier: Modifier = Modifier,
) {
    val placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant)

    AsyncImage(
        model = ArtworkModel(id = imageId),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        placeholder = placeholder,
        error = placeholder,
    )
}
