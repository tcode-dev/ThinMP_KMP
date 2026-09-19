# AlbumDetail 画面: KMP 側の未実装機能

Android 版 (`ThinMP_Android_Kotlin`) の `AlbumDetailScreen` と、KMP 版 (`composeApp`) の `AlbumDetailPage` を比較し、KMP 側で未実装・差分のある機能をまとめる。

- 比較元: `ThinMP_Android_Kotlin/app/src/main/java/dev/tcode/thinmp/view/screen/AlbumDetailScreen.kt`
- 比較先: `composeApp/src/commonMain/kotlin/dev/tcode/thinmpk/view/page/AlbumDetailPage.kt`
- 調査日: 2026-09-19

## サマリー

| # | 機能 | Android 版 | KMP 版 | 状態 |
|---|------|-----------|--------|------|
| 1 | ショートカット登録／解除 (TopAppBar メニュー) | `ShortcutDropdownMenuItemView(AlbumId(id))` | `dropdownMenus = { }` (空) | **未実装** (機能自体が存在しない) |
| 2 | プレイリストへ追加 (曲行の長押しメニュー) | `PlaylistDropdownMenuItemView` → `PlaylistRegisterPopupView` | メニュー項目なし | **未実装** (機能自体が存在しない) |
| 3 | `CommonLayoutView` (プレイリスト登録ポップアップを持つ共通レイアウト) | あり | コメントアウトされ `MiniPlayerLayout` を直接使用 | **未実装** (#2 に依存) |
| 4 | 画面復帰 (onResume) 時の再読み込み | `CustomLifecycleEventObserver(viewModel)` で `load()` 再実行 | 同上 | **対応済み** (2026-09-20) |
| 5 | プレイヤーエラー時 (`onError`) の再読み込み | ViewModel が `MusicPlayerListener` を実装し `load()` | リスナー未登録 | **未実装** |
| 6 | 曲一覧の `IS_MUSIC = 1` フィルタ | `findByAlbumId` の selection に含む | 含む | **対応済み** (2026-09-19) |
| 7 | トラック番号ソート (`"15/30"` 形式対応) | `CD_TRACK_NUMBER` を CASE 式でパース + メモリ上でも `sortedBy` | `TRACK ASC` のみ | 差分 (要確認) |
| 8 | 行ドロップダウンの状態を行 id でキー付け | `DropdownMenuView(id = song.id)` + `pointerInput(song.id)` | `DropdownMenuBox` はキーなし、`pointerInput(index)` | 差分 (潜在バグ) |
| 9 | ミニプレイヤー表示制御 (`isVisiblePlayer`) | ViewModel が状態を持ち Layout に渡す | `MiniPlayer` 自身が `isVisible` で判定 | 別方式で実装済み |
| 10 | 文言のリソース化 (`stringResource`) | `R.string.add_favorite` 等 | ハードコード英語 | 差分 (i18n) |
| 11 | ヘッダー画像の `ContentScale` | `Fit` + `fillMaxSize()` | `Crop` 固定 + `fillMaxWidth()` | 差分 (見た目) |

---

## 1. ショートカット登録／解除 (TopAppBar のドロップダウンメニュー)

### Android 版の挙動

`DetailCollapsingTopAppBarView` の `dropdownMenus` スロットに `ShortcutDropdownMenuItemView(AlbumId(id), callback)` を渡している。右上の「⋮」を押すと「Add to a shortcut / Remove from shortcut」が表示され、押下でトグルする。

```kotlin
// AlbumDetailScreen.kt (Android)
DetailCollapsingTopAppBarView(title = uiState.primaryText, ..., dropdownMenus = { callback ->
    ShortcutDropdownMenuItemView(AlbumId(id), callback)
}) { ... }
```

関連実装 (Android):

- `view/dropdownMenu/ShortcutDropdownMenuItemView.kt` — `produceState<Boolean?>` で登録状態を非同期取得し、確定するまで項目を `enabled = false` にする
- `viewModel/ShortcutViewModel.kt` — `isShortcut()` / `toggle()`。toggle は `viewModelScope` で実行 (メニューが閉じてもキャンセルされないように)
- `register/ShortcutRegister.kt`, `repository/ShortcutRepository.kt`, `repository/dao/ShortcutDao.kt`, `model/room/ShortcutEntity.kt`
- `model/media/valueObject/ShortcutItemId.kt` (`AlbumId` / `ArtistId` / `PlaylistId` が実装)
- `res/values/strings.xml` — `add_shortcut`, `remove_shortcut`

### KMP 版の現状

```kotlin
// AlbumDetailPage.kt (KMP)
DetailCollapsingAppBar(
    title = uiState.album?.name ?: "",
    ...
    dropdownMenus = { callback ->
    }   // 空
) { ... }
```

`DetailCollapsingAppBar` / `DetailTopAppBar` 側には「⋮」ボタンと `DropdownMenu` が既にあるので、UI の受け皿はできている。ただしショートカット機能自体 (Entity / DAO / Repository / ViewModel / メニュー項目) が KMP には一切存在しない (`grep -ri shortcut composeApp/src` でヒットなし)。

### 実装に必要なもの

1. `model/room/ShortcutEntity.kt` と `repository/dao/ShortcutDao.kt` を追加し、`AppDatabase` の `entities` に追加 (version を上げる)
2. `repository/ShortcutRepository.kt` (お気に入りと同じ構成: `FavoriteSongRepository` を参照)
3. `di/AndroidModule.kt` (および iOS 側の Koin 定義) に登録
4. `viewmodel/ShortcutMenuViewModel.kt` (`FavoriteSongMenuViewModel` と同じパターン)
5. `view/dropdownMenu/ShortcutDropdownMenuItem.kt` (`FavoriteSongDropdownMenuItem` と同じパターン)
6. `AlbumDetailPage` / `ArtistDetailPage` の `dropdownMenus` に組み込む
7. (メイン画面にショートカット一覧を出す場合は `ShortcutCellView` 相当も必要だが、本ドキュメントの対象外)

---

## 2. プレイリストへ追加 (曲行の長押しメニュー)

### Android 版の挙動

曲行を長押しすると「Add to favorites / Remove from favorites」に加えて「Add to a playlist」が表示される。押すと `CommonLayoutView` から受け取った `showPlaylistRegisterPopup(song.songId)` を呼び、`PlaylistRegisterPopupView` (既存プレイリスト一覧 + 新規作成フォーム) が画面中央にポップアップ表示される。

```kotlin
// AlbumDetailScreen.kt (Android)
DropdownMenuView(id = song.id, dropdownContent = { callback ->
    val callbackPlaylist = {
        showPlaylistRegisterPopup(song.songId)
        callback()
    }

    FavoriteSongDropdownMenuItemView(song.songId, callback)
    PlaylistDropdownMenuItemView(callbackPlaylist)
}) { ... }
```

関連実装 (Android):

- `view/dropdownMenu/PlaylistDropdownMenuItemView.kt` — 文言を出すだけの項目
- `view/playlist/PlaylistRegisterPopupView.kt` — `Popup` で一覧 / 新規作成 UI。`PlaylistsViewModel.load(songId)` で「この曲が既に入っているプレイリスト」をマークする
- `view/row/PlaylistRegisterRowView.kt`
- `viewModel/PlaylistsViewModel.kt`, `register/PlaylistRegister.kt`, `repository/PlaylistRepository.kt`
- `repository/dao/PlaylistDao.kt`, `PlaylistSongDao.kt`, `model/room/PlaylistEntity.kt`, `PlaylistSongEntity.kt`
- `res/values/strings.xml` — `add_playlist`, `create_playlist`, `playlist_name`, `already_added_to_playlist` 等

### KMP 版の現状

```kotlin
// AlbumDetailPage.kt (KMP)
DropdownMenuBox(dropdownContent = { callback ->
    FavoriteSongDropdownMenuItem(song, callback)
}) { ... }
```

お気に入りのみ。プレイリスト機能自体が KMP に存在しない (`grep -ri playlist composeApp/src` は `PlayerPage.kt` の無関係な語のみ)。

### 実装に必要なもの

1. `PlaylistEntity` / `PlaylistSongEntity` / `PlaylistDao` / `PlaylistSongDao` を追加し `AppDatabase` に登録
2. `PlaylistRepository` + Koin 登録
3. `PlaylistsViewModel` (一覧取得、曲の登録、新規作成)
4. `PlaylistDropdownMenuItem`, `PlaylistRegisterPopup`, `PlaylistRegisterRow` の Compose 化
5. #3 の `CommonLayout` を用意し、`AlbumDetailPage` / `ArtistDetailPage` / `SongsPage` 等の行メニューに組み込む

---

## 3. `CommonLayoutView` 相当の共通レイアウト

### Android 版

```kotlin
// CommonLayoutView.kt (Android)
@Composable
fun CommonLayoutView(isVisibleMiniPlayer: Boolean = true, content: @Composable ((showPlaylistRegisterPopup: (songId: SongId) -> Unit) -> Unit)) {
    val visiblePopup = remember { mutableStateOf(false) }
    val playlistRegisterSongId = remember { mutableStateOf(SongId("")) }
    ...
    MiniPlayerLayoutView(isVisibleMiniPlayer) {
        content(showPlaylistRegisterPopup)
        if (visiblePopup.value) {
            PlaylistRegisterPopupView(playlistRegisterSongId.value, togglePopup)
        }
    }
}
```

ミニプレイヤー + プレイリスト登録ポップアップの状態を 1 か所で持ち、各画面に `showPlaylistRegisterPopup` を渡す。

### KMP 版の現状

`AlbumDetailPage` に `// CommonLayoutView(uiState.isVisiblePlayer) { showPlaylistRegisterPopup ->` とコメントアウトが残っており、実体は `MiniPlayerLayout` を直接使っている。#2 を実装する際に `MiniPlayerLayout` を包む `CommonLayout` を追加するか、`MiniPlayerLayout` にポップアップ機能を持たせる。

> Android 版のコメントにある通り、`playlistRegisterSongId` は `remember` で保持しないと、ポップアップ表示中に `isVisibleMiniPlayer` が変わった際に再コンポーズで初期化され、空 id のレコードが書かれるバグがある。移植時に注意。

---

## 4. 画面復帰時の再読み込み (ライフサイクル連携)

### Android 版

```kotlin
// AlbumDetailScreen.kt (Android)
CustomLifecycleEventObserver(viewModel)

// AlbumDetailViewModel.kt (Android)
class AlbumDetailViewModel(...) : AndroidViewModel(application), CustomLifecycleEventObserverListener, MusicPlayerListener {
    override fun onResume() {
        if (initialized) {
            load()
            bindService()
        } else {
            initialized = true
        }
    }
    override fun onStop() { musicPlayer.destroy(getApplication()) }
    ...
}
```

`ON_RESUME` で 2 回目以降は `load()` を再実行する。別画面でお気に入り操作をして戻ってきた場合や、他アプリでライブラリが変わった場合に反映される。

### KMP 版 (対応済み: 2026-09-20)

`AlbumDetailViewModel` に `CustomLifecycleEventObserverListener` を実装し、`onResume` で 2 回目以降に `load()` を再実行する。初回の `load()` は従来通り `LaunchedEffect(Unit)` で行い、オブザーバー登録時に即時発火する最初の `ON_RESUME` は `initialized` フラグで読み飛ばす。

```kotlin
// AlbumDetailViewModel.kt (KMP)
override fun onResume() {
    if (initialized) {
        load()
    } else {
        initialized = true
    }
}

// AlbumDetailPage.kt (KMP)
LaunchedEffect(Unit) {
    viewModel.load()
}

CustomLifecycleEventObserver(viewModel)
```

> `onStop` の `musicPlayer.destroy()` / `bindService()` は Android 版が画面ごとにサービスへ bind/unbind する設計のためのもの。KMP 版は `MusicPlayer` が Koin の singleton なので、この部分は移植不要。

---

## 5. プレイヤーエラー時の再読み込み

### Android 版

`AlbumDetailViewModel` が `MusicPlayerListener` を実装し、`onError()` で `load()` を再実行、`onBind()` で `isVisiblePlayer` を更新する。

### KMP 版の現状

`AlbumDetailViewModel` は `MusicPlayerListener` を登録していない。`MiniPlayerViewModel` 側で `onError` → `isVisible = false` は処理しているので、ミニプレイヤーの非表示は動く。曲一覧を再読み込みする必要があるかは要検討 (再生エラー ≠ ライブラリ変更のため、必須ではない可能性が高い)。

---

## 6. `IS_MUSIC = 1` フィルタ

### Android 版

```kotlin
// SongRepository.kt (Android)
suspend fun findByAlbumId(albumId: String): List<SongModel> {
    return getList(
        MediaStore.Audio.Media.ALBUM_ID + " = ? AND " + MediaStore.Audio.Media.IS_MUSIC + " = 1",
        arrayOf(albumId),
        trackNumberSortOrder
    )
}
```

### KMP 版 (対応済み: 2026-09-19)

`findByAlbumId()` / `findByArtistId()` の selection に `AND IS_MUSIC = ?` を追加し、`findAll()` と揃えた。

```kotlin
// SongRepositoryImpl.kt (KMP androidMain)
override fun findByAlbumId(albumId: String): List<SongModel> {
    selection = "${MediaStore.Audio.Media.ALBUM_ID} = ? AND ${MediaStore.Audio.Media.IS_MUSIC} = ?"
    selectionArgs = arrayOf(albumId, "1")
    sortOrder = "${MediaStore.Audio.Media.TRACK} ASC"
    return getList()
}
```

iOS 側 (Swift 実装) は MediaStore ではなく MPMediaLibrary を使うため対象外。

---

## 7. トラック番号のソート

### Android 版

`CD_TRACK_NUMBER` が `"15/30"` 形式の場合に `/` の前だけを整数化して並べる SQL の CASE 式 (`trackNumberSortOrder`) を使い、さらに `AlbumDetailService` でも `sortedBy { it.getTrackNumber() }` で並べ直している。`SongModel` に `trackNumber: String` を持つ。

### KMP 版の現状

`MediaStore.Audio.Media.TRACK ASC` でソート。`TRACK` は整数カラム (ディスク番号 × 1000 + トラック番号) なので通常は正しく並ぶ。`SongModel` に `trackNumber` フィールドはない。

実機で複数ディスクや `"15/30"` 形式のタグを持つアルバムで並び順を確認し、問題があれば Android 版と同じ CASE 式に寄せる。iOS 側 (Swift 実装) のソートも合わせて確認が必要。

---

## 8. 行ドロップダウンの状態キー

### Android 版

`DropdownMenuView(id = song.id, ...)` が `remember(id)` で開閉状態を、`pointerInput(id)` でジェスチャー検出を行 id に紐付けている。リストに item key がないため、行が減って別の曲が同じスロットに繰り上がったとき、古い行のメニューが開いたまま新しい行の上に残るのを防ぐ (ソース内コメント参照)。

### KMP 版の現状

`DropdownMenuBox` は引数に id を持たず `remember { mutableStateOf(false) }` で状態を持つ。`pointerInput(index)` は index キーなので、同じ index に別の曲が来ても再生成されない。

アルバム詳細ではリストが動的に減ることはほぼないが、お気に入り一覧などでは発生し得る。`DropdownMenuBox` に `id: String` を追加して `remember(id)` にし、呼び出し側も `pointerInput(song.id)` に揃えるのが安全。

---

## 9. ミニプレイヤーの表示制御 (実装済み・方式差分)

Android 版は `AlbumDetailViewModel.uiState.isVisiblePlayer` を `CommonLayoutView` に渡して表示を切り替える。KMP 版は `MiniPlayerViewModel.uiState.isVisible` を `MiniPlayer` 自身が判定して非表示にする。機能としては同等なので追加実装は不要。ただし `DetailCollapsingAppBar` 末尾の `EmptyMiniPlayer()` (スクロール末尾の余白) はミニプレイヤーの表示状態に関わらず常に入る点が Android 版と異なる。

---

## 10. 文言のリソース化

Android 版はメニュー文言を `stringResource(R.string.xxx)` で参照し、`MaterialTheme.colorScheme.primary` で色付けしている。KMP 版の `FavoriteSongDropdownMenuItem` は `"Add to Favorites"` / `"Remove from Favorites"` をハードコード。Compose Multiplatform の `composeResources/values/strings.xml` + `stringResource(Res.string.xxx)` に置き換えると Android 版と揃う。ショートカット (#1)・プレイリスト (#2) を追加する際に同時に対応するのが効率的。

---

## 11. ヘッダー画像の ContentScale

Android 版は `ImageView(uri, contentScale = ContentScale.Fit, modifier = Modifier.fillMaxSize())`。KMP 版の `ArtworkImage` は `ContentScale.Crop` 固定で `Modifier.fillMaxWidth()`。正方形でないアートワークで見え方が変わる。`ArtworkImage` に `contentScale` 引数を追加して呼び出し側で選べるようにするのが簡単。

---

## その他の細かな差分 (機能差ではない)

- **読み込みスレッド**: Android 版は `viewModelScope.launch` で suspend 関数として読み込む。KMP 版の `load()` は同期実行 (メインスレッドで `ContentResolver.query`)。曲数の多いアルバムではフレーム落ちの原因になるので、`viewModelScope.launch(Dispatchers.IO)` 等に寄せる余地がある。
- **アルバムが見つからない場合**: Android 版は `findById` が null なら `return@launch` で前の状態を維持。KMP 版は `album = null` を書き込みタイトルが空になる。
- **メニューの見た目**: Android 版は `DropdownMenu` に `offset = DpOffset((-1).dp, 0.dp)` と `background(MaterialTheme.colorScheme.onBackground)` を指定。KMP 版の `DropdownMenuBox` はデフォルト。
- **ViewModel の id 受け渡し**: Android 版は Hilt + `SavedStateHandle`。KMP 版は `viewModelFactory { initializer { AlbumDetailViewModel(id) } }`。これは設計差であり問題ではない。

## 推奨する実装順

1. ~~#6 (`IS_MUSIC` フィルタ)~~ (対応済み) と ~~#4 (onResume 再読み込み)~~ (対応済み) — 小さく、既存部品で完結する
2. #8 (`DropdownMenuBox` の id キー化) — 他画面のバグ予防にもなる
3. #10 (文言リソース化) — #1/#2 の前提として
4. #1 (ショートカット) — DB スキーマ変更を伴うが単機能
5. #2 + #3 (プレイリスト + 共通レイアウト) — 最も大きい。プレイリスト一覧／詳細画面とセットで計画する
6. #7 / #11 — 実機確認の上で必要なら
