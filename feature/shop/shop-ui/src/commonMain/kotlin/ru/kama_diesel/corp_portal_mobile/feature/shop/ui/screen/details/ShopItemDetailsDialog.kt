package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.component.ShopItemQuantityComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.CartAddingState
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopItemUIModel
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.add_cart
import ru.kama_diesel.corp_portal_mobile.resources.close_24px
import ru.kama_diesel.corp_portal_mobile.resources.order

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopItemDetailsDialog(
    shopItem: ShopItemUIModel,
    cartAddingState: CartAddingState,
    quantity: Int,
    onCloseClick: () -> Unit,
    onAddToCartClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Dialog(onDismissRequest = { onCloseClick() }) {
        Card(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.inverseSurface, shape = RoundedCornerShape(12.dp))
                .fillMaxSize(),
        ) {
            Scaffold(
                topBar = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        Text(
                            modifier = Modifier.weight(1f).padding(all = 12.dp),
                            text = shopItem.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.scrim,
                        )
                        IconButton(
                            onClick = onCloseClick,
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.close_24px),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                contentDescription = null,
                            )
                        }
                    }
                },
                bottomBar = {
                    Row(
                        modifier = Modifier
                            .height(72.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (cartAddingState is CartAddingState.Adding) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(36.dp),
                            )
                        } else if (quantity > 0) {
                            ShopItemQuantityComponent(
                                modifier = Modifier
                                    .width(100.dp),
                                quantity = quantity,
                                onAddClick = onAddClick,
                                onRemoveClick = onRemoveClick,
                                onDeleteClick = onDeleteClick,
                            )
                        } else {
                            Button(
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                                    .fillMaxWidth(),
                                shape = ShapeDefaults.Small,
                                onClick = {
                                    onAddToCartClick(shopItem.id)
                                },
                            ) {
                                Text(
                                    text = stringResource(
                                        resource = if (shopItem.isAvailable) {
                                            Res.string.add_cart
                                        } else {
                                            Res.string.order
                                        }
                                    ),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                },
                containerColor = MaterialTheme.colorScheme.inverseSurface,
            ) { paddingValues ->
                ShopItemDetailsContent(
                    modifier = Modifier.padding(paddingValues = paddingValues),
                    shopItem = shopItem,
                )
            }
        }
    }
}
