package dev.tcode.thinmpk.view.dropdownMenu

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.tcode.thinmpk.model.SongModel
import dev.tcode.thinmpk.viewmodel.FavoriteSongMenuViewModel

/**
 * 曲のお気に入り登録／解除を行うメニュー項目。
 * 登録状態の取得は非同期なので、確定するまで文言は空のまま（項目の高さは変わらない）。
 *
 * @param callback メニューを閉じる
 * @param onCompleted 登録／解除がDBに反映されたあとに呼ばれる
 */
@Composable
fun FavoriteSongDropdownMenuItem(
    song: SongModel,
    callback: () -> Unit,
    onCompleted: () -> Unit = {},
    viewModel: FavoriteSongMenuViewModel = viewModel(factory = viewModelFactory { initializer { FavoriteSongMenuViewModel() } }),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(song.id) {
        viewModel.load(song)
    }

    DropdownMenuItem(
        text = {
            Text(
                when (uiState.isFavorite) {
                    true -> "Remove from Favorites"
                    false -> "Add to Favorites"
                    null -> ""
                }
            )
        },
        onClick = {
            callback()
            viewModel.toggle(song, onCompleted)
        },
    )
}
