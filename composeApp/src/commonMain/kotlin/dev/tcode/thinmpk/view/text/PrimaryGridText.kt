package dev.tcode.thinmpk.view.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign

@Composable
fun PrimaryGridText(text: String) {
    PrimaryText(
        text = text,
        textAlign = TextAlign.Center,
    )
}
