package dev.tcode.thinmpk.view.dropdownMenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * 長押しでメニューを開くためのラッパー。
 * [content] には行の描画を、[dropdownContent] にはメニュー項目を渡す。
 * どちらのスロットにも開閉用の callback が渡るので、行側の長押しで開き、項目の選択で閉じる。
 */
@Composable
fun DropdownMenuBox(
    dropdownContent: @Composable ColumnScope.(callback: () -> Unit) -> Unit,
    content: @Composable BoxScope.(callback: () -> Unit) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val callback = { expanded = !expanded }

    Box {
        content(callback)
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            dropdownContent(callback)
        }
    }
}
