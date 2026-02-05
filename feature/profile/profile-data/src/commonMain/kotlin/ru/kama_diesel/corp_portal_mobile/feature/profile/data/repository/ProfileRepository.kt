package ru.kama_diesel.corp_portal_mobile.feature.profile.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.CorpPortalApi
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.TransferThxRequestData
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IProfileRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ProfileItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.UserIdWithNameItem

@Inject
class ProfileRepository(
    private val corpPortalApi: CorpPortalApi,
) : IProfileRepository {

    override suspend fun getProfile(): ProfileItem {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getProfile().profile.let {
                ProfileItem(
                    fullName = it.fullName,
                    position = it.position,
                    department = it.department,
                    mail = it.mail,
                    mobile = it.mobile,
                    username = it.username,
                    chief = it.chief,
                    personalMobile = it.personalMobile,
                    personalMail = it.personalMail,
                )
            }
        }
    }

    override suspend fun getBalance(): Int {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getMyInfo().user.balance
        }
    }

    override suspend fun getProfileImagePath(): String? {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getMyInfo().user.imagePath
        }
    }

    override suspend fun getCartItemsCount(): Int {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getCartData().cartItems?.sumOf { it.quantity }
        } ?: 0
    }

    override suspend fun getOrdersCount(): Int {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getOrders().carts?.size
        } ?: 0
    }

    override suspend fun getUserIdsWithNames(): List<UserIdWithNameItem> {
        return corpPortalApi.getUserIds().users?.map {
            UserIdWithNameItem(
                userId = it.userId,
                fullName = it.fullName,
            )
        } ?: listOf()
    }

    override suspend fun getGiftBalance(): Int {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getMyInfo().user.giftBalance
        }
    }

    override suspend fun transferThx(userId: Int, amount: Int) {
        return withContext(Dispatchers.IO) {
            corpPortalApi.transferThx(
                TransferThxRequestData(
                    userId = userId,
                    amount = amount,
                )
            )
        }
    }
}
