package dev.tcode.thinmpk.view.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.SkipNext
import dev.tcode.thinmpk.constant.StyleConstant
import dev.tcode.thinmpk.view.image.ArtworkImage
import dev.tcode.thinmpk.view.nav.LocalNavigator
import dev.tcode.thinmpk.view.text.PrimaryText
import dev.tcode.thinmpk.viewmodel.MiniPlayerViewModel

@Composable
fun MiniPlayer(
    viewModel: MiniPlayerViewModel = viewModel(factory = viewModelFactory { initializer { MiniPlayerViewModel() } })
) {
    val uiState by viewModel.uiState.collectAsState()
    val navigator = LocalNavigator.current

    if (!uiState.isVisible) {
        return
    }

    Row(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(StyleConstant.ROW_HEIGHT.dp)
            .padding(StyleConstant.PADDING_TINY.dp)
            .clickable {
//                navigator.player()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        ArtworkImage(
            imageId = uiState.imageId,
            contentDescription = uiState.primaryText,
            size = 40.dp,
            radius = 4.dp
        )
        Box(
            modifier = Modifier
                .padding(start = StyleConstant.PADDING_SMALL.dp)
                .weight(1f)
        ) {
            PrimaryText(text = uiState.primaryText)
        }
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .size(StyleConstant.BUTTON_SIZE.dp)
                .clip(RoundedCornerShape(StyleConstant.IMAGE_CORNER_SIZE.dp))
                .clickable { viewModel.toggle() }) {
            Icon(
                imageVector = if (uiState.isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(StyleConstant.IMAGE_SIZE.dp)
            )
        }
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .size(StyleConstant.BUTTON_SIZE.dp)
                .clip(RoundedCornerShape(StyleConstant.IMAGE_CORNER_SIZE.dp))
                .clickable { viewModel.next() }) {
            Icon(
                imageVector = Icons.Rounded.SkipNext,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(StyleConstant.IMAGE_SIZE.dp)
            )
        }
    }
}