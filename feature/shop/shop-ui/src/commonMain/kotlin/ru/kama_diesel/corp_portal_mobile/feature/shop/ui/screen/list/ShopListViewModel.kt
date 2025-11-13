package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list

import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.di.ShopListScope
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.GetShopListUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.api.IShopFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListViewState

@ShopListScope
@Inject
class ShopListViewModel(
    private val getShopListUseCase: GetShopListUseCase,
    routerHolder: RouterHolder<IShopFlowRouter>,
    private val initialState: ShopListViewState,
) : BaseStateViewModel<ShopListViewState>() {

    private val router by routerHolder

    init {
        getData()
    }

    fun getData() {
        coroutineScope.launch {
            val shopItems = getShopListUseCase()
            setState {
                copy(
                    shopItems = shopItems,
                    isLoading = false,
                    dialog = ShopListDialog.No,
                )
            }
        }
    }

    fun onShopItemClick() {

    }

    override fun createInitialState() = initialState
}
