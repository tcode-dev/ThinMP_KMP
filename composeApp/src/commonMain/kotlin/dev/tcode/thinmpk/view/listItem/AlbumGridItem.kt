package dev.tcode.thinmpk.view.listItem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.tcode.thinmpk.model.AlbumModel
import dev.tcode.thinmpk.view.image.ArtworkImage
import dev.tcode.thinmpk.view.text.PrimaryGridText
import dev.tcode.thinmpk.view.text.SecondaryGridText

@Composable
fun AlbumGridItem(album: AlbumModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ArtworkImage(
            imageId = album.imageId,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(4.dp)),
        )
        PrimaryGridText(text = album.name)
        SecondaryGridText(text = album.artistName)
    }
}
