package com.example.tpkmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tpkmp.presentation.ui.AppNavigation
import com.example.tpkmp.presentation.ui.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AppNavigation(modifier = Modifier.padding(innerPadding))
        }
    }
}
