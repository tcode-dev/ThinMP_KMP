package dev.tcode.thinmpk.view.component.listItem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tcode.thinmpk.model.SongModel
import dev.tcode.thinmpk.view.component.image.ArtworkImage
import dev.tcode.thinmpk.view.text.PrimaryTextView
import dev.tcode.thinmpk.view.text.SecondaryTextView

@Composable
fun SongListItem(song: SongModel, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ArtworkImage(
            imageId = song.imageId,
            contentDescription = song.albumName,
            radius = 4.dp
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            PrimaryTextView(song.name)
            SecondaryTextView(song.artistName)
        }
    }
    HorizontalDivider()
}
