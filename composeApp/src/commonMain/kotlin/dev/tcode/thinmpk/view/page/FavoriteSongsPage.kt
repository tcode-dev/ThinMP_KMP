package dev.tcode.thinmpk.view.page

import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.tcode.thinmpk.view.collapsingAppBar.ListCollapsingAppBar
import dev.tcode.thinmpk.view.layout.MiniPlayerLayout
import dev.tcode.thinmpk.view.listItem.SongListItem
import dev.tcode.thinmpk.viewmodel.FavoriteSongsViewModel

@Composable
fun FavoriteSongsPage(
    viewModel: FavoriteSongsViewModel = viewModel(factory = viewModelFactory { initializer { FavoriteSongsViewModel() } })
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    MiniPlayerLayout {
        ListCollapsingAppBar("Favorite Songs") {
            itemsIndexed(uiState.songs) { index, song ->
                SongListItem(
                    song,
                    onClick = { viewModel.start(index) },
                ) { dismiss ->
                    DropdownMenuItem(
                        text = { Text("Remove from Favorites") },
                        onClick = {
                            dismiss()
                            viewModel.deleteFavorite(song)
                        },
                    )
                }
            }
        }
    }
}
