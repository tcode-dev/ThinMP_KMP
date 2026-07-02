package dev.tcode.thinmpk.view.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.tcode.thinmpk.constant.StyleConstant
import dev.tcode.thinmpk.view.nav.LocalNavigator

@Composable
fun BackButton(modifier: Modifier = Modifier) {
    val navigator = LocalNavigator.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(StyleConstant.BUTTON_SIZE.dp)
            .clip(RoundedCornerShape(StyleConstant.IMAGE_CORNER_SIZE.dp))
            .clickable { navigator.back() }) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(StyleConstant.ICON_SIZE.dp)
        )
    }
}
