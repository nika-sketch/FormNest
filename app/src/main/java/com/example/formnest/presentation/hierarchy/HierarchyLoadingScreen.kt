package com.example.formnest.presentation.hierarchy

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.formnest.shared.shimmer

@Composable
fun LoadingContentPlaceholder(levels: List<Int>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(levels) { index, level ->
            ShimmerHierarchyItem(level = level)
        }
    }
}

@Composable
fun ShimmerHierarchyItem(level: Int) {
    val baseFontSize = 24.sp
    val step = 2.sp
    val minFontSize = 12.sp

    val fontSize = (baseFontSize.value - level * step.value).coerceAtLeast(minFontSize.value).sp
    val paddingStart = (level * 12).dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = paddingStart, top = 8.dp, bottom = 8.dp)
            .height(fontSize.value.dp * 1.6f)
            .clip(RoundedCornerShape(4.dp))
            .shimmer()
    )
}
