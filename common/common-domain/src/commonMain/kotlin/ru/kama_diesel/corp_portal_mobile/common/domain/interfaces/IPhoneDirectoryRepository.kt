package ru.kama_diesel.corp_portal_mobile.common.domain.interfaces

import ru.kama_diesel.corp_portal_mobile.common.domain.model.EmployeeItem

interface IPhoneDirectoryRepository {
    suspend fun getPhoneDirectory(): List<EmployeeItem>
    fun savePinnedEmployeeId(id: String)
    fun removePinnedEmployeeId(id: String)
    fun getPinnedEmployeeIds(): List<String>
}
