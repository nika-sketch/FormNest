package com.example.formnest.presentation.hierarchy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.formnest.presentation.hierarchy.model.RendererItemUi

@Composable
fun HierarchyScreen(
    contentList: List<RendererItemUi>,
    onClick: ((String, String) -> Unit)? = null
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(contentList) { index, item ->
            HierarchyItem(renderItemUi = item, onClick = onClick)
        }
    }
}

@Composable
fun HierarchyItem(renderItemUi: RendererItemUi, onClick: ((String, String) -> Unit)? = null) {
    val item = renderItemUi.item
    val level = renderItemUi.level

    val baseFontSize = 24.sp
    val step = 2.sp
    val minFontSize = 12.sp

    val fontSize = (baseFontSize.value - level * step.value).coerceAtLeast(minFontSize.value).sp
    val paddingStart = (level * 12).dp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = paddingStart, top = 8.dp, bottom = 8.dp)
    ) {
        item.Content(fontSize, onClick = onClick)
    }
}
