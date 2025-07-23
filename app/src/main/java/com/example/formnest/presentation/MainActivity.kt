package com.example.formnest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.formnest.FormNestApp
import com.example.formnest.presentation.mapper.ContentUiMapper
import com.example.formnest.presentation.model.ContentItemUi
import com.example.formnest.presentation.model.RendererItemUi
import com.example.formnest.shared.viewModelFactory
import com.example.formnest.ui.theme.FormNestTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = viewModel<MainViewModel>(
                factory = viewModelFactory {
                    MainViewModel(
                        formNestRepository = FormNestApp.formNestRepository,
                        contentMapper = ContentUiMapper()
                    )
                }
            )
            FormNestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val state = viewModel.state.collectAsStateWithLifecycle()
                    ContentScreen(contentList = state.value, innerPadding = innerPadding)
                }
            }
        }
    }
}

@Composable
fun ContentScreen(contentList: List<RendererItemUi>, innerPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        itemsIndexed(contentList) { index, item ->
            RenderFlatItem(renderItemUi = item)
        }
    }
}

@Composable
fun RenderFlatItem(renderItemUi: RendererItemUi) {
    val item = renderItemUi.item
    val level = renderItemUi.level

    val baseFontSize = 24.sp
    val step = 2.sp
    val minFontSize = 12.sp

    val fontSize = (baseFontSize.value - level * step.value)
        .coerceAtLeast(minFontSize.value)
        .sp

    val paddingStart = (level * 12).dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = paddingStart, top = 8.dp, bottom = 8.dp)
    ) {
        when (item) {
            is ContentItemUi.Page -> Text(
                text = item.title,
                fontSize = fontSize, fontWeight = FontWeight.Bold
            )

            is ContentItemUi.Section -> Text(
                text = item.title, fontSize = fontSize,
                fontWeight = FontWeight.SemiBold
            )

            is ContentItemUi.Text -> Text(text = item.title, fontSize = fontSize)
            is ContentItemUi.Image -> {
                Text(text = item.title, fontSize = fontSize)
                Spacer(modifier = Modifier.height(4.dp))
                ClickableImage(
                    imageUrl = item.src,
                    title = item.title
                )
            }
        }
    }
}

@Composable
fun ClickableImage(imageUrl: String, title: String) {
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
