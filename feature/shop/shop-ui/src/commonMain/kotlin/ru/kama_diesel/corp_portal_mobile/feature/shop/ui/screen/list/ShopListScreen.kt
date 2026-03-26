package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.details.ShopItemDetailsDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.CartAddingState
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListViewState
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.account_circle_24px
import ru.kama_diesel.corp_portal_mobile.resources.icon_currency
import ru.kama_diesel.corp_portal_mobile.resources.menu_24px
import ru.kama_diesel.corp_portal_mobile.resources.shop

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopListScreen(
    viewState: ShopListViewState,
    drawerState: DrawerState,
    onToProfileClick: () -> Unit,
    onRefresh: () -> Unit,
    onSorterChange: (Sorter) -> Unit,
    onFilterChange: (Filter) -> Unit,
    onResetFilters: () -> Unit,
    onShopItemClick: (Int) -> Unit,
    onAddToCartClick: (Int) -> Unit,
    onToCartClick: () -> Unit,
    onToOrdersClick: () -> Unit,
    onCloseDialogClick: () -> Unit,
    onUpdateQuantityClick: (Int, Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(Res.string.shop))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.menu_24px),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = null,
                        )
                    }
                },
                actions = {
                    if (viewState.balance != null) {
                        Text(
                            text = viewState.balance.toString(),
                            maxLines = 1,
                            fontSize = 20.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Image(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(Res.drawable.icon_currency),
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    IconButton(
                        onClick = onToProfileClick,
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.account_circle_24px),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = null,
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
        ) {
            ShopListScreenContent(
                shopItems = viewState.sortedShopItems,
                cartItems = viewState.cartItems,
                orderItems = viewState.orderItems,
                selectedSorter = viewState.selectedSorter,
                selectedFilter = viewState.selectedFilter,
                isRefreshing = viewState.isLoading,
                onRefresh = onRefresh,
                onShopItemClick = onShopItemClick,
                onSorterChange = onSorterChange,
                onFilterChange = onFilterChange,
                onResetFilters = onResetFilters,
                onAddToCartClick = onAddToCartClick,
                onToCartClick = onToCartClick,
                onToOrdersClick = onToOrdersClick,
                onUpdateQuantityClick = onUpdateQuantityClick,
                onDeleteClick = onDeleteClick,
            )
        }
    }

    when (val dialog = viewState.dialog) {
        is ShopListDialog.Details -> {
            val cartItem = viewState.cartItems.find { it.itemId == dialog.shopItem.id }
            ShopItemDetailsDialog(
                shopItem = dialog.shopItem,
                quantity = cartItem?.quantity ?: 0,
                cartAddingState = viewState.sortedShopItems.find { it.id == dialog.shopItem.id }?.cartAddingState
                    ?: CartAddingState.No,
                onCloseClick = onCloseDialogClick,
                onAddToCartClick = onAddToCartClick,
                onAddClick = {
                    if ((cartItem?.quantity ?: 0) < dialog.shopItem.quantity) {
                        onUpdateQuantityClick(cartItem?.inCartItemId ?: 0,  cartItem?.quantity?.plus(1) ?: 0)
                    }
                },
                onRemoveClick = {
                    onUpdateQuantityClick(cartItem?.inCartItemId ?: 0, cartItem?.quantity?.minus(1) ?: 0)
                },
                onDeleteClick = {
                    onDeleteClick(cartItem?.inCartItemId ?: 0)
                },
            )
        }

        ShopListDialog.No -> Unit
    }
}
