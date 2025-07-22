package com.example.formnest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.formnest.FormNestApp
import com.example.formnest.data.model.FormNestApiModel
import com.example.formnest.shared.viewModelFactory
import com.example.formnest.ui.theme.FormNestTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = viewModel<MainViewModel>(
                factory = viewModelFactory {
                    MainViewModel(networkModule = FormNestApp.networkModule)
                }
            )
            FormNestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val state = viewModel.state.collectAsStateWithLifecycle()
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        formNestApiModel = state.value
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, formNestApiModel: FormNestApiModel) {
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
            formNestApiModel = FormNestApiModel(emptyList(), "", "")
        )
    }
}
