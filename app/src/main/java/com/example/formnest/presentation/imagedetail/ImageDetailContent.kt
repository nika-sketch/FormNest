package com.example.formnest.presentation.imagedetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun ImageContent(modifier: Modifier = Modifier, title: String, imageUrl: String) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        val model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true).build()
        AsyncImage(
            model = model,
            contentDescription = title,
            modifier = Modifier
                .size(400.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}
