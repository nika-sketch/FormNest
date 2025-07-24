package com.example.formnest.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.formnest.presentation.navigation.NavRoot
import com.example.formnest.ui.theme.FormNestTheme

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      FormNestTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          NavRoot(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
          )
        }
      }
    }
  }
}
