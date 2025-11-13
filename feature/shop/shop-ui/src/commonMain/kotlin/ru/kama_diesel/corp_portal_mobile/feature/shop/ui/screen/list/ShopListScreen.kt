package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import ru.kama_diesel.corp_portal_mobile.common.ui.component.LoadingDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopListScreen(
    viewState: ShopListViewState,
    onRefresh: () -> Unit,
    onShopItemClick: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        ShopListScreenContent(
            shopItems = viewState.shopItems,
            isRefreshing = viewState.isLoading,
            scrollEnabled = !expanded,
            onRefresh = onRefresh,
            onShopItemClick = onShopItemClick,
        )

        when (viewState.dialog) {
            ShopListDialog.Loading -> LoadingDialog()
            ShopListDialog.No -> Unit
        }
    }
}
