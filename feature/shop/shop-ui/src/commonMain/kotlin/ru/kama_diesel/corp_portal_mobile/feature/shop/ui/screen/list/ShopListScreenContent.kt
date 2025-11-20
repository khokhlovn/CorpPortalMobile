package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.domain.model.CartItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.CartAddingState
import ru.kama_diesel.corp_portal_mobile.resources.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopListScreenContent(
    shopItems: List<ShopItem>,
    cartItems: List<CartItem>,
    selectedSorter: Sorter,
    selectedFilter: Filter,
    isRefreshing: Boolean,
    cartAddingState: CartAddingState,
    onRefresh: () -> Unit,
    onSorterChange: (Sorter) -> Unit,
    onFilterChange: (Filter) -> Unit,
    onResetFilters: () -> Unit,
    onShopItemClick: (Int) -> Unit,
    onAddToCartClick: (Int) -> Unit,
    onToCartClick: () -> Unit,
) {
    val state = rememberPullToRefreshState()

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
                    columns = GridCells.Fixed(count = 2),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
                ) {
                    itemsIndexed(items = shopItems) { index, shopItem ->
                        ShopItemContent(
                            item = shopItem,
                            cartAddingState = cartAddingState,
                            onShopItemClick = {
                                onShopItemClick(index)
                            },
                            onAddToCartClick = {
                                onAddToCartClick(shopItem.id)
                            }
                        )
                    }
                }
            }
        }
        if (cartItems.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
            ) {
                FloatingActionButton(
                    onClick = onToCartClick,
                ) {
                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ) {
                                Text(text = cartItems.size.toString())
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
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
    item: ShopItem,
    cartAddingState: CartAddingState,
    onShopItemClick: () -> Unit,
    onAddToCartClick: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .height(244.dp)
            .clickable {
                onShopItemClick()
            },
        shape = RoundedCornerShape(size = 12.dp),
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.inverseSurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .weight(1f),
        ) {
            val pagerState = rememberPagerState(pageCount = { item.imagePaths?.size ?: 1 })

            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .height(120.dp),
                state = pagerState,
            ) { page ->
                AsyncImage(
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    model = item.imagePaths?.get(page),
                    placeholder = painterResource(Res.drawable.placeholder),
                    error = painterResource(Res.drawable.placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }

            if (!item.imagePaths.isNullOrEmpty() && item.imagePaths!!.size > 1) {
                Spacer(modifier = Modifier.height(4.dp))
                PagerIndicator(item.imagePaths!!.size, pagerState.currentPage)
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
                text = stringResource(
                    resource = if (item.isAvailable) {
                        Res.string.in_stock
                    } else {
                        Res.string.to_order
                    }
                ),
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.outline,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    text = item.price.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
                if (cartAddingState is CartAddingState.Adding) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(28.dp),
                    )
                } else {
                    Button(
                        modifier = Modifier
                            .height(28.dp)
                            .width(68.dp),
                        shape = ShapeDefaults.Small,
                        contentPadding = PaddingValues(all = 4.dp),
                        onClick = onAddToCartClick,
                    ) {
                        Text(
                            text = stringResource(
                                resource = if (item.isAvailable) {
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

@Composable
fun PagerIndicator(pageCount: Int, currentPageIndex: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement
            .spacedBy(
                space = 2.dp,
                alignment = Alignment.CenterHorizontally
            ),
    ) {
        repeat(pageCount) { iteration ->
            val color =
                if (currentPageIndex == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
            Box(
                modifier = modifier
                    .clip(CircleShape)
                    .background(color)
                    .size(6.dp)
            )
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
