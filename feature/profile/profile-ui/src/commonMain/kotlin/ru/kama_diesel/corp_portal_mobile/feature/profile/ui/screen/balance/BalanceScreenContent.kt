package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.balance

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ThxHistoryItem
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders.BalanceFilterPanel
import ru.kama_diesel.corp_portal_mobile.resources.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BalanceScreenContent(
    balance: Int,
    giftBalance: Int,
    weeklyAward: Int,
    endOfWeekDate: String,
    fullName: String,
    filteredHistoryEvents: List<ThxHistoryItem>,
    query: String,
    selectedSorter: Sorter,
    isRefreshing: Boolean,
    hintsVisible: Boolean,
    onRefresh: () -> Unit,
    onQueryChange: (String) -> Unit,
    onQueryClear: () -> Unit,
    onSorterChange: (Sorter) -> Unit,
    onToTransferClick: () -> Unit,
    onToBalanceClick: () -> Unit,
    toShop: () -> Unit,
) {
    val state = rememberPullToRefreshState()
    val hints = remember {
        listOf(
            Hint(
                name = Res.string.hint_transfer_name,
                description = Res.string.hint_transfer_description,
                icon = Res.drawable.event_25,
            ),
            Hint(
                name = Res.string.hint_weekly_name,
                description = Res.string.hint_weekly_description,
                icon = Res.drawable.event_2,
            ),
            Hint(
                name = Res.string.hint_article_name,
                description = Res.string.hint_article_description,
                icon = Res.drawable.event_12,
            ),
            Hint(
                name = Res.string.hint_activity_name,
                description = Res.string.hint_activity_description,
                icon = Res.drawable.event_4,
            ),
            Hint(
                name = Res.string.hint_new_year_name,
                description = Res.string.hint_new_year_description,
                icon = Res.drawable.event_day,
            ),
            Hint(
                name = Res.string.hint_march_name,
                description = Res.string.hint_march_description,
                icon = Res.drawable.event_day,
            ),
            Hint(
                name = Res.string.hint_february_name,
                description = Res.string.hint_february_description,
                icon = Res.drawable.event_day,
            ),
            Hint(
                name = Res.string.hint_enterprise_name,
                description = Res.string.hint_enterprise_description,
                icon = Res.drawable.event_day,
            ),
            Hint(
                name = Res.string.hint_birthday_name,
                description = Res.string.hint_birthday_description,
                icon = Res.drawable.event_day,
            ),
            Hint(
                name = Res.string.hint_achievement_name,
                description = Res.string.hint_achievement_description,
                icon = Res.drawable.event_13,
            ),
            Hint(
                name = Res.string.hint_volunteer_name,
                description = Res.string.hint_volunteer_description,
                icon = Res.drawable.event_14,
            ),
            Hint(
                name = Res.string.hint_promotion_name,
                description = Res.string.hint_promotion_description,
                icon = Res.drawable.event_15,
            ),
            Hint(
                name = Res.string.hint_improvement_name,
                description = Res.string.hint_improvement_description,
                icon = Res.drawable.event_16,
            ),
            Hint(
                name = Res.string.hint_work_name,
                description = Res.string.hint_work_description,
                icon = Res.drawable.event_17,
            ),
            Hint(
                name = Res.string.hint_survey_name,
                description = Res.string.hint_survey_description,
                icon = Res.drawable.event_18,
            ),
            Hint(
                name = Res.string.hint_competition_name,
                description = Res.string.hint_competition_description,
                icon = Res.drawable.event_19,
            ),
            Hint(
                name = Res.string.hint_mentoring_name,
                description = Res.string.hint_mentoring_description,
                icon = Res.drawable.event_20,
            ),
            Hint(
                name = Res.string.hint_sport_name,
                description = Res.string.hint_sport_description,
                icon = Res.drawable.event_21,
            ),
            Hint(
                name = Res.string.hint_eco_name,
                description = Res.string.hint_eco_description,
                icon = Res.drawable.event_22,
            ),
            Hint(
                name = Res.string.hint_experience_name,
                description = Res.string.hint_experience_description,
                icon = Res.drawable.event_23,
            ),
            Hint(
                name = Res.string.hint_vaccination_name,
                description = Res.string.hint_vaccination_description,
                icon = Res.drawable.event_24,
            ),
            Hint(
                name = Res.string.hint_shop_name,
                description = Res.string.hint_shop_description,
                icon = Res.drawable.event_shop,
            ),
        )
    }

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                BalanceCard(
                    balance = balance,
                    giftBalance = giftBalance,
                    weeklyAward = weeklyAward,
                    endOfWeekDate = endOfWeekDate,
                    isRefreshing = isRefreshing,
                    onToTransferClick = onToTransferClick,
                    onToBalanceClick = onToBalanceClick,
                    toShop = toShop,
                )
            }

            if (hintsVisible) {
                items(items = hints, key = { hints.indexOf(it) }) { hint ->
                    HintCard(
                        modifier = Modifier,
                        hint = hint,
                    )
                }
            } else {
                stickyHeader {
                    SearchCard(
                        modifier = Modifier,
                        query = query,
                        selectedSorter = selectedSorter,
                        onQueryChange = onQueryChange,
                        onQueryClear = onQueryClear,
                        onSorterChange = onSorterChange,
                    )
                }

                items(items = filteredHistoryEvents, key = { it.transactionId }) { historyEvent ->
                    HistoryEventCard(
                        modifier = Modifier,
                        historyEvent = historyEvent,
                        fullName = fullName,
                    )
                }
            }
        }
    }
}

