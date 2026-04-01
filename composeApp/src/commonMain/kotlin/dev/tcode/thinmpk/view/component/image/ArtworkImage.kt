package dev.tcode.thinmpk.view.component.image

import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.tcode.thinmpk.model.ArtworkModel

@Composable
fun ArtworkImage(
    imageId: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 50.dp,
) {
    val placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant)

    AsyncImage(
        model = ArtworkModel(id = imageId),
        contentDescription = contentDescription,
        modifier = modifier.size(size),
        contentScale = ContentScale.Crop,
        placeholder = placeholder,
        error = placeholder,
    )
}
