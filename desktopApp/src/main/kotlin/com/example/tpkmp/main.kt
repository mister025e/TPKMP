package com.example.tpkmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.tpkmp.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "TPKMP",
        ) {
            App()
        }
    }
}