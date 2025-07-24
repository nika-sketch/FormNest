package com.example.formnest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.example.formnest.FormNestApp
import com.example.formnest.presentation.hierarchy.HierarchyViewModel
import com.example.formnest.presentation.hierarchy.HierarchyErrorScreen
import com.example.formnest.presentation.hierarchy.mapper.ContentUiMapper
import com.example.formnest.presentation.hierarchy.HierarchyScreen
import com.example.formnest.presentation.hierarchy.LoadingContentPlaceholder
import com.example.formnest.presentation.hierarchy.model.HierarchyScreenState
import com.example.formnest.presentation.imagedetail.ImageContent
import com.example.formnest.presentation.navigation.model.ImageDetail
import com.example.formnest.presentation.navigation.model.MainScreen
import com.example.formnest.shared.DispatcherProvider
import com.example.formnest.shared.viewModelFactory

@Composable
fun NavRoot(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(MainScreen)
    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        modifier = modifier,
        entryProvider = { key ->
            when (key) {
                MainScreen -> NavEntry(key = key) {
                    val contentViewModel = viewModel<HierarchyViewModel>(
                        factory = viewModelFactory {
                            HierarchyViewModel(
                                formNestRepository = FormNestApp.formNestRepository,
                                contentMapper = ContentUiMapper(),
                                dispatchers = DispatcherProvider.Default()
                            )
                        }
                    )
                    val state = contentViewModel.state.collectAsStateWithLifecycle()
                    when (val state = state.value) {
                        is HierarchyScreenState.Error -> HierarchyErrorScreen(
                            message = state.message,
                            onRetry = contentViewModel::refresh
                        )

                        is HierarchyScreenState.Loading -> LoadingContentPlaceholder(
                            levels = listOf(0, 1, 1, 2, 0, 1, 2, 3)
                        )

                        is HierarchyScreenState.Success -> HierarchyScreen(
                            contentList = state.hierarchyList,
                            onClick = { title, imageUrl ->
                                backStack.add(ImageDetail(title = title, imageUrl = imageUrl))
                            })
                    }

                }

                is ImageDetail -> NavEntry(key = key) {
                    ImageContent(
                        title = key.title,
                        imageUrl = key.imageUrl,
                    )
                }

                else -> error("Unknown key: $key")
            }
        }
    )
}
