package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.CorpPortalApi
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IPhoneDirectoryRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.EmployeeItem

@Inject
class PhoneDirectoryRepository(
    private val corpPortalApi: CorpPortalApi,
    private val preferences: IPreferences,
) : IPhoneDirectoryRepository {

    override suspend fun getPhoneDirectory(): List<EmployeeItem> {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getPhoneBook().employees?.map {
                EmployeeItem(
                    fullName = it.fullName,
                    position = it.position,
                    department = it.department,
                    service = it.service,
                    mail = it.mail,
                    mobile = it.mobile,
                )
            } ?: listOf()
        }
    }

    override fun savePinnedEmployeeId(id: String) {
        preferences.putString(key = PINNED_EMPLOYEE_IDS, getPinnedEmployeeIds().plus(id).joinToString())
    }

    override fun removePinnedEmployeeId(id: String) {
        preferences.putString(key = PINNED_EMPLOYEE_IDS, getPinnedEmployeeIds().minus(id).joinToString())
    }

    override fun getPinnedEmployeeIds(): List<String> {
        return preferences.getString(key = PINNED_EMPLOYEE_IDS).split(", ")
    }

    companion object {
        private const val PINNED_EMPLOYEE_IDS = "PINNED_EMPLOYEE_IDS"
    }
}
