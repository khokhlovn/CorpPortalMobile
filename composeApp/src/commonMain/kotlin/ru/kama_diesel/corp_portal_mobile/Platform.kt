package ru.kama_diesel.corp_portal_mobile

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform