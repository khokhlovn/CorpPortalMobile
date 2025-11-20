package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.add_cart
import ru.kama_diesel.corp_portal_mobile.resources.order

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopItemDetailsDialog(
    shopItem: ShopItem,
    onCloseClick: () -> Unit,
    onAddToCartClick: () -> Unit,
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
                                imageVector = Icons.Default.Close,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                contentDescription = null,
                            )
                        }
                    }
                },
                bottomBar = {
                    Row(
                        modifier = Modifier.padding(all = 12.dp)
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = ShapeDefaults.Small,
                            onClick = {},
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
