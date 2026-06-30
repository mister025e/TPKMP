package com.example.tpkmp.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tpkmp.di.apiModule
import com.example.tpkmp.di.viewModelModule
import com.example.tpkmp.presentation.ui.MyError
import com.example.tpkmp.presentation.ui.WeatherGallery
import com.example.tpkmp.presentation.ui.theme.AppTheme
import com.example.tpkmp.presentation.viewmodel.MainViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import tpkmp.shared.generated.resources.Res
import tpkmp.shared.generated.resources.load_data_button
import tpkmp.shared.generated.resources.search_city_hint

@Composable
fun SearchBar(modifier: Modifier = Modifier, searchText: MutableState<String>, onSearch: () -> Unit) {
    // Lifting the string resource call out of the label lambda to avoid deadlocks in Previews
    val searchHint = stringResource(Res.string.search_city_hint)
    TextField(
        value = searchText.value,
        onValueChange = { searchText.value = it },
        modifier = modifier,
        label = { Text(searchHint) },
        trailingIcon = {
            if (searchText.value.isNotEmpty()) {
                IconButton(onClick = { searchText.value = "" }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                }
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch() })
    )
}

@Composable
fun SearchScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {
    val list by mainViewModel.dataList.collectAsStateWithLifecycle()
    val isRunning by mainViewModel.runInProgress.collectAsStateWithLifecycle()
    val errorMessage by mainViewModel.errorMessage.collectAsStateWithLifecycle()
    val searchText = rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (errorMessage.isBlank() && list.isEmpty()) {
            mainViewModel.loadWeathers("Paris")
        }
    }

    Column(modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {
        val keyboardController = LocalSoftwareKeyboardController.current
        SearchBar(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            searchText = searchText,
            onSearch = {
                mainViewModel.loadWeathers(searchText.value)
                keyboardController?.hide()
            }
        )

        MyError(errorMessage = errorMessage)

        AnimatedVisibility(
            visible = isRunning,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        WeatherGallery(
            modifier = Modifier.weight(1f),
            list = list,
            onPictureClick = {}
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            // Lifting the string resource call out of the button content lambda
            val loadDataText = stringResource(Res.string.load_data_button)
            Button(
                onClick = {
                    mainViewModel.loadWeathers(searchText.value)
                    keyboardController?.hide()
                },
                enabled = !isRunning
            ) {
                Text(loadDataText)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true,
           uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
@Composable
fun SearchScreenPreview() {
    KoinApplication(application = { modules(apiModule, viewModelModule) }) {
        val viewModel = koinViewModel<MainViewModel>()
        LaunchedEffect(Unit) {
            viewModel.runInProgress.value = true
            viewModel.errorMessage.value = "Une erreur est survenue"
            viewModel.dataList.value = emptyList()
        }
        AppTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                SearchScreen(modifier = Modifier.padding(innerPadding), mainViewModel = viewModel)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenWithDataPreview() {
    KoinApplication(application = { modules(apiModule, viewModelModule) }) {
        val viewModel = koinViewModel<MainViewModel>()
        LaunchedEffect(Unit) {
            viewModel.loadFakeData()
        }
        AppTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                SearchScreen(modifier = Modifier.padding(innerPadding), mainViewModel = viewModel)
            }
        }
    }
}
