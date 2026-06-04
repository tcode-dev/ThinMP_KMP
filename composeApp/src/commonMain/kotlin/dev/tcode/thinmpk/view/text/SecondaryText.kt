package dev.tcode.thinmpk.view.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SecondaryText(text: String, textAlign: TextAlign? = null) {
    PlainText(
        text = text,
        textAlign = textAlign,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.bodySmall,
    )
}
