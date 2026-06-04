package dev.tcode.thinmpk.view.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.tcode.thinmpk.view.image.ArtworkImage
import dev.tcode.thinmpk.constant.StyleConstant
import dev.tcode.thinmpk.model.SongModel
import dev.tcode.thinmpk.view.collapsingTopAppBar.DetailCollapsingTopAppBar
import dev.tcode.thinmpk.view.collapsingTopAppBar.detailSize
import dev.tcode.thinmpk.view.listItem.AlbumGridItem
import dev.tcode.thinmpk.view.listItem.GridItem
import dev.tcode.thinmpk.view.listItem.SongListItem
import dev.tcode.thinmpk.view.nav.LocalNavigator
import dev.tcode.thinmpk.view.text.PlainText
import dev.tcode.thinmpk.view.text.PrimaryText
import dev.tcode.thinmpk.view.text.PrimaryTitle
import dev.tcode.thinmpk.view.text.SecondaryTitle
import dev.tcode.thinmpk.view.util.CustomGridCellsFixed
import dev.tcode.thinmpk.view.util.gridSpanCount
import dev.tcode.thinmpk.viewmodel.ArtistDetailViewModel
import kotlin.invoke

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
    val (size, gradientHeight, primaryTitlePosition, secondaryTitlePosition) = detailSize()
    val imageSize: Dp = size / 2

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
//        item(span = { GridItemSpan(spanCount) }) {
//            val placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant)
//
//            AsyncImage(
//                model = ArtworkModel(id = uiState.artist?.imageId ?: ""),
//                contentDescription = uiState.artist?.name,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 80.dp)
//                    .aspectRatio(1f)
//                    .clip(CircleShape),
//                contentScale = ContentScale.Crop,
//                placeholder = placeholder,
//                error = placeholder,
//            )
//        }
//        item(span = { GridItemSpan(spanCount) }) {
//            PrimaryText(
//                text = uiState.artist?.name ?: "",
//                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
//            )
//        }
        item(span = { GridItemSpan(spanCount) }) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(size)
            ) {
                ArtworkImage(
                    imageId = uiState.imageId ?: "",
                    contentDescription = uiState.artist?.name,
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
                        imageId = uiState.imageId ?: "",
                        contentDescription = uiState.artist?.name,
                        modifier = Modifier
                            .size(imageSize)
                            .clip(CircleShape)
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
                    PrimaryTitle(uiState.artist?.name ?: "")
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                        .offset(y = secondaryTitlePosition),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    SecondaryTitle(uiState.artist?.name ?: "")
                }
            }
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
                    AlbumGridItem(album, Modifier.clickable { navigator.albumDetail(album.id) })
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
