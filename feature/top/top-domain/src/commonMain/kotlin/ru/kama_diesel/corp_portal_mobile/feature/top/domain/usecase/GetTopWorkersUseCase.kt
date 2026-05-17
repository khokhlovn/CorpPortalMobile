package ru.kama_diesel.corp_portal_mobile.feature.top.domain.usecase

import io.ktor.client.plugins.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ITopRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.WallOfFameItem

@Inject
class
GetTopWorkersUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val topRepository: ITopRepository,
) {
    suspend operator fun invoke(): List<WallOfFameItem> {
        return try {
            topRepository.getTopWorkers()
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            listOf()
        } catch (_: Exception) {
            listOf()
        }
    }
}
