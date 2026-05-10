package dev.tcode.thinmpk.view.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.tcode.thinmpk.view.collapsingTopAppBar.GridCollapsingTopAppBar
import dev.tcode.thinmpk.view.component.listItem.AlbumGridItem
import dev.tcode.thinmpk.viewmodel.AlbumsViewModel

@Composable
fun AlbumsPage(
    onAlbumClick: (String) -> Unit = {},
    viewModel: AlbumsViewModel = viewModel(factory = viewModelFactory { initializer { AlbumsViewModel() } })
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    GridCollapsingTopAppBar(
        title = "Albums",
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(uiState.albums) { index, album ->
            AlbumGridItem(album, Modifier.clickable { onAlbumClick(album.id) })
        }
    }
}
