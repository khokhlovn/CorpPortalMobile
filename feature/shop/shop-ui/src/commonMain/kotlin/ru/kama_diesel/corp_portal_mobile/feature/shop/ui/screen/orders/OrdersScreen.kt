package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders.model.OrdersViewState
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.arrow_back_24px
import ru.kama_diesel.corp_portal_mobile.resources.orders

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    viewState: OrdersViewState,
    onRefresh: () -> Unit,
    onBackClick: () -> Unit,
    onSorterChange: (Sorter) -> Unit,
    onCancelOrderClick: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(Res.string.orders))
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.arrow_back_24px),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
        ) {
            OrdersScreenContent(
                orderItems = viewState.sortedOrderItems,
                shopItems = viewState.shopItems,
                selectedSorter = viewState.selectedSorter,
                isRefreshing = viewState.isLoading,
                onRefresh = onRefresh,
                onSorterChange = onSorterChange,
                onCancelOrderClick = onCancelOrderClick,
            )
        }
    }
}
