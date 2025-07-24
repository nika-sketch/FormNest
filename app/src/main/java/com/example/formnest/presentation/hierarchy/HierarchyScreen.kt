package com.example.formnest.presentation.hierarchy

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.formnest.presentation.hierarchy.model.HierarchyContentUi
import com.example.formnest.presentation.hierarchy.model.HierarchyRecursiveItemUi
import kotlinx.coroutines.delay

@Composable
fun HierarchyScreen(
  contentList: List<HierarchyRecursiveItemUi>,
  onClick: ((String, String) -> Unit)? = null
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
  ) {
    itemsIndexed(contentList) { index, item ->
      HierarchyItem(
        modifier = Modifier.animateItem(),
        renderItemUi = item,
        onClick = onClick,
        index = index // pass index here
      )
    }
  }
}

@Composable
fun HierarchyItem(
  modifier: Modifier = Modifier,
  renderItemUi: HierarchyRecursiveItemUi,
  onClick: ((String, String) -> Unit)? = null,
  index: Int
) {
  val item = renderItemUi.item
  val level = renderItemUi.level

  val baseFontSize = 24.sp
  val step = 2.sp
  val minFontSize = 12.sp
  val fontSize = (baseFontSize.value - level * step.value).coerceAtLeast(minFontSize.value).sp
  val paddingStart = (level * 12).dp

  val transitionState = remember { MutableTransitionState(false) }
  LaunchedEffect(Unit) {
    delay(index * 50L)
    transitionState.targetState = true
  }
  AnimatedVisibility(
    visibleState = transitionState,
    enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
    modifier = modifier
      .fillMaxWidth()
      .padding(start = paddingStart, top = 8.dp, bottom = 8.dp)
  ) {
    Column {
      when (item) {
        is HierarchyContentUi.Page -> Text(
          text = item.title, fontSize = fontSize, fontWeight = FontWeight.Bold
        )

        is HierarchyContentUi.Section -> Text(
          text = item.title, fontSize = fontSize, fontWeight = FontWeight.SemiBold
        )

        is HierarchyContentUi.Text -> Text(text = item.title, fontSize = fontSize)
        is HierarchyContentUi.Image -> {
          Text(text = item.title, fontSize = fontSize)
          Spacer(modifier = Modifier.height(4.dp))
          ClickableImage(
            imageUrl = item.src,
            title = item.title,
            onClick = onClick
          )
        }
      }
    }
  }
}


@Composable
private fun ClickableImage(
  imageUrl: String, title: String,
  onClick: ((String, String) -> Unit)? = null
) {
  val model = ImageRequest.Builder(LocalContext.current)
    .data(imageUrl)
    .crossfade(true).build()
  AsyncImage(
    model = model,
    contentDescription = title,
    modifier = Modifier
      .size(100.dp)
      .clickable {
        onClick?.invoke(title, imageUrl)
      }
      .clip(RoundedCornerShape(8.dp)),
    contentScale = ContentScale.Crop
  )
}
