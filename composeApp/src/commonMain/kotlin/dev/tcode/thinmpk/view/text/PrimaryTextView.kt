package dev.tcode.thinmpk.view.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun PrimaryTextView(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    textAlign: TextAlign? = null,
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        textAlign = textAlign,
        modifier = modifier,
    )
}
