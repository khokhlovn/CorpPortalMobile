package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.domain.model.OrderStatus
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders.model.OrderItemUIModel
import ru.kama_diesel.corp_portal_mobile.resources.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreenContent(
    orderItems: List<OrderItemUIModel>,
    shopItems: List<ShopItem>,
    selectedSorter: Sorter,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onSorterChange: (Sorter) -> Unit,
    onCancelOrderClick: (Int) -> Unit,
) {
    val state = rememberPullToRefreshState()

    Column(
        modifier = Modifier
            .background(color = Color(red = 243, green = 243, blue = 243))
            .fillMaxSize(),
    ) {
        OrdersFilterPanel(
            selectedSorter = selectedSorter,
            onSorterChange = onSorterChange,
        )
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
                        isRefreshing = isRefreshing,
                        onCancelOrderClick = onCancelOrderClick,
                    )
                }
            }
        }
    }
}

@Composable
fun OrderItemContent(
    orderItem: OrderItemUIModel,
    shopItems: List<ShopItem>,
    isRefreshing: Boolean,
    onCancelOrderClick: (Int) -> Unit,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(Res.string.order_number, orderItem.id.toString()),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.scrim,
                )
                if (orderItem.status == OrderStatus.Ordered && !isRefreshing) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    )
                    val interactionSource = remember { MutableInteractionSource() }
                    Text(
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                            ) {
                                if (!isRefreshing) {
                                    onCancelOrderClick(orderItem.id)
                                }
                            },
                        text = stringResource(Res.string.cancel),
                        fontSize = 12.sp,
                        lineHeight = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
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
                    CoilImage(
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(shape = RoundedCornerShape(8.dp))
                            .aspectRatio(1f),
                        imageModel = { shopItem?.imagePaths?.firstOrNull() },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                        ),
                        loading = {
                            Box(modifier = Modifier.matchParentSize()) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        },
                        failure = {
                            painterResource(resource = Res.drawable.placeholder)
                        },
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
                            OrderStatus.Completed -> Res.string.completed_at
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
                Spacer(modifier = Modifier.width(2.dp))
                Image(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(Res.drawable.icon_currency),
                    contentDescription = null,
                )
            }
        }
    }
}

@Serializable
enum class Sorter(val stringResourceId: StringResource) {
    DateIncreasing(stringResourceId = Res.string.by_date_increasing),
    DateDecreasing(stringResourceId = Res.string.by_date_decreasing),
    SumIncreasing(stringResourceId = Res.string.by_sum_increasing),
    SumDecreasing(stringResourceId = Res.string.by_sum_decreasing),
}
