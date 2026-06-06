package dev.tcode.thinmpk.view.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.tcode.thinmpk.constant.StyleConstant

@Composable
fun SecondaryTitle(text: String) {
    PlainText(
        text = text,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center,
        style = TextStyle(fontSize = StyleConstant.FONT_MEDIUM.sp),
        modifier = Modifier.fillMaxWidth().padding(horizontal = (StyleConstant.BUTTON_SIZE + StyleConstant.PADDING_TINY).dp),
    )
}