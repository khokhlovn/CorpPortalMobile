package ru.kama_diesel.corp_portal_mobile.common.domain.interfaces

import ru.kama_diesel.corp_portal_mobile.common.domain.model.WallOfFameItem

interface ITopRepository {
    suspend fun getTopWorkers(): List<WallOfFameItem>
}
