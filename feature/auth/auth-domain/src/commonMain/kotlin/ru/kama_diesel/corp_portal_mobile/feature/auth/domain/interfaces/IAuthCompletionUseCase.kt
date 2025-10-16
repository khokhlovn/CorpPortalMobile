package ru.kama_diesel.corp_portal_mobile.feature.auth.domain.interfaces

interface IAuthCompletionUseCase {
    suspend operator fun invoke(name: String)
}