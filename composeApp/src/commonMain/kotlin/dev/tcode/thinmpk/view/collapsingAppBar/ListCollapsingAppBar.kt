package dev.tcode.thinmpk.view.collapsingAppBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.tcode.thinmpk.constant.StyleConstant
import dev.tcode.thinmpk.view.topAppBar.PlainTopAppBar

@Composable
fun ListCollapsingAppBar(title: String, content: LazyListScope.() -> Unit) {
    val lazyListState = rememberLazyListState()
    val scrollOffset = remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset } }

    Box(Modifier.zIndex(1F)) {
        PlainTopAppBar(title, visible = scrollOffset.value > 1)
    }
    LazyColumn(state = lazyListState) {
        item {
            Spacer(
                Modifier
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .height(StyleConstant.ROW_HEIGHT.dp)
            )
        }
        content()
    }
}
