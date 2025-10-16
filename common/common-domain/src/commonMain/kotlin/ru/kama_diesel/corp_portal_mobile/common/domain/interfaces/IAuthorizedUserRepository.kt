package ru.kama_diesel.corp_portal_mobile.common.domain.interfaces

interface IAuthorizedUserRepository {
    suspend fun isUserAuthorized(): Boolean
    suspend fun setUserAuthorized()
    fun logout()
}