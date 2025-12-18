package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.model

import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ProfileItem

@Serializable
data class ProfileViewState(
    val profileItem: ProfileItem,
    val imagePath: String?,
    val balance: Int,
    val cartItemsCount: Int,
    val isFirstLoading: Boolean,
    val isLoading: Boolean,
)
