package ru.kama_diesel.corp_portal_mobile.feature.auth.domain.interfaces

interface IUserRepository {
    suspend fun login(username: String, password: String)
}
