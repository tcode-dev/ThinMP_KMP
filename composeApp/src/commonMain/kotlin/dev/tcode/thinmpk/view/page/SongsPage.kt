package dev.tcode.thinmpk.view.page

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.tcode.thinmpk.view.collapsingTopAppBar.ColumnCollapsingTopAppBar
import dev.tcode.thinmpk.view.component.listItem.SongListItem
import dev.tcode.thinmpk.viewmodel.SongsViewModel

@Composable
fun SongsPage(
    viewModel: SongsViewModel = viewModel(factory = viewModelFactory { initializer { SongsViewModel() } })
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    ColumnCollapsingTopAppBar("Songs") {
        itemsIndexed(uiState.songs) { index, song ->
            SongListItem(song, Modifier.pointerInput(index) {
                detectTapGestures(
                    onLongPress = { println("onLongPress: index=$index, song=${song.name}") },
                    onTap = { viewModel.start(index) }
                )
            })
        }
    }
}
