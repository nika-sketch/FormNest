package com.example.formnest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.formnest.FormNestApp
import com.example.formnest.domain.model.ContentItemDomain
import com.example.formnest.shared.viewModelFactory
import com.example.formnest.ui.theme.FormNestTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = viewModel<MainViewModel>(
                factory = viewModelFactory {
                    MainViewModel(formNestRepository = FormNestApp.formNestRepository)
                }
            )
            FormNestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val state = viewModel.state.collectAsStateWithLifecycle()
                    ContentScreen(listOf(state.value), innerPadding = innerPadding)
                }
            }
        }
    }
}

@Composable
fun ContentScreen(contentItems: List<ContentItemDomain>, innerPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        itemsIndexed(contentItems) { index, item ->
            RenderItem(item = item, level = 0)
        }
    }
}

@Composable
fun RenderItem(item: ContentItemDomain, level: Int) {
    val fontSize = when (level) {
        0 -> 24.sp
        1 -> 20.sp
        else -> 16.sp
    }

    val paddingStart = (level * 12).dp

    Column(modifier = Modifier.padding(start = paddingStart)) {
        when (item) {
            is ContentItemDomain.Page -> {
                Text(text = item.title, fontSize = fontSize, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                item.items.forEach { child ->
                    RenderItem(child, level + 1)
                }
            }

            is ContentItemDomain.Section -> {
                Text(text = item.title, fontSize = fontSize, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                item.items.forEach { child ->
                    RenderItem(child, level + 1)
                }
            }

            is ContentItemDomain.Text -> {
                Text(text = item.title, fontSize = fontSize)
            }

            is ContentItemDomain.Image -> {
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
    AsyncImage(
        model = imageUrl,
        contentDescription = title,
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, formNestApiModel: ContentItemDomain) {
    Text(
        text = formNestApiModel.toString(),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FormNestTheme {
        Greeting(
            name = "Android",
            formNestApiModel = ContentItemDomain.Text("Sample Text")
        )
    }
}
