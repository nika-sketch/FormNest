package com.example.formnest.presentation.hierarchy.model

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

sealed interface ContentItemUi {

    @Composable
    fun Content(fontSize: TextUnit)

    data class Page(
        val title: String,
        val items: List<ContentItemUi>,
    ) : ContentItemUi {
        @Composable
        override fun Content(fontSize: TextUnit) {
            Text(
                text = title,
                fontSize = fontSize, fontWeight = FontWeight.Bold
            )
        }
    }

    data class Section(
        val title: String,
        val items: List<ContentItemUi>,
    ) : ContentItemUi {
        @Composable
        override fun Content(fontSize: TextUnit) {
            Text(
                text = title, fontSize = fontSize,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    data class Text(val title: String) : ContentItemUi {
        @Composable
        override fun Content(fontSize: TextUnit) {
            Text(text = title, fontSize = fontSize)
        }
    }

    data class Image(
        val title: String, val src: String
    ) : ContentItemUi {
        @Composable
        override fun Content(fontSize: TextUnit) {
            Text(text = title, fontSize = fontSize)
            Spacer(modifier = Modifier.height(4.dp))
            ClickableImage(
                imageUrl = src,
                title = title
            )
        }

        @Composable
        private fun ClickableImage(imageUrl: String, title: String) {
            val model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true).build()
            AsyncImage(
                model = model,
                contentDescription = title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}