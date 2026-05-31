package dev.tcode.thinmpk.view.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import dev.tcode.thinmpk.model.SongModel
import dev.tcode.thinmpk.view.collapsingTopAppBar.DetailCollapsingTopAppBar
import dev.tcode.thinmpk.view.component.listItem.AlbumGridItem
import dev.tcode.thinmpk.view.component.listItem.GridItem
import dev.tcode.thinmpk.view.component.listItem.SongListItem
import dev.tcode.thinmpk.view.nav.LocalNavigator
import dev.tcode.thinmpk.view.text.PlainText
import dev.tcode.thinmpk.view.text.PrimaryText
import dev.tcode.thinmpk.view.util.CustomGridCellsFixed
import dev.tcode.thinmpk.view.util.gridSpanCount
import dev.tcode.thinmpk.viewmodel.ArtistDetailViewModel

@Composable
fun ArtistDetailPage(
    id: String,
    viewModel: ArtistDetailViewModel = viewModel(factory = viewModelFactory {
        initializer {
            ArtistDetailViewModel(
                id
            )
        }
    })
) {
    val uiState by viewModel.uiState.collectAsState()
    val navigator = LocalNavigator.current
    val spanCount: Int = gridSpanCount()

    LaunchedEffect(Unit) {
        viewModel.load()
    }
    DetailCollapsingTopAppBar(
        title = uiState.artist?.name ?: "",
        columns = CustomGridCellsFixed(spanCount),
        spanCount = spanCount,
        dropdownMenus = { callback ->
        }
    ) {
        item(span = { GridItemSpan(spanCount) }) {
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
        item(span = { GridItemSpan(spanCount) }) {
            PrimaryText(
                text = uiState.artist?.name ?: "",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            )
        }
        if (uiState.albums.isNotEmpty()) {
            item(span = { GridItemSpan(spanCount) }) {
                PlainText(
                    text = "Albums",
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                )
            }
            itemsIndexed(items = uiState.albums) { index, album ->
                GridItem(index, spanCount) {
                    AlbumGridItem(album,  Modifier.clickable { navigator.albumDetail(album.id) })
                }
            }
        }
        if (uiState.songs.isNotEmpty()) {
            item(span = { GridItemSpan(spanCount) }) {
                PlainText(
                    text = "Songs",
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                )
            }
            itemsIndexed(
                uiState.songs,
                span = { _: Int, _: SongModel -> GridItemSpan(spanCount) }) { index, song ->
                SongListItem(song, Modifier.pointerInput(index) {
                    detectTapGestures(
                        onTap = { viewModel.start(index) }
                    )
                })
            }
        }
    }
}
