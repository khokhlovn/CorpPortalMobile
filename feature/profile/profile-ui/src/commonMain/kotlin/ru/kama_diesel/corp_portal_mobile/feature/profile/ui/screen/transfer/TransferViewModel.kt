package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.transfer

import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.di.TransferScope
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase.GetMyInfoUseCase
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase.GetUserIdsWithNamesUseCase
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase.TransferThxUseCase
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.api.IProfileFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.transfer.model.TransferViewState

@TransferScope
@Inject
class TransferViewModel(
    private val initialState: TransferViewState,
    private val getUserIdsWithNamesUseCase: GetUserIdsWithNamesUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val transferThxUseCase: TransferThxUseCase,
    routerHolder: RouterHolder<IProfileFlowRouter>,
) : BaseStateViewModel<TransferViewState>() {

    private val router by routerHolder

    init {
        getData()
    }

    fun getData() {
        getUsers()
    }

    fun back() {
        router.back()
    }

    fun onUserNameChange(name: String) {
        setState {
            copy(
                selectedUserId = null,
                userName = name,
                filteredUserIdsWithNames = userIdsWithNames.filter { item -> item.fullName.contains(other = name, ignoreCase = true) }
            )
        }
    }

    fun onUserSelect(userId: Int) {
        setState {
            copy(
                selectedUserId = userId,
                userName = userIdsWithNames.find { item -> item.userId == userId }?.fullName ?: "",
                filteredUserIdsWithNames = userIdsWithNames.filter { item -> item.userId == userId },
            )
        }
    }

    fun onAmountSelect(amount: Int) {
        setState {
            copy(
                amount = amount,
            )
        }
    }

    fun onTransferClick() {
        val userId = currentState.selectedUserId
        val amount = currentState.amount
        val availableAmount = currentState.availableAmount
        if (userId != null && amount != null && amount <= availableAmount) {
            coroutineScope.launch {
                setState {
                    copy(
                        isLoading = true,
                    )
                }

                val isTransferThxSuccess = transferThxUseCase(userId = userId, amount = amount)

                setState {
                    copy(
                        isLoading = false,
                        showSuccessSnackbar = isTransferThxSuccess,
                        userName = "",
                        amount = null,
                        selectedUserId = null,
                    )
                }
            }

            getData()
        }
    }

    fun onHideSnackbar() {
        setState {
            copy(
                showSuccessSnackbar = false,
            )
        }
    }

    private fun getUsers() {
        coroutineScope.launch {
            setState {
                copy(
                    isLoading = true,
                )
            }
            val users = getUserIdsWithNamesUseCase().sortedBy { it.fullName }
            val myInfo = getMyInfoUseCase() ?: return@launch
            setState {
                copy(
                    userName = "",
                    availableAmount = myInfo.giftBalance,
                    userIdsWithNames = users,
                    filteredUserIdsWithNames = users,
                    selectedUserId = null,
                    amount = null,
                    isLoading = false,
                )
            }
        }
    }

    override fun createInitialState() = initialState
}
