package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.domain.model.OrderItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.OrderStatus
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem
import ru.kama_diesel.corp_portal_mobile.resources.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreenContent(
    orderItems: List<OrderItem>,
    shopItems: List<ShopItem>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
) {
    val state = rememberPullToRefreshState()

    Column(
        modifier = Modifier
            .background(color = Color(red = 243, green = 243, blue = 243))
            .fillMaxSize(),
    ) {
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize().weight(1f),
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
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            ) {
                items(items = orderItems) { orderItem ->
                    OrderItemContent(
                        orderItem = orderItem,
                        shopItems = shopItems,
                    )
                }
            }
        }
    }
}

@Composable
fun OrderItemContent(
    orderItem: OrderItem,
    shopItems: List<ShopItem>,
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(size = 12.dp),
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.inverseSurface)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxSize(),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.order_number, orderItem.id.toString()),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.scrim,
            )
            Spacer(modifier = Modifier.height(4.dp))
            orderItem.items.forEach { orderPositionItem ->
                val shopItem = shopItems.find { shopItem -> shopItem.id == orderPositionItem.id }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(shape = RoundedCornerShape(8.dp))
                            .aspectRatio(1f),
                        model = shopItem?.imagePaths?.firstOrNull(),
                        placeholder = painterResource(Res.drawable.placeholder),
                        error = painterResource(Res.drawable.placeholder),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        text = shopItem?.name ?: "",
                        fontSize = 12.sp,
                        lineHeight = 14.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.scrim,
                    )
                    Text(
                        text = stringResource(
                            Res.string.price_quantity,
                            shopItem?.price ?: 0,
                            orderPositionItem.quantity
                        ),
                        fontSize = 12.sp,
                        lineHeight = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = stringResource(
                        resource = when (orderItem.status) {
                            OrderStatus.Ordered -> Res.string.ordered_at
                            OrderStatus.Cancelled -> Res.string.cancelled_at
                        },
                        orderItem.date,
                    ),
                    fontSize = 12.sp,
                    lineHeight = 14.sp,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = orderItem.items.sumOf { orderPositionItem ->
                        (shopItems.find { shopItem ->
                            shopItem.id == orderPositionItem.id
                        }?.price ?: 0) * orderPositionItem.quantity
                    }.toString(),
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
            }
        }
    }
}
