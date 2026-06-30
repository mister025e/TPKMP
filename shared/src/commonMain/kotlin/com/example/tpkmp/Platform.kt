package com.example.tpkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform