package dev.tcode.thinmpk.view.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.tcode.thinmpk.constant.StyleConstant
import dev.tcode.thinmpk.view.button.BackButton
import dev.tcode.thinmpk.view.collapsingAppBar.detailSize
import dev.tcode.thinmpk.view.image.ArtworkImage
import dev.tcode.thinmpk.view.text.PrimaryTitle
import dev.tcode.thinmpk.view.text.SecondaryTitle
import dev.tcode.thinmpk.viewmodel.PlayerViewModel

@Composable
fun PlayerPage(
    viewModel: PlayerViewModel = viewModel(factory = viewModelFactory { initializer { PlayerViewModel() } })
) {
    val uiState by viewModel.uiState.collectAsState()
    val (size, gradientHeight, primaryTitlePosition, secondaryTitlePosition) = detailSize()
    val imageSize: Dp = size / 2

    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(size)
        ) {
            ArtworkImage(
                imageId = uiState.imageId,
                contentDescription = uiState.primaryText,
                modifier = Modifier.fillMaxWidth().blur(20.dp),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(gradientHeight)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            0.0f to MaterialTheme.colorScheme.background.copy(alpha = 0F),
                            1.0F to MaterialTheme.colorScheme.background,
                        )
                    ),
            ) {}
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                ArtworkImage(
                    imageId = uiState.imageId,
                    contentDescription = uiState.primaryText,
                    modifier = Modifier.size(imageSize),
                    radius = 4.dp,
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(StyleConstant.ROW_HEIGHT.dp)
                    .offset(y = primaryTitlePosition),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                PrimaryTitle(uiState.primaryText)
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .offset(y = secondaryTitlePosition),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                SecondaryTitle(uiState.secondaryText)
            }
            Row(
                modifier = Modifier
                    .statusBarsPadding()
                    .height(StyleConstant.ROW_HEIGHT.dp)
                    .padding(start = StyleConstant.PADDING_TINY.dp)
            ) {
                BackButton()
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .clip(RoundedCornerShape(StyleConstant.IMAGE_CORNER_SIZE.dp))
                    .clickable { viewModel.prev() }) {
                Icon(
                    imageVector = Icons.Rounded.SkipPrevious,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(72.dp)
                )
            }
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .clip(RoundedCornerShape(StyleConstant.IMAGE_CORNER_SIZE.dp))
                    .clickable { viewModel.toggle() }) {
                Icon(
                    imageVector = if (uiState.isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(88.dp)
                )
            }
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .clip(RoundedCornerShape(StyleConstant.IMAGE_CORNER_SIZE.dp))
                    .clickable { viewModel.next() }) {
                Icon(
                    imageVector = Icons.Rounded.SkipNext,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(72.dp)
                )
            }
        }
    }
}
