package dev.tcode.thinmpk.view.image

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    size: Dp? = null,
    radius: Dp = 0.dp,
) {
    val placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant)
    val sizeModifier = if (size != null) modifier.size(size) else modifier

    AsyncImage(
        model = ArtworkModel(id = imageId),
        contentDescription = contentDescription,
        modifier = sizeModifier.clip(RoundedCornerShape(radius)),
        contentScale = ContentScale.Crop,
        placeholder = placeholder,
        error = placeholder,
    )
}
