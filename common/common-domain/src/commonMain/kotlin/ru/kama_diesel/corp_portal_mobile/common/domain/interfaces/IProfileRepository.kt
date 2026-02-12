package ru.kama_diesel.corp_portal_mobile.common.domain.interfaces

import ru.kama_diesel.corp_portal_mobile.common.domain.model.MeItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ProfileItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ThxHistoryItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.UserIdWithNameItem

interface IProfileRepository {
    suspend fun getProfile(): ProfileItem
    suspend fun getCartItemsCount(): Int
    suspend fun getOrdersCount(): Int
    suspend fun getUserIdsWithNames(): List<UserIdWithNameItem>
    suspend fun transferThx(userId: Int, amount: Int)
    suspend fun getMyInfo(): MeItem
    suspend fun getThxHistory(): List<ThxHistoryItem>
    suspend fun getWeeklyThx()
}
