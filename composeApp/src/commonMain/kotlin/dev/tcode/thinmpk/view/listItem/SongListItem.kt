package dev.tcode.thinmpk.view.listItem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.tcode.thinmpk.constant.StyleConstant
import dev.tcode.thinmpk.model.SongModel
import dev.tcode.thinmpk.view.image.ArtworkImage
import dev.tcode.thinmpk.view.text.PrimaryText
import dev.tcode.thinmpk.view.text.SecondaryText

@Composable
fun SongListItem(song: SongModel, modifier: Modifier = Modifier) {
    Column(
        Modifier.height(StyleConstant.ROW_HEIGHT.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ArtworkImage(
                imageId = song.imageId,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(4.dp)),
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                PrimaryText(song.name)
                SecondaryText(song.artistName)
            }
        }
        HorizontalDivider()
    }
}
