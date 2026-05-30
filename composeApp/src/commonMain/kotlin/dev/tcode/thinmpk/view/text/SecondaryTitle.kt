package dev.tcode.thinmpk.view.text

import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.tcode.thinmpk.constant.StyleConstant
import dev.tcode.thinmpk.view.util.screenWidth

@Composable
fun SecondaryTitle(text: String) {
    Text(
        text,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = StyleConstant.FONT_MEDIUM.sp,
        modifier = Modifier.width(screenWidth() - StyleConstant.BUTTON_SIZE.dp * 2 - StyleConstant.PADDING_TINY.dp * 2)
    )
}