package dev.tcode.thinmpk.view.collapsingTopAppBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

private val TOP_APP_BAR_HEIGHT = 48.dp

@Composable
fun GridCollapsingTopAppBar(
    title: String,
    columns: GridCells,
    modifier: Modifier = Modifier,
    content: LazyGridScope.() -> Unit
) {
    val lazyGridState = rememberLazyGridState()
    val scrollOffset = remember { derivedStateOf { lazyGridState.firstVisibleItemScrollOffset } }

    Box(Modifier.zIndex(1F)) {
        PlainTopAppBar(title, visible = scrollOffset.value > 1)
    }
    LazyVerticalGrid(
        columns = columns,
        state = lazyGridState,
        modifier = modifier
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Spacer(
                Modifier
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .height(TOP_APP_BAR_HEIGHT)
            )
        }
        content()
    }
}
