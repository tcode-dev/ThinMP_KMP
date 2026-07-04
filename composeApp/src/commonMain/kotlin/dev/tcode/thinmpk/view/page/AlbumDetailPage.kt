package dev.tcode.thinmpk.view.page

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.offset
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.tcode.thinmpk.view.image.ArtworkImage
import dev.tcode.thinmpk.constant.StyleConstant
import dev.tcode.thinmpk.model.SongModel
import dev.tcode.thinmpk.view.collapsingAppBar.DetailCollapsingAppBar
import dev.tcode.thinmpk.view.collapsingAppBar.detailSize
import dev.tcode.thinmpk.view.layout.MiniPlayerLayout
import dev.tcode.thinmpk.view.listItem.SongListItem
import dev.tcode.thinmpk.view.text.PrimaryTitle
import dev.tcode.thinmpk.view.text.SecondaryTitle
import dev.tcode.thinmpk.view.util.CustomGridCellsFixed
import dev.tcode.thinmpk.view.util.gridSpanCount
import dev.tcode.thinmpk.viewmodel.AlbumDetailViewModel

@Composable
fun AlbumDetailPage(
    id: String,
    viewModel: AlbumDetailViewModel = viewModel(factory = viewModelFactory {
        initializer {
            AlbumDetailViewModel(id)
        }
    })
) {
    val uiState by viewModel.uiState.collectAsState()
    val spanCount: Int = gridSpanCount()
    val (size, gradientHeight, primaryTitlePosition, secondaryTitlePosition) = detailSize()

    LaunchedEffect(Unit) {
        viewModel.load()
    }

//    CommonLayoutView(uiState.isVisiblePlayer) { showPlaylistRegisterPopup ->
    MiniPlayerLayout {
        DetailCollapsingAppBar(
            title = uiState.album?.name ?: "",
            columns = CustomGridCellsFixed(spanCount),
            spanCount = spanCount,
            dropdownMenus = { callback ->
            }
        ) {
            item(span = { GridItemSpan(spanCount) }) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(size)
                ) {
                    ArtworkImage(
                        imageId = uiState.album?.imageId ?: "",
                        modifier = Modifier.fillMaxWidth(),
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
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(StyleConstant.ROW_HEIGHT.dp)
                            .offset(y = primaryTitlePosition),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        PrimaryTitle(uiState.album?.name ?: "")
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                            .offset(y = secondaryTitlePosition),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        SecondaryTitle(uiState.album?.artistName ?: "")
                    }
                }
            }
            itemsIndexed(
                uiState.songs,
                span = { _: Int, _: SongModel -> GridItemSpan(spanCount) }) { index, song ->
                SongListItem(song, Modifier.pointerInput(index) {
                    detectTapGestures(
                        onLongPress = { println("onLongPress: index=$index, song=${song.name}") },
                        onTap = { viewModel.start(index) }
                    )
                })
            }
        }
    }
//    }
}