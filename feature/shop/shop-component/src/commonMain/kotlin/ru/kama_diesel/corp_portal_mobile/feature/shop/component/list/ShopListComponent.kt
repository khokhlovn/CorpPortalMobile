package ru.kama_diesel.corp_portal_mobile.feature.shop.component.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.common.component.registerAndGetSavedState
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.di.ShopFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.list.di.ShopListDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.list.di.create
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListViewState

class ShopListComponent(
    componentContext: ComponentContext,
    shopFlowDIComponent: ShopFlowDIComponent,
) : ComponentContext by componentContext {

    private val savedState: ShopListViewState = registerAndGetSavedState(
        key = SHOP_LIST_SAVED_STATE,
        initialValue = ShopListViewState(
            shopItems = listOf(),
            dialog = ShopListDialog.No,
            isLoading = true,
        ),
        deserialization = ShopListViewState.serializer(),
        serialization = ShopListViewState.serializer()
    ) {
        viewModel.currentState
    }

    private val diComponent = instanceKeeper.getOrCreate {
        ShopListDIComponent::class.create(shopFlowDIComponent, savedState)
    }

    val viewModel = diComponent.viewModel

    companion object Companion {
        private const val SHOP_LIST_SAVED_STATE = "SHOP_LIST_SAVED_STATE"
    }
}