@Composable
private fun BalanceCard(
    balance: Int,
    giftBalance: Int,
    weeklyAward: Int,
    endOfWeekDate: String,
    isRefreshing: Boolean,
    onToTransferClick: () -> Unit,
    onToBalanceClick: () -> Unit,
    toShop: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        shape = RoundedCornerShape(size = 12.dp),
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.inverseSurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(Res.string.balance_count),
                    fontSize = 20.sp,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.scrim,
                )
                Text(
                    text = (balance + giftBalance).toString(),
                    fontSize = 20.sp,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(Res.drawable.icon_currency),
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = stringResource(Res.string.gift_count),
                    fontSize = 12.sp,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    color = MaterialTheme.colorScheme.scrim,
                )
                Text(
                    text = giftBalance.toString(),
                    fontSize = 12.sp,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
                Image(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(Res.drawable.icon_currency),
                    contentDescription = null,
                )
                if (giftBalance > 0) {
                    Text(
                        text = stringResource(Res.string.till_date, endOfWeekDate),
                        fontSize = 12.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier
                    .clickable(
                        enabled = giftBalance > 0 && !isRefreshing,
                        onClick = onToTransferClick,
                    ),
                text = stringResource(Res.string.transfer),
                color = if (giftBalance > 0 && !isRefreshing) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = stringResource(Res.string.available_count),
                    fontSize = 12.sp,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    color = MaterialTheme.colorScheme.scrim,
                )
                Text(
                    text = balance.toString(),
                    fontSize = 12.sp,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
                Image(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(Res.drawable.icon_currency),
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier
                    .clickable(
                        enabled = balance > 0 && !isRefreshing,
                        onClick = toShop,
                    ),
                text = stringResource(Res.string.go_to_shop),
                color = if (balance > 0 && !isRefreshing) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = stringResource(Res.string.weekly_count),
                    fontSize = 12.sp,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    color = MaterialTheme.colorScheme.scrim,
                )
                Text(
                    text = weeklyAward.toString(),
                    fontSize = 12.sp,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
                Image(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(Res.drawable.icon_currency),
                    contentDescription = null,
                )
                if (weeklyAward > 0) {
                    Text(
                        text = stringResource(Res.string.till_date, endOfWeekDate),
                        fontSize = 12.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier
                    .clickable(
                        enabled = weeklyAward > 0 && !isRefreshing,
                        onClick = onToBalanceClick,
                    ),
                text = stringResource(Res.string.transfer_to_balance),
                color = if (weeklyAward > 0 && !isRefreshing) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun SearchCard(
    modifier: Modifier = Modifier,
    query: String,
    selectedSorter: Sorter,
    onQueryChange: (String) -> Unit,
    onQueryClear: () -> Unit,
    onSorterChange: (Sorter) -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        shape = RoundedCornerShape(size = 12.dp),
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.inverseSurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
        ) {
            val focusManager = LocalFocusManager.current
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = query,
                maxLines = 1,
                placeholder = {
                    Text(text = stringResource(Res.string.search))
                },
                onValueChange = onQueryChange,
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.search_24px),
                        tint = MaterialTheme.colorScheme.inverseOnSurface,
                        contentDescription = null,
                    )
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                onQueryClear()
                                focusManager.clearFocus()
                            }
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.close_24px),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                contentDescription = null,
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Search,
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                    },
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedTextColor = MaterialTheme.colorScheme.scrim,
                    unfocusedTextColor = MaterialTheme.colorScheme.scrim,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
            )
            Spacer(modifier = Modifier.height(8.dp))
            BalanceFilterPanel(
                selectedSorter = selectedSorter,
                onSorterChange = onSorterChange,
            )
        }
    }
}

