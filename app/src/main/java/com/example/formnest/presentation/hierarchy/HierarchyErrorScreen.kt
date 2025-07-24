package com.example.formnest.presentation.hierarchy

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HierarchyErrorScreen(
  message: String,
  onRetry: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val transitionState = remember { MutableTransitionState(false).apply { targetState = true } }
  AnimatedVisibility(
    visibleState = transitionState,
    enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
    exit = fadeOut(),
    modifier = modifier.fillMaxSize()
  ) {
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier.fillMaxSize()
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
          .wrapContentSize()
          .padding(24.dp)
      ) {
        Text(
          text = message,
          style = MaterialTheme.typography.bodyLarge,
          color = MaterialTheme.colorScheme.error,
          textAlign = TextAlign.Center
        )

        Button(onClick = onRetry) {
          Text("Retry")
        }
      }
    }
  }
}
