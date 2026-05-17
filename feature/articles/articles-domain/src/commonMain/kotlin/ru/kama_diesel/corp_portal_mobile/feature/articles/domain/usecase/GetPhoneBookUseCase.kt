package ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase

import io.ktor.client.plugins.ClientRequestException
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IArticlesRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IPhoneDirectoryRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.EmployeeItem

@Inject
class GetPhoneBookUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val articlesRepository: IArticlesRepository,
) {
    suspend operator fun invoke(): List<EmployeeItem> {
        return try {
            articlesRepository.getPhoneDirectory()
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            listOf()
        } catch (_: Exception) {
            listOf()
        }
    }
}
