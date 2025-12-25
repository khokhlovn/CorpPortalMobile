package ru.kama_diesel.corp_portal_mobile.common.domain.interfaces

import ru.kama_diesel.corp_portal_mobile.common.domain.model.ProfileItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TopWorkerItem

interface ITopRepository {
    suspend fun getTopWorkers(): List<TopWorkerItem>
}
