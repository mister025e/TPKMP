package com.example.tpkmp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tpkmp.presentation.ui.screens.SearchScreen
import com.example.tpkmp.presentation.viewmodel.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = koinViewModel()
    NavHost(
        navController = navController,
        startDestination = "search",
        modifier = modifier
    ) {
        composable("search") {
            SearchScreen(mainViewModel = mainViewModel)
        }
    }
}
