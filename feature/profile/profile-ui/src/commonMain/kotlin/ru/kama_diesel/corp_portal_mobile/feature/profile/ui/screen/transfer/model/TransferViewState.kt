package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.transfer.model

import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.common.domain.model.UserIdWithNameItem

@Serializable
data class TransferViewState(
    val selectedUserId: Int?,
    val userName: String,
    val amount: Int?,
    val availableAmount: Int,
    val userIdsWithNames: List<UserIdWithNameItem>,
    val filteredUserIdsWithNames: List<UserIdWithNameItem>,
    val isLoading: Boolean,
    val showSuccessSnackbar: Boolean,
)
