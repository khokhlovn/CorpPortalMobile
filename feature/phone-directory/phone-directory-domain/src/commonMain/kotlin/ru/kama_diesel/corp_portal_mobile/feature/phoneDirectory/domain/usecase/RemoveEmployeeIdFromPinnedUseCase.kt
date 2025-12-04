package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.domain.usecase

import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IPhoneDirectoryRepository

@Inject
class RemoveEmployeeIdFromPinnedUseCase(
    private val phoneDirectoryRepository: IPhoneDirectoryRepository,
) {
    operator fun invoke(id: String) {
        phoneDirectoryRepository.removePinnedEmployeeId(id = id)
    }
}
