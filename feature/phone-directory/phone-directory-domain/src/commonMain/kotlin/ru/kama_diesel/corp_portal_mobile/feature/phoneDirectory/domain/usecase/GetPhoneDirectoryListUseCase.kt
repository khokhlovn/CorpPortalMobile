package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.domain.usecase

import io.ktor.client.plugins.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IPhoneDirectoryRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.EmployeeItem

@Inject
class GetPhoneDirectoryListUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val phoneDirectoryRepository: IPhoneDirectoryRepository,
) {
    suspend operator fun invoke(): List<EmployeeItem> {
        return try {
            phoneDirectoryRepository.getPhoneDirectory()
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            listOf()
        } catch (_: Exception) {
            listOf()
        }
    }
}
