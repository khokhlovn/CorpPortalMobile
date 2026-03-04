package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ProfileItem
import ru.kama_diesel.corp_portal_mobile.common.ui.component.FullScreenImageViewer
import ru.kama_diesel.corp_portal_mobile.resources.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProfileScreenContent(
    profileItem: ProfileItem,
    imagePath: String?,
    balance: Int,
    cartItemsCount: Int,
    ordersCount: Int,
    isRefreshing: Boolean,
    isFirstLoading: Boolean,
    onRefresh: () -> Unit,
    onBalanceClick: () -> Unit,
    onCartClick: () -> Unit,
    onOrdersHistoryClick: () -> Unit,
) {
    val state = rememberPullToRefreshState()

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(red = 243, green = 243, blue = 243))
    ) {
        PullToRefreshBox(
            modifier = Modifier.weight(1f).fillMaxSize(),
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
            if (!isFirstLoading) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(all = 12.dp),
                ) {
                    item {
                        ProfileCard(
                            profileItem = profileItem,
                            imagePath = imagePath,
                        )
                    }
                    item {
                        BalanceAndShopCards(
                            balance = balance,
                            cartItemsCount = cartItemsCount,
                            ordersCount = ordersCount,
                            onBalanceClick = onBalanceClick,
                            onCartClick = onCartClick,
                            onOrdersHistoryClick = onOrdersHistoryClick,
                        )
                    }
                    item {
                        DocumentsCard()
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileCard(
    profileItem: ProfileItem,
    imagePath: String?
) {
    Card(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.inverseSurface, shape = RoundedCornerShape(12.dp))
            .fillMaxWidth(),
    ) {
        var selectedImageIndex by remember { mutableIntStateOf(0) }
        var isImageOpened by remember { mutableStateOf(false) }

        if (imagePath != null && isImageOpened) {
            FullScreenImageViewer(
                selectedIndex = selectedImageIndex,
                imagePaths = listOf(imagePath),
                onClose = { isImageOpened = false },
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inverseSurface)
                .padding(vertical = 12.dp, horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            val interactionSource = remember { MutableInteractionSource() }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                CoilImage(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clip(shape = CircleShape)
                        .size(80.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = {
                                selectedImageIndex = 0
                                isImageOpened = true
                            }
                        ),
                    imageModel = { imagePath },
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
                        painterResource(resource = Res.drawable.person_placeholder)
                    },
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .heightIn(max = 80.dp),
                    text = profileItem.fullName,
                    autoSize = TextAutoSize.StepBased(),
                    fontWeight = FontWeight.Bold,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    maxLines = 3,
                    color = MaterialTheme.colorScheme.scrim,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                if (profileItem.department.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            modifier = Modifier.widthIn(min = 100.dp),
                            text = stringResource(Res.string.department_details),
                            fontWeight = FontWeight.Medium,
                            style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.scrim,
                        )
                        Text(
                            text = profileItem.department,
                            fontSize = 12.sp,
                            style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                            color = MaterialTheme.colorScheme.outline,
                        )
                    }
                }
                if (profileItem.position.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            modifier = Modifier.widthIn(min = 100.dp),
                            text = stringResource(Res.string.position_details),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                            color = MaterialTheme.colorScheme.scrim,
                        )
                        Text(
                            text = profileItem.position,
                            fontSize = 12.sp,
                            style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                            color = MaterialTheme.colorScheme.outline,
                        )
                    }
                }
                if (profileItem.mail.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            modifier = Modifier.widthIn(min = 100.dp),
                            text = stringResource(Res.string.mail_details),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                            color = MaterialTheme.colorScheme.scrim,
                        )
                        Text(
                            text = profileItem.mail,
                            fontSize = 12.sp,
                            style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                            color = MaterialTheme.colorScheme.outline,
                        )
                    }
                }
                if (!profileItem.mobile.isNullOrEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            modifier = Modifier.widthIn(min = 100.dp),
                            text = stringResource(Res.string.mobile_details),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                            color = MaterialTheme.colorScheme.scrim,
                        )
                        Text(
                            text = "+7${profileItem.mobile}",
                            fontSize = 12.sp,
                            style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                            color = MaterialTheme.colorScheme.outline,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BalanceAndShopCards(
    balance: Int,
    cartItemsCount: Int,
    ordersCount: Int,
    onBalanceClick: () -> Unit,
    onCartClick: () -> Unit,
    onOrdersHistoryClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ShopCard(
            modifier = Modifier.weight(1f),
            cartItemsCount = cartItemsCount,
            ordersCount = ordersCount,
            onCartClick = onCartClick,
            onOrdersHistoryClick = onOrdersHistoryClick,
        )
        BalanceCard(
            modifier = Modifier.weight(1f),
            balance = balance,
            onBalanceClick = onBalanceClick,
        )
    }
}

