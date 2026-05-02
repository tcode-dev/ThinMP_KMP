package dev.tcode.thinmpk.view.component.listItem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.tcode.thinmpk.model.AlbumModel
import dev.tcode.thinmpk.model.ArtworkModel
import dev.tcode.thinmpk.view.text.PrimaryTextView
import dev.tcode.thinmpk.view.text.SecondaryTextView

@Composable
fun AlbumGridItem(album: AlbumModel, modifier: Modifier = Modifier) {
    val placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant)

    Column(modifier = modifier) {
        AsyncImage(
            model = ArtworkModel(id = album.imageId),
            contentDescription = album.name,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop,
            placeholder = placeholder,
            error = placeholder,
        )
        PrimaryTextView(
            text = album.name,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
        )
        SecondaryTextView(
            text = album.artistName,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
