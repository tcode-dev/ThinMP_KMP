package dev.tcode.thinmpk

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform