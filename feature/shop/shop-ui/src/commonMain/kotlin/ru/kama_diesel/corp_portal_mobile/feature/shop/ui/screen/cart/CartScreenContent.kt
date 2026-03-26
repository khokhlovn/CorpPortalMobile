package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartAddingState
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartItemUIModel
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.MakingOrderState
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.component.ShopItemQuantityComponent
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.delete_24px
import ru.kama_diesel.corp_portal_mobile.resources.drop_selected
import ru.kama_diesel.corp_portal_mobile.resources.icon_currency
import ru.kama_diesel.corp_portal_mobile.resources.make_order
import ru.kama_diesel.corp_portal_mobile.resources.placeholder
import ru.kama_diesel.corp_portal_mobile.resources.select_all

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenContent(
    cartItems: List<CartItemUIModel>,
    shopItems: List<ShopItem>,
    balance: Int?,
    totalSum: Int?,
    makingOrderState: MakingOrderState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onUpdateQuantityClick: (Int, Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onOrderClick: () -> Unit,
    onCheckedChange: (Int, Boolean) -> Unit,
    onSelectAllClick: (Boolean) -> Unit,
    onDropSelectedItems: () -> Unit,
) {
    val state = rememberPullToRefreshState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.inverseSurface),
    ) {
        if (cartItems.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.inverseSurface)
                    .padding(vertical = 4.dp)
                    .padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = cartItems.all { it.isChecked },
                    onCheckedChange = { isChecked ->
                        onSelectAllClick(isChecked)
                    },
                )
                val selectAllInteractionSource = remember { MutableInteractionSource() }
                Text(
                    modifier = Modifier
                        .clickable(
                            interactionSource = selectAllInteractionSource,
                            indication = null,
                            onClick = {
                                onSelectAllClick(!cartItems.all { it.isChecked })
                            },
                        )
                        .padding(all = 4.dp),
                    text = stringResource(Res.string.select_all),
                    maxLines = 1,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                val dropSelectedInteractionSource = remember { MutableInteractionSource() }
                Text(
                    modifier = Modifier
                        .clickable(
                            interactionSource = dropSelectedInteractionSource,
                            indication = null,
                            enabled = cartItems.any { it.isChecked },
                            onClick = onDropSelectedItems,
                        )
                        .padding(all = 4.dp),
                    text = stringResource(Res.string.drop_selected),
                    maxLines = 1,
                    fontWeight = FontWeight.Medium,
                    color = if (cartItems.any { it.isChecked }) {
                        MaterialTheme.colorScheme.inverseOnSurface
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    fontSize = 14.sp
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
        }
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
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                itemsIndexed(items = cartItems) { index, cartItem ->
                    val shopItem = shopItems.find { shopItem -> shopItem.id == cartItem.itemId }
                    CartItemContent(
                        cartItem = cartItem,
                        shopItemName = shopItem?.name ?: "",
                        shopItemPrice = shopItem?.price ?: 0,
                        shopItemImagePath = shopItem?.imagePaths?.firstOrNull(),
                        shopItemQuantity = shopItem?.quantity ?: 0,
                        isChecked = cartItem.isChecked,
                        onAddClick = {
                            onUpdateQuantityClick(cartItem.inCartItemId, cartItem.quantity.plus(1))
                        },
                        onRemoveClick = {
                            onUpdateQuantityClick(cartItem.inCartItemId, cartItem.quantity.minus(1))
                        },
                        onDeleteClick = {
                            onDeleteClick(cartItem.inCartItemId)
                        },
                        onCheckedChange = onCheckedChange,
                    )
                    if (index < cartItems.size - 1) {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Color(red = 243, green = 243, blue = 243),
                        )
                    }
                }
            }
        }
        if (cartItems.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = totalSum.toString(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.icon_currency),
                    contentDescription = null,
                )
            }
            Row(
                modifier = Modifier
                    .height(72.dp)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (makingOrderState is MakingOrderState.Process) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp),
                    )
                } else {
                    Button(
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth(),
                        enabled = balance != null && totalSum != null && balance >= totalSum,
                        shape = ShapeDefaults.Small,
                        onClick = onOrderClick,
                    ) {
                        Text(
                            text = stringResource(resource = Res.string.make_order),
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemContent(
    cartItem: CartItemUIModel,
    shopItemName: String,
    shopItemPrice: Int,
    shopItemImagePath: String?,
    shopItemQuantity: Int,
    isChecked: Boolean,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onCheckedChange: (Int, Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(140.dp)
            .fillMaxWidth()
            .padding(end = 16.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked ->
                onCheckedChange(cartItem.inCartItemId, isChecked)
            },
        )
        CoilImage(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxHeight()
                .clip(shape = RoundedCornerShape(8.dp))
                .aspectRatio(1f),
            imageModel = { shopItemImagePath },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = shopItemName,
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    color = MaterialTheme.colorScheme.scrim,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${shopItemPrice}/шт.",
                    fontSize = 12.sp,
                    lineHeight = 14.sp,
                    color = MaterialTheme.colorScheme.outline,
                )
            }
            Row(
                modifier = Modifier
                    .height(28.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically),
                        text = (shopItemPrice * cartItem.quantity).toString(),
                        textAlign = TextAlign.End,
                        autoSize = TextAutoSize.StepBased(maxFontSize = 20.sp),
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
                        .fillMaxHeight()
                        .width(112.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (cartItem.cartAddingState is CartAddingState.Adding) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(24.dp),
                        )
                    } else {
                        Icon(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable(onClick = onDeleteClick),
                            painter = painterResource(Res.drawable.delete_24px),
                            tint = MaterialTheme.colorScheme.inverseOnSurface,
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        ShopItemQuantityComponent(
                            modifier = Modifier
                                .padding(vertical = 4.dp),
                            inCartQuantity = cartItem.quantity,
                            totalQuantity = shopItemQuantity,
                            onAddClick = onAddClick,
                            onRemoveClick = onRemoveClick,
                            onDeleteClick = onDeleteClick,
                        )
                    }
                }
            }
        }
    }
}
