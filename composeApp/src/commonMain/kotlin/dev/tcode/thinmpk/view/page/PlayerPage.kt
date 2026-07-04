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
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.automirrored.rounded.PlaylistAdd
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
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

@OptIn(ExperimentalMaterial3Api::class)
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
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize().navigationBarsPadding()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 30.dp)
            ) {
                Slider(
                    value = uiState.sliderPosition,
                    onValueChange = { viewModel.seek(it) },
                    onValueChangeFinished = { viewModel.seekFinished() },
                    thumb = {
                        SliderDefaults.Thumb(
                            interactionSource = remember { MutableInteractionSource() },
                            colors = SliderDefaults.colors(thumbColor = MaterialTheme.colorScheme.onSurface),
                            thumbSize = DpSize(20.dp, 20.dp)
                        )
                    },
                    track = { sliderState ->
                        SliderDefaults.Track(
                            sliderState = sliderState,
                            colors = SliderDefaults.colors(activeTrackColor = MaterialTheme.colorScheme.onSurface),
                            trackInsideCornerSize = 0.dp,
                            thumbTrackGapSize = 0.dp,
                            modifier = Modifier.height(10.dp)
                        )
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = StyleConstant.PADDING_SMALL.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(uiState.currentTime, color = MaterialTheme.colorScheme.secondary)
                    Text(uiState.durationTime, color = MaterialTheme.colorScheme.secondary)
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
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(StyleConstant.BUTTON_SIZE.dp)
                        .clip(RoundedCornerShape(StyleConstant.IMAGE_CORNER_SIZE.dp))
                        .clickable { }) {
                    Icon(
                        imageVector = Icons.Rounded.Repeat,
                        contentDescription = null,
                        modifier = Modifier.size(StyleConstant.IMAGE_SIZE.dp),
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(StyleConstant.BUTTON_SIZE.dp)
                        .clip(RoundedCornerShape(StyleConstant.IMAGE_CORNER_SIZE.dp))
                        .clickable { }) {
                    Icon(
                        imageVector = Icons.Rounded.Shuffle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                        modifier = Modifier.size(StyleConstant.IMAGE_SIZE.dp)
                    )
                }
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .size(StyleConstant.BUTTON_SIZE.dp)
                        .clip(RoundedCornerShape(StyleConstant.IMAGE_CORNER_SIZE.dp))
                        .clickable {}) {
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                        modifier = Modifier.size(StyleConstant.IMAGE_SIZE.dp)
                    )
                }
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .size(StyleConstant.BUTTON_SIZE.dp)
                        .clip(RoundedCornerShape(StyleConstant.IMAGE_CORNER_SIZE.dp))
                        .clickable {}) {
                    Icon(
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                        modifier = Modifier.size(StyleConstant.IMAGE_SIZE.dp)
                    )
                }
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .size(StyleConstant.BUTTON_SIZE.dp)
                        .clip(RoundedCornerShape(StyleConstant.IMAGE_CORNER_SIZE.dp))
                        .clickable {}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.PlaylistAdd,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(StyleConstant.IMAGE_SIZE.dp)
                    )
                }
            }
        }
    }
}
