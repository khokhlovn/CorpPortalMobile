package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem
import ru.kama_diesel.corp_portal_mobile.resources.*

@Composable
fun ShopListScreenContent(
    shopItems: List<ShopItem>,
    isRefreshing: Boolean,
    scrollEnabled: Boolean,
    onRefresh: () -> Unit,
    onShopItemClick: () -> Unit,
) {
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(red = 243, green = 243, blue = 243)),
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
            userScrollEnabled = scrollEnabled,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
        ) {
            items(items = shopItems) { shopItem ->
                ShopItemContent(
                    item = shopItem,
                    scrollEnabled = scrollEnabled,
                    onShopItemClick = onShopItemClick,
                )
            }
        }
    }
}

@Composable
fun ShopItemContent(
    item: ShopItem,
    scrollEnabled: Boolean,
    onShopItemClick: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .height(244.dp)
            .then(
                if (scrollEnabled) {
                    Modifier
//                        .clickable {
//                            onShopItemClick()
//                        }
                } else {
                    Modifier
                }
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
            val pagerState = rememberPagerState(pageCount = { item.imagePaths?.size ?: 1 })

            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .height(120.dp),
                state = pagerState,
            ) { page ->
                AsyncImage(
                    modifier = Modifier.height(120.dp),
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
                Button(
                    modifier = Modifier
                        .height(28.dp)
                        .width(68.dp),
                    shape = ShapeDefaults.Small,
                    contentPadding = PaddingValues(all = 4.dp),
                    onClick = {},
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
