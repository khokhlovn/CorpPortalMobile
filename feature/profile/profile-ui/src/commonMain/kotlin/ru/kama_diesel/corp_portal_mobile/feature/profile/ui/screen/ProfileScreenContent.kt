package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
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
    isRefreshing: Boolean,
    isFirstLoading: Boolean,
    onRefresh: () -> Unit,
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
                    contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                ) {
                    item {
                        ProfileCard(
                            profileItem = profileItem,
                            imagePath = imagePath,
                        )
                    }
                    item {
                        BalanceCard(
                            balance = balance,
                        )
                    }
                    item {
                        ShopCard(
                            cartItemsCount = cartItemsCount,
                        )
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
                .padding(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            val interactionSource = remember { MutableInteractionSource() }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clip(shape = CircleShape)
                        .size(80.dp)
                        .background(Color.Black)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = {
                                selectedImageIndex = 0
                                isImageOpened = true
                            }
                        ),
                    model = imagePath,
                    placeholder = painterResource(Res.drawable.person_placeholder),
                    error = painterResource(Res.drawable.person_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
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
                            style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.scrim,
                        )
                        Text(
                            text = profileItem.department,
                            fontSize = 14.sp,
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
                            fontSize = 14.sp,
                            style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                            color = MaterialTheme.colorScheme.scrim,
                        )
                        Text(
                            text = profileItem.position,
                            fontSize = 14.sp,
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
                            fontSize = 14.sp,
                            style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                            color = MaterialTheme.colorScheme.scrim,
                        )
                        Text(
                            text = profileItem.mail,
                            fontSize = 14.sp,
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
                            fontSize = 14.sp,
                            style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                            color = MaterialTheme.colorScheme.scrim,
                        )
                        Text(
                            text = "+7${profileItem.mobile}",
                            fontSize = 14.sp,
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
private fun BalanceCard(
    balance: Int,
) {
    Card(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.inverseSurface, shape = RoundedCornerShape(12.dp))
            .fillMaxWidth(),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inverseSurface)
                .padding(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    modifier = Modifier.widthIn(min = 100.dp),
                    text = stringResource(Res.string.balance),
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.scrim,
                )
                Text(
                    text = balance.toString(),
                    fontSize = 14.sp,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    color = MaterialTheme.colorScheme.outline,
                )
            }
        }
    }
}

@Composable
private fun ShopCard(
    cartItemsCount: Int,
) {
    Card(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.inverseSurface, shape = RoundedCornerShape(12.dp))
            .fillMaxWidth(),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inverseSurface)
                .padding(all = 12.dp),
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
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        modifier = Modifier.widthIn(min = 100.dp),
                        text = stringResource(Res.string.in_cart_count),
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.scrim,
                    )
                    Text(
                        text = cartItemsCount.toString(),
                        fontSize = 14.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
            }
        }
    }
}
