package com.tyshko.todoappkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform