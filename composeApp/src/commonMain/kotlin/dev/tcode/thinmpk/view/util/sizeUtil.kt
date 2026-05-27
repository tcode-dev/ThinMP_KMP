package dev.tcode.thinmpk.view.util

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import dev.tcode.thinmpk.constant.StyleConstant
import kotlin.math.max

@Composable
fun screenWidthDp(): Int {
    val containerSize = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current.density
    return (containerSize.width / density).toInt()
}

@Composable
fun screenHeightDp(): Int {
    val containerSize = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current.density
    return (containerSize.height / density).toInt()
}

@Composable
fun gridSpanCount(): Int {
    val spanCount = screenWidthDp() / StyleConstant.GRID_SPAN_BASE_SIZE

    return max(spanCount, StyleConstant.GRID_MIN_SPAN_COUNT)
}

@Composable
fun miniPlayerHeight(): Float {
    return WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().value + StyleConstant.ROW_HEIGHT
}

@Composable
fun screenWidth(): Dp {
    return screenWidthDp().dp
}

@Composable
fun screenHeight(): Dp {
    return screenHeightDp().dp + WindowInsets.systemBars.asPaddingValues().calculateTopPadding() + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
}

@Composable
fun systemBars(): Dp {
    return WindowInsets.systemBars.asPaddingValues().calculateTopPadding()
}

@Composable
fun isHeightMedium(): Boolean {
    return screenHeightDp().dp >= 480f.dp
}

@Composable
fun isHeightExpanded(): Boolean {
    return screenHeightDp().dp >= 900.dp
}

@Composable
fun minSize(): Dp {
    return min(screenWidth(), screenHeight())
}

@Composable
fun maxSize(): Dp {
    return max(screenWidth(), screenHeight())
}

@Composable
fun isLandscape(): Boolean {
    return screenWidthDp() > screenHeightDp()
}
