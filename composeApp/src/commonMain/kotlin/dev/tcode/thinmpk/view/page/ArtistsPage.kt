package dev.tcode.thinmpk.view.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.tcode.thinmpk.view.collapsingTopAppBar.ColumnCollapsingTopAppBar
import dev.tcode.thinmpk.view.nav.LocalNavigator
import androidx.compose.material3.MaterialTheme
import dev.tcode.thinmpk.view.text.PlainText
import dev.tcode.thinmpk.viewmodel.ArtistsViewModel

@Composable
fun ArtistsPage(
    viewModel: ArtistsViewModel = viewModel(factory = viewModelFactory { initializer { ArtistsViewModel() } })
) {
    val navigator = LocalNavigator.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    ColumnCollapsingTopAppBar(
        title = "Artists",
    ) {
        itemsIndexed(uiState.artists) { _, artist ->
            PlainText(
                text = artist.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navigator.artistDetail(artist.id) }
                    .padding(16.dp),
            )
        }
    }
}
