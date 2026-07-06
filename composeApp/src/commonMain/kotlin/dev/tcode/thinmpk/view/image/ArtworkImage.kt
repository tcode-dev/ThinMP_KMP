package dev.tcode.thinmpk.view.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import dev.tcode.thinmpk.model.ArtworkModel
import org.jetbrains.compose.resources.painterResource
import thinmpk.composeapp.generated.resources.Res
import thinmpk.composeapp.generated.resources.song_dark

@Composable
fun ArtworkImage(
    imageId: String,
    modifier: Modifier = Modifier,
) {
    val placeholder = painterResource(Res.drawable.song_dark)

    AsyncImage(
        model = ArtworkModel(id = imageId),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        placeholder = placeholder,
        error = placeholder,
    )
}
