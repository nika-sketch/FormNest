package com.example.formnest.presentation.hierarchy.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
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
  val animatedIndices = remember { mutableStateSetOf<Int>() }
  LazyColumn(modifier = Modifier.fillMaxSize()) {
    itemsIndexed(contentList, key = { index, item ->
      item.item.hashCode()
    }) { index, item ->
      HierarchyItem(
        modifier = Modifier.animateItem(),
        renderItemUi = item,
        onClick = onClick,
        index = index,
        animatedIndices = animatedIndices,
        onAnimated = animatedIndices::add
      )
    }
  }
}

@Composable
fun HierarchyItem(
  modifier: Modifier = Modifier,
  renderItemUi: HierarchyRecursiveItemUi,
  onClick: ((String, String) -> Unit)? = null,
  index: Int,
  animatedIndices: Set<Int>,
  onAnimated: (Int) -> Unit
) {
  val alreadyAnimated = remember { index in animatedIndices }
  var visible by remember { mutableStateOf(alreadyAnimated) }

  val offsetY by animateDpAsState(
    targetValue = if (visible) 0.dp else 40.dp,
  )
  val alpha by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
  )

  LaunchedEffect(Unit) {
    if (!alreadyAnimated) {
      delay(index * 30L)
      visible = true
      onAnimated(index)
    }
  }

  val item = renderItemUi.item
  val level = renderItemUi.level

  val baseFontSize = 24.sp
  val step = 2.sp
  val minFontSize = 12.sp
  val fontSize = (baseFontSize.value - level * step.value).coerceAtLeast(minFontSize.value).sp
  val paddingStart = (level * 12).dp

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(start = paddingStart, top = 8.dp, bottom = 8.dp)
      .offset { IntOffset(x = 0, y = offsetY.roundToPx()) }
      .graphicsLayer { this.alpha = alpha }
  ) {
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
