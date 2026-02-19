package ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase

import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IProfileRepository

@Inject
class TransferThxCeoUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val profileRepository: IProfileRepository,
) {
    suspend operator fun invoke(userId: Int, amount: Int): String {
        return try {
            val error = profileRepository.transferThxCeo(userId = userId, amount = amount)
            error ?: "OK"
        } catch (exception: ClientRequestException) {
            if (exception.response.status.value == 404 || exception.response.status.value == 403) {
                val response = exception.response.bodyAsText()
                val jsonMap = Json.parseToJsonElement(response)
                    .jsonObject
                jsonMap["error"].toString()
            } else {
                logoutUseCase.invoke()
                ""
            }
        } catch (_: Exception) {
            ""
        }
    }
}
