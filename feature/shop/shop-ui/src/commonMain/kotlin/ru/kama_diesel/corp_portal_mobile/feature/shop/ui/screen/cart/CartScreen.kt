package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartViewState
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.arrow_back_24px
import ru.kama_diesel.corp_portal_mobile.resources.cart
import ru.kama_diesel.corp_portal_mobile.resources.icon_currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewState: CartViewState,
    onRefresh: () -> Unit,
    onBackClick: () -> Unit,
    onUpdateQuantityClick: (Int, Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onOrderClick: () -> Unit,
    onCheckedChange: (Int, Boolean) -> Unit,
    onSelectAllClick: (Boolean) -> Unit,
    onDropSelectedItems: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(Res.string.cart))
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
                }
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
        ) {
            CartScreenContent(
                cartItems = viewState.cartItems,
                shopItems = viewState.shopItems,
                balance = viewState.balance,
                totalSum = viewState.totalSum,
                makingOrderState = viewState.makingOrderState,
                isRefreshing = viewState.isLoading,
                onRefresh = onRefresh,
                onUpdateQuantityClick = onUpdateQuantityClick,
                onDeleteClick = onDeleteClick,
                onOrderClick = onOrderClick,
                onCheckedChange = onCheckedChange,
                onSelectAllClick = onSelectAllClick,
                onDropSelectedItems = onDropSelectedItems,
            )
        }
    }
}
