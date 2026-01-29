package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen

import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.di.ProfileScope
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase.GetBalanceUseCase
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase.GetCartItemsCountUseCase
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase.GetOrdersCountUseCase
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase.GetProfileImagePathUseCase
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase.GetProfileUseCase
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.model.ProfileViewState

@ProfileScope
@Inject
class ProfileViewModel(
    private val logoutUseCase: ILogoutUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val getCartItemsCountUseCase: GetCartItemsCountUseCase,
    private val getProfileImagePathUseCase: GetProfileImagePathUseCase,
    private val getOrdersCountUseCase: GetOrdersCountUseCase,
    private val initialState: ProfileViewState,
) : BaseStateViewModel<ProfileViewState>() {

    init {
        getData()
    }

    fun getData() {
        getProfileInfo()
    }

    fun onLogoutClick() {
        logoutUseCase()
    }

    fun onCartClick() {

    }

    fun onOrdersHistoryClick() {

    }

    private fun getProfileInfo() {
        coroutineScope.launch {
            setState {
                copy(
                    isLoading = true,
                )
            }
            val profileItem = getProfileUseCase() ?: return@launch
            val imagePath = getProfileImagePathUseCase()
            val balance = getBalanceUseCase()
            val cartItemsCount = getCartItemsCountUseCase()
            val ordersCount = getOrdersCountUseCase()
            setState {
                copy(
                    profileItem = profileItem,
                    imagePath = imagePath,
                    balance = balance,
                    cartItemsCount = cartItemsCount,
                    ordersCount = ordersCount,
                    isLoading = false,
                    isFirstLoading = false,
                )
            }
        }
    }

    override fun createInitialState() = initialState
}
