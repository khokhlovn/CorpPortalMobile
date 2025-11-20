package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.domain.model.CartItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartAddingState
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.cart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenContent(
    cartItems: List<CartItem>,
    shopItems: List<ShopItem>,
    isRefreshing: Boolean,
    cartAddingState: CartAddingState,
    onRefresh: () -> Unit,
    onBackClick: () -> Unit,
    onAddToCartClick: (Int) -> Unit,
) {
    val state = rememberPullToRefreshState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.inverseSurface),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = onBackClick,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = MaterialTheme.colorScheme.scrim,
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(Res.string.cart),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
            )
        }
        Spacer(
            modifier = Modifier
                .height(4.dp)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.15f),
                            Color.Transparent,
                        )
                    )
                )
        )
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            state = state,
            isRefreshing = isRefreshing,
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = isRefreshing,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    state = state,
                )
            },
            onRefresh = onRefresh,
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
            ) {
                items(items = cartItems) { cartItem ->
                    CartItemContent(
                        cartItem = cartItem,
                        shopItemName = shopItems.find { shopItem -> shopItem.id == cartItem.itemId }?.name ?: "",
                        cartAddingState = cartAddingState,
                        onAddToCartClick = {},
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemContent(
    cartItem: CartItem,
    shopItemName: String,
    cartAddingState: CartAddingState,
    onAddToCartClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = shopItemName,
            color = MaterialTheme.colorScheme.outline,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = cartItem.quantity.toString(),
            color = MaterialTheme.colorScheme.outline,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
