package com.example.formnest.presentation.model

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.formnest.presentation.ClickableImage

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
    }
}