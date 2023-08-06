package com.maxjin.kmmcomposenative

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform