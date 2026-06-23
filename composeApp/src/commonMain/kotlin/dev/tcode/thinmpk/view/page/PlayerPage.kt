package dev.tcode.thinmpk.view.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.tcode.thinmpk.constant.StyleConstant
import dev.tcode.thinmpk.view.button.BackButton
import dev.tcode.thinmpk.view.text.PrimaryTitle
import dev.tcode.thinmpk.view.text.SecondaryTitle
import dev.tcode.thinmpk.viewmodel.PlayerViewModel

@Composable
fun PlayerPage(
    viewModel: PlayerViewModel = viewModel(factory = viewModelFactory { initializer { PlayerViewModel() } })
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            BackButton()

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    PrimaryTitle(uiState.primaryText)
                    SecondaryTitle(uiState.secondaryText)
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
                            tint = MaterialTheme.colorScheme.primary,
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
                            tint = MaterialTheme.colorScheme.primary,
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
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(72.dp)
                        )
                    }
                }
            }
        }
    }
}
