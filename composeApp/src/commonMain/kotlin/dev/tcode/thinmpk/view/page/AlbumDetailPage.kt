package dev.tcode.thinmpk.view.page

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil3.compose.AsyncImage
import dev.tcode.thinmpk.model.ArtworkModel
import dev.tcode.thinmpk.view.collapsingTopAppBar.DetailCollapsingTopAppBar
import dev.tcode.thinmpk.view.component.listItem.SongListItem
import dev.tcode.thinmpk.view.text.PrimaryTextView
import dev.tcode.thinmpk.view.text.SecondaryTextView
import dev.tcode.thinmpk.viewmodel.AlbumDetailViewModel

@Composable
fun AlbumDetailPage(
    albumId: String,
    viewModel: AlbumDetailViewModel = viewModel(factory = viewModelFactory { initializer { AlbumDetailViewModel(albumId) } })
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    DetailCollapsingTopAppBar(uiState.album?.name ?: "") {
        item {
            val placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant)

            AsyncImage(
                model = ArtworkModel(id = uiState.album?.imageId ?: ""),
                contentDescription = uiState.album?.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                placeholder = placeholder,
                error = placeholder,
            )
        }
        item {
            PrimaryTextView(
                text = uiState.album?.name ?: "",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            )
            SecondaryTextView(
                text = uiState.album?.artistName ?: "",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            )
        }
        itemsIndexed(uiState.songs) { index, song ->
            SongListItem(song, Modifier.pointerInput(index) {
                detectTapGestures(
                    onTap = { viewModel.start(index) }
                )
            })
        }
    }
}