@Composable
private fun BalanceCard(
    modifier: Modifier = Modifier,
    balance: Int,
    onBalanceClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.inverseSurface, shape = RoundedCornerShape(12.dp))
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inverseSurface)
                .padding(vertical = 16.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(Res.drawable.universal_currency_alt_24px),
                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier
                        .widthIn(min = 50.dp)
                        .fillMaxWidth()
                        .weight(1f),
                    text = stringResource(Res.string.balance_count),
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.scrim,
                )
                Text(
                    modifier = Modifier.clickable {
                        onBalanceClick()
                    },
                    text = balance.toString(),
                    fontSize = 12.sp,
                    maxLines = 1,
                    style = TextStyle.Default.copy(textDecoration = TextDecoration.Underline),
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
                Image(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(Res.drawable.icon_currency),
                    contentDescription = null,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(Res.drawable.notifications_unread_24px),
                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.widthIn(min = 90.dp),
                    text = stringResource(Res.string.notifications),
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.scrim,
                )
                Text(
                    text = balance.toString(),
                    fontSize = 12.sp,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(Res.drawable.table_restaurant_24px),
                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.widthIn(min = 90.dp),
                    text = stringResource(Res.string.reservations),
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.scrim,
                )
                Text(
                    text = balance.toString(),
                    fontSize = 12.sp,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(Res.drawable.event_24px),
                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.widthIn(min = 90.dp),
                    text = stringResource(Res.string.events),
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.scrim,
                )
                Text(
                    text = balance.toString(),
                    fontSize = 12.sp,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
            }
        }
    }
}

@Composable
private fun ShopCard(
    modifier: Modifier = Modifier,
    cartItemsCount: Int,
    ordersCount: Int,
    onCartClick: () -> Unit,
    onOrdersHistoryClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.inverseSurface, shape = RoundedCornerShape(12.dp)),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseSurface)
                .padding(vertical = 16.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(Res.string.shop),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.scrim,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    modifier = Modifier
                        .widthIn(min = 100.dp)
                        .clickable {
                            if (ordersCount > 0) {
                                onOrdersHistoryClick()
                            }
                        },
                    text = stringResource(Res.string.orders_history),
                    fontWeight = FontWeight.Medium,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        modifier = Modifier.widthIn(min = 100.dp),
                        text = stringResource(Res.string.in_cart_count),
                        fontWeight = FontWeight.Medium,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.scrim,
                    )
                    Text(
                        modifier = Modifier.clickable {
                            if (cartItemsCount > 0) {
                                onCartClick()
                            }
                        },
                        text = cartItemsCount.toString(),
                        fontSize = 12.sp,
                        maxLines = 1,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                    )
                }
            }
        }
    }
}

@Composable
private fun DocumentsCard() {
    Card(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.inverseSurface, shape = RoundedCornerShape(12.dp))
            .fillMaxWidth(),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseSurface)
                .padding(vertical = 16.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(Res.string.documents),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.scrim,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = stringResource(Res.string.documents_for_familiarization),
                    fontWeight = FontWeight.Medium,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.scrim,
                )
                Text(
                    text = stringResource(Res.string.billing_sheets),
                    fontWeight = FontWeight.Medium,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.scrim,
                )
                Text(
                    text = stringResource(Res.string.vacation_calculation),
                    fontWeight = FontWeight.Medium,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.scrim,
                )
                Text(
                    text = stringResource(Res.string.submit_application),
                    fontWeight = FontWeight.Medium,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.scrim,
                )
                Text(
                    text = stringResource(Res.string.declare_absence),
                    fontWeight = FontWeight.Medium,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.scrim,
                )
                Text(
                    text = stringResource(Res.string.contact_ceo),
                    fontWeight = FontWeight.Medium,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.scrim,
                )
            }
        }
    }
}
