package com.example.formnest.presentation.imagedetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

private val FallbackImageSize = 400.dp

@Composable
fun ImageContent(
  modifier: Modifier = Modifier,
  title: String,
  imageUrl: String
) {
  val transitionState = remember { MutableTransitionState(false).apply { targetState = true } }
  val size = remember { extractImageSizeDp(imageUrl) }
  val width = size?.first ?: FallbackImageSize
  val height = size?.second ?: FallbackImageSize
  AnimatedVisibility(
    visibleState = transitionState,
    enter = slideInVertically(
      initialOffsetY = { fullHeight -> fullHeight },
      animationSpec = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
      )
    )
  ) {
    val scrollState = rememberScrollState()

    Column(
      modifier = modifier
        .fillMaxSize()
        .verticalScroll(scrollState),
    ) {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(12.dp))

      Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
      ) {
        val model = ImageRequest.Builder(LocalContext.current)
          .data(imageUrl)
          .crossfade(true)
          .build()

        AsyncImage(
          model = model,
          contentDescription = title,
          modifier = Modifier
            .width(width)
            .height(height),
          contentScale = ContentScale.Crop
        )
      }
    }
  }
}

private fun extractImageSizeDp(url: String): Pair<Dp, Dp>? {
  val regex = Regex("(\\d{2,5})x(\\d{2,5})")
  val matchResult = regex.find(url)
  return matchResult?.let { result ->
    val (width, height) = result.destructured
    width.toInt().dp to height.toInt().dp
  }
}
