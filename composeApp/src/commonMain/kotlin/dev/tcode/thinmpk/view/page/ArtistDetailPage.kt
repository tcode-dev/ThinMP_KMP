package dev.tcode.thinmpk.view.page

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import dev.tcode.thinmpk.view.component.listItem.AlbumGridItem
import dev.tcode.thinmpk.view.component.listItem.SongListItem
import dev.tcode.thinmpk.view.text.PlainTextView
import dev.tcode.thinmpk.view.text.PrimaryTextView
import dev.tcode.thinmpk.viewmodel.ArtistDetailViewModel

@Composable
fun ArtistDetailPage(
    artistId: String,
    onAlbumClick: ((String) -> Unit)? = null,
    viewModel: ArtistDetailViewModel = viewModel(factory = viewModelFactory { initializer { ArtistDetailViewModel(artistId) } })
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    DetailCollapsingTopAppBar(uiState.artist?.name ?: "") {
        item {
            val placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant)

            AsyncImage(
                model = ArtworkModel(id = uiState.artist?.imageId ?: ""),
                contentDescription = uiState.artist?.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 80.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = placeholder,
                error = placeholder,
            )
        }
        item {
            PrimaryTextView(
                text = uiState.artist?.name ?: "",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            )
        }
        if (uiState.albums.isNotEmpty()) {
            item {
                PlainTextView(
                    text = "Albums",
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                )
            }
            itemsIndexed(uiState.albums) { _, album ->
                AlbumGridItem(album, Modifier.pointerInput(album.id) {
                    detectTapGestures(
                        onTap = { onAlbumClick?.invoke(album.id) }
                    )
                }.padding(horizontal = 16.dp, vertical = 4.dp))
            }
        }
        if (uiState.songs.isNotEmpty()) {
            item {
                PlainTextView(
                    text = "Songs",
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
}
