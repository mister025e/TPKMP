package com.example.tpkmp

import androidx.compose.ui.window.ComposeUIViewController
import com.example.tpkmp.di.initKoin

fun MainViewController() = ComposeUIViewController { App() }

fun initKoinIos() {
    initKoin()
}