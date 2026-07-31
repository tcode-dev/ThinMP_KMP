package dev.tcode.thinmpk.view.listItem

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import dev.tcode.thinmpk.constant.StyleConstant
import dev.tcode.thinmpk.model.SongModel
import dev.tcode.thinmpk.view.image.ArtworkImage
import dev.tcode.thinmpk.view.text.PrimaryText
import dev.tcode.thinmpk.view.text.SecondaryText

@Composable
fun SongListItem(
    song: SongModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    menuContent: (@Composable ColumnScope.(dismiss: () -> Unit) -> Unit)? = null,
) {
    val density = LocalDensity.current
    val haptic = LocalHapticFeedback.current
    val currentOnClick by rememberUpdatedState(onClick)
    val hasMenu = menuContent != null
    var expanded by remember { mutableStateOf(false) }
    var menuOffset by remember { mutableStateOf(DpOffset.Zero) }

    Box(modifier) {
        Column(
            Modifier
                .height(StyleConstant.ROW_HEIGHT.dp)
                .pointerInput(hasMenu) {
                    detectTapGestures(
                        onTap = { currentOnClick() },
                        onLongPress = { offset ->
                            if (hasMenu) {
                                // DropdownMenu の offset は行の下端が基準なので、行の高さ分だけ引いて指の位置に合わせる
                                menuOffset = with(density) {
                                    DpOffset(
                                        offset.x.toDp(),
                                        offset.y.toDp() - StyleConstant.ROW_HEIGHT.dp,
                                    )
                                }
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                expanded = true
                            }
                        },
                    )
                }
        ) {
            Row(
                modifier = Modifier
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

        if (menuContent != null) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = menuOffset,
            ) {
                menuContent { expanded = false }
            }
        }
    }
}