@Composable
private fun HistoryEventCard(
    modifier: Modifier = Modifier,
    historyEvent: ThxHistoryItem,
    fullName: String,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        shape = RoundedCornerShape(size = 12.dp),
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.inverseSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val drawableResource = getIconIdByEventId(eventId = historyEvent.eventId)
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(resource = drawableResource),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = historyEvent.description,
                    fontSize = 16.sp,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.scrim,
                )
                val recipientName = historyEvent.recipientName
                val creatorName = historyEvent.creatorName
                if (recipientName != null) {
                    if (historyEvent.creatorName == fullName && historyEvent.recipientName != fullName) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = stringResource(Res.string.recipient_with_name),
                                fontSize = 12.sp,
                                style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                                color = MaterialTheme.colorScheme.outline,
                            )
                            Text(
                                text = recipientName,
                                fontSize = 12.sp,
                                style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                                color = MaterialTheme.colorScheme.scrim,
                            )
                        }
                    }
                    if (historyEvent.recipientName == fullName && historyEvent.creatorName != fullName && historyEvent.creatorName != "admin") {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = stringResource(Res.string.creator_with_name),
                                fontSize = 12.sp,
                                style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                                color = MaterialTheme.colorScheme.outline,
                            )
                            Text(
                                text = creatorName,
                                fontSize = 12.sp,
                                style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                                color = MaterialTheme.colorScheme.scrim,
                            )
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.date_with_date),
                        fontSize = 12.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        color = MaterialTheme.colorScheme.outline,
                    )
                    Text(
                        text = historyEvent.date.split(" ")[0],
                        fontSize = 12.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        color = MaterialTheme.colorScheme.scrim,
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = historyEvent.amount.toString(),
                fontSize = 20.sp,
                style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(Res.drawable.icon_currency),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun HintCard(
    modifier: Modifier = Modifier,
    hint: Hint,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        shape = RoundedCornerShape(size = 12.dp),
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.inverseSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(resource = hint.icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = stringResource(hint.name),
                    fontSize = 16.sp,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.scrim,
                )
                Text(
                    text = stringResource(hint.description),
                    fontSize = 12.sp,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    color = MaterialTheme.colorScheme.scrim,
                )
            }
        }
    }
}

private fun getIconIdByEventId(eventId: Int?): DrawableResource {
    return when (eventId) {
        1 -> Res.drawable.event_1
        2,
        3 -> Res.drawable.event_2

        4 -> Res.drawable.event_4
        5 -> Res.drawable.event_17
        6 -> Res.drawable.event_2
        7,
        8,
        9,
        10,
        11 -> Res.drawable.event_day

        12 -> Res.drawable.event_12
        13 -> Res.drawable.event_13
        14 -> Res.drawable.event_14
        15 -> Res.drawable.event_15
        16 -> Res.drawable.event_16
        17 -> Res.drawable.event_17
        18 -> Res.drawable.event_18
        19 -> Res.drawable.event_19
        20 -> Res.drawable.event_20
        21 -> Res.drawable.event_21
        22 -> Res.drawable.event_22
        23 -> Res.drawable.event_23
        24 -> Res.drawable.event_24
        null -> Res.drawable.event_shop
        else -> Res.drawable.event_25
    }
}

@Serializable
enum class Sorter(val stringResourceId: StringResource) {
    DateIncreasing(stringResourceId = Res.string.by_date_increasing),
    DateDecreasing(stringResourceId = Res.string.by_date_decreasing),
    SumIncreasing(stringResourceId = Res.string.by_sum_increasing),
    SumDecreasing(stringResourceId = Res.string.by_sum_decreasing),
}

data class Hint(
    val name: StringResource,
    val description: StringResource,
    val icon: DrawableResource,
)
