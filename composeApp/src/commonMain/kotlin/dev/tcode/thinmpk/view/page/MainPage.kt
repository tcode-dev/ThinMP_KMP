package dev.tcode.thinmpk.view.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tcode.thinmpk.view.nav.LocalNavigator
import androidx.compose.material3.MaterialTheme
import dev.tcode.thinmpk.view.text.PlainText

@Composable
fun MainPage() {
    val navigator = LocalNavigator.current

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
        ) {
            PlainText(
                text = "Library",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            )
            PlainText(
                text = "Songs",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navigator.songs() }
                    .padding(16.dp)
            )
            PlainText(
                text = "Artists",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navigator.artists() }
                    .padding(16.dp)
            )
            PlainText(
                text = "Albums",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navigator.albums() }
                    .padding(16.dp)
            )
        }
    }
}
