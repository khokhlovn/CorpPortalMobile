package ru.kama_diesel.corp_portal_mobile.common.domain.interfaces

import ru.kama_diesel.corp_portal_mobile.common.domain.model.OrderItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ProfileItem

interface IProfileRepository {
    suspend fun getProfile(): ProfileItem
    suspend fun getBalance(): Int
    suspend fun getProfileImagePath(): String?
    suspend fun getCartItemsCount(): Int
    suspend fun getOrdersCount(): Int
}
