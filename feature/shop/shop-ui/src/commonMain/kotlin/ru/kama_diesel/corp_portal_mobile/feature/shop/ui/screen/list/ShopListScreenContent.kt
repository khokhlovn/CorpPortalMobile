package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.layout.LazyLayoutCacheWindow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.crossfade.CrossfadePlugin
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.domain.model.CartItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.OrderItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.OrderStatus
import ru.kama_diesel.corp_portal_mobile.common.ui.component.PagerIndicator
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.component.ShopItemQuantityComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.CartAddingState
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopItemUIModel
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.add_cart
import ru.kama_diesel.corp_portal_mobile.resources.all
import ru.kama_diesel.corp_portal_mobile.resources.by_name
import ru.kama_diesel.corp_portal_mobile.resources.by_price_decreasing
import ru.kama_diesel.corp_portal_mobile.resources.by_price_increasing
import ru.kama_diesel.corp_portal_mobile.resources.icon_currency
import ru.kama_diesel.corp_portal_mobile.resources.in_stock
import ru.kama_diesel.corp_portal_mobile.resources.list_alt_24px
import ru.kama_diesel.corp_portal_mobile.resources.not_available
import ru.kama_diesel.corp_portal_mobile.resources.order
import ru.kama_diesel.corp_portal_mobile.resources.placeholder
import ru.kama_diesel.corp_portal_mobile.resources.shopping_cart_24px
import ru.kama_diesel.corp_portal_mobile.resources.to_order

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ShopListScreenContent(
    shopItems: List<ShopItemUIModel>,
    cartItems: List<CartItem>,
    orderItems: List<OrderItem>,
    selectedSorter: Sorter,
    selectedFilter: Filter,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onSorterChange: (Sorter) -> Unit,
    onFilterChange: (Filter) -> Unit,
    onResetFilters: () -> Unit,
    onShopItemClick: (Int) -> Unit,
    onAddToCartClick: (Int) -> Unit,
    onToCartClick: () -> Unit,
    onToOrdersClick: () -> Unit,
    onUpdateQuantityClick: (Int, Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
) {
    val state = rememberPullToRefreshState()
    val percentCacheWindow = LazyLayoutCacheWindow(
        aheadFraction = 1f,
        behindFraction = 0.5f,
    )
    val gridState = rememberLazyGridState(cacheWindow = percentCacheWindow)

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(red = 243, green = 243, blue = 243)),
        ) {
            ShopListFilterPanel(
                selectedSorter = selectedSorter,
                selectedFilter = selectedFilter,
                onSorterChange = onSorterChange,
                onFilterChange = onFilterChange,
                onResetFilters = onResetFilters,
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
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    state = gridState,
                    columns = GridCells.Fixed(count = 2),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
                ) {
                    itemsIndexed(items = shopItems) { index, shopItem ->
                        val cartItem = cartItems.find { it.itemId == shopItem.id }
                        ShopItemContent(
                            item = shopItem,
                            quantity = cartItem?.quantity ?: 0,
                            onShopItemClick = {
                                onShopItemClick(index)
                            },
                            onAddToCartClick = {
                                onAddToCartClick(shopItem.id)
                            },
                            onAddClick = {
                                if ((cartItem?.quantity ?: 0) < shopItem.quantity) {
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
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
        ) {
            if (orderItems.isNotEmpty()) {
                FloatingActionButton(
                    onClick = onToOrdersClick,
                ) {
                    BadgedBox(
                        badge = {
                            val activeOrders = orderItems.count { it.status == OrderStatus.Ordered }
                            if (activeOrders > 0) {
                                Badge(
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                ) {
                                    Text(text = orderItems.count { it.status == OrderStatus.Ordered }.toString())
                                }
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.list_alt_24px),
                            contentDescription = null,
                        )
                    }
                }
            }
            if (cartItems.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                FloatingActionButton(
                    onClick = onToCartClick,
                ) {
                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ) {
                                Text(text = cartItems.sumOf { it.quantity }.toString())
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.shopping_cart_24px),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShopItemContent(
    item: ShopItemUIModel,
    quantity: Int,
    onShopItemClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { item.imagePaths?.size ?: 1 })


    Card(
        modifier = Modifier.fillMaxWidth()
            .height(244.dp)
            .clickable {
                onShopItemClick()
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        shape = RoundedCornerShape(size = 12.dp),
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.inverseSurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .weight(1f),
        ) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .height(120.dp),
                state = pagerState,
            ) { page ->
                CoilImage(
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    imageModel = { item.imagePaths?.get(page) },
                    component = rememberImageComponent {
                        +CrossfadePlugin(
                            duration = 550
                        )
                    },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        requestSize = IntSize(500, 500),
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
            }


            if (!item.imagePaths.isNullOrEmpty() && item.imagePaths.size > 1) {
                Spacer(modifier = Modifier.height(4.dp))
                PagerIndicator(item.imagePaths.size, pagerState.currentPage)
            } else {
                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = item.name,
                fontSize = 10.sp,
                lineHeight = 12.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.scrim,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = if (item.isActive) {
                    stringResource(Res.string.in_stock, item.quantity)
                } else {
                    stringResource(Res.string.not_available)
                },
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.outline,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    Text(
                        modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically),
                        text = item.price.toString(),
                        fontSize = 14.sp,
                        lineHeight = 16.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                    )
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(Res.drawable.icon_currency),
                        contentDescription = null,
                    )
                }
                Row(
                    modifier = Modifier
                        .height(28.dp)
                        .width(68.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (item.cartAddingState is CartAddingState.Adding) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(24.dp),
                        )
                    } else if (quantity > 0) {
                        ShopItemQuantityComponent(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 4.dp),
                            inCartQuantity = quantity,
                            totalQuantity = item.quantity,
                            onAddClick = onAddClick,
                            onRemoveClick = onRemoveClick,
                            onDeleteClick = onDeleteClick,
                        )
                    } else {
                        Button(
                            modifier = Modifier.fillMaxSize(),
                            shape = ShapeDefaults.Small,
                            contentPadding = PaddingValues(all = 4.dp),
                            onClick = {
                                if (item.isActive) {
                                    onAddToCartClick()
                                }
                            },
                        ) {
                            Text(
                                text = stringResource(
                                    resource = if (item.isActive) {
                                        Res.string.add_cart
                                    } else {
                                        Res.string.order
                                    }
                                ),
                                fontSize = 8.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Serializable
enum class Sorter(val stringResourceId: StringResource) {
    PriceIncreasing(stringResourceId = Res.string.by_price_increasing),
    PriceDecreasing(stringResourceId = Res.string.by_price_decreasing),
    Name(stringResourceId = Res.string.by_name),
}

@Serializable
enum class Filter(val stringResourceId: StringResource) {
    All(stringResourceId = Res.string.all),
    Available(stringResourceId = Res.string.in_stock),
    NotAvailable(stringResourceId = Res.string.to_order),
}
