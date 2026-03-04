package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.transfer

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.domain.model.UserIdWithNameItem
import ru.kama_diesel.corp_portal_mobile.resources.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TransferScreenContent(
    amount: Int?,
    availableAmount: Int,
    role: Int,
    selectedUserId: Int?,
    userName: String,
    filteredUserIdsWithNames: List<UserIdWithNameItem>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onUserNameChange: (String) -> Unit,
    onUserSelect: (Int) -> Unit,
    onAmountSelect: (Int) -> Unit,
    onAmountSelectCeo: (String) -> Unit,
    onTransferClick: () -> Unit,
    onTransferCeoClick: () -> Unit,
) {
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.inverseSurface)
            .verticalScroll(rememberScrollState()),
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 14.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.widthIn(max = 200.dp),
                    text = stringResource(Res.string.available_thx),
                    fontSize = 14.sp,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    color = MaterialTheme.colorScheme.scrim,
                )
                Text(
                    text = availableAmount.toString(),
                    fontSize = 18.sp,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
                Image(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(Res.drawable.icon_currency),
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            var userExpanded by remember { mutableStateOf(false) }
            val focusManager = LocalFocusManager.current
            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(),
                expanded = userExpanded,
                onExpandedChange = {
                    userExpanded = !userExpanded
                    if (!userExpanded) {
                        focusManager.clearFocus()
                    }
                }
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable),
                    value = userName,
                    onValueChange = onUserNameChange,
                    trailingIcon = {
                        TrailingIcon(expanded = userExpanded)
                    },
                    textStyle = TextStyle(fontSize = 14.sp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors().copy(
                        focusedTextColor = MaterialTheme.colorScheme.scrim,
                        unfocusedTextColor = MaterialTheme.colorScheme.scrim,
                    ),
                    label = { Text(text = stringResource(Res.string.select_user), fontSize = 14.sp) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                )

                ExposedDropdownMenu(
                    expanded = userExpanded && filteredUserIdsWithNames.isNotEmpty(),
                    containerColor = MaterialTheme.colorScheme.inverseSurface,
                    onDismissRequest = {
                        userExpanded = false
                        focusManager.clearFocus()
                    }
                ) {
                    filteredUserIdsWithNames.forEach { userItem ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = userItem.fullName,
                                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.scrim,
                                )
                            },
                            onClick = {
                                onUserSelect(userItem.userId)
                                userExpanded = false
                                focusManager.clearFocus()
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (role == 41 || role == 42) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = amount?.toString() ?: "",
                    onValueChange = onAmountSelectCeo,
                    textStyle = TextStyle(fontSize = 14.sp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors().copy(
                        focusedTextColor = MaterialTheme.colorScheme.scrim,
                        unfocusedTextColor = MaterialTheme.colorScheme.scrim,
                    ),
                    supportingText = { if (role == 42) Text(text = stringResource(Res.string.amount_limit_hint), fontSize = 12.sp) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                    label = { Text(text = stringResource(Res.string.enter_amount_thx), fontSize = 14.sp) },
                )
            } else {
                var amountExpanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    modifier = Modifier.fillMaxWidth(),
                    expanded = amountExpanded,
                    onExpandedChange = {
                        amountExpanded = !amountExpanded
                        if (!amountExpanded) {
                            focusManager.clearFocus()
                        }
                    }
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth()
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                        value = when (amount) {
                            1 -> {
                                stringResource(Res.string.amount_thx_one)
                            }

                            2 -> {
                                stringResource(Res.string.amount_thx_two)
                            }

                            3 -> {
                                stringResource(Res.string.amount_thx_three)
                            }

                            else -> {
                                ""
                            }
                        },
                        onValueChange = onUserNameChange,
                        trailingIcon = {
                            TrailingIcon(expanded = userExpanded)
                        },
                        textStyle = TextStyle(fontSize = 14.sp),
                        singleLine = true,
                        readOnly = true,
                        colors = OutlinedTextFieldDefaults.colors().copy(
                            focusedTextColor = MaterialTheme.colorScheme.scrim,
                            unfocusedTextColor = MaterialTheme.colorScheme.scrim,
                        ),
                        label = { Text(text = stringResource(Res.string.select_amount_thx), fontSize = 14.sp) },
                    )

                    ExposedDropdownMenu(
                        expanded = amountExpanded,
                        containerColor = MaterialTheme.colorScheme.inverseSurface,
                        onDismissRequest = {
                            amountExpanded = false
                            focusManager.clearFocus()
                        }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = stringResource(Res.string.amount_thx_one),
                                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.scrim,
                                )
                            },
                            onClick = {
                                amountExpanded = false
                                focusManager.clearFocus()
                                onAmountSelect(1)
                            },
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = stringResource(Res.string.amount_thx_two),
                                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.scrim,
                                )
                            },
                            onClick = {
                                amountExpanded = false
                                focusManager.clearFocus()
                                onAmountSelect(2)
                            },
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = stringResource(Res.string.amount_thx_three),
                                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.scrim,
                                )
                            },
                            onClick = {
                                amountExpanded = false
                                focusManager.clearFocus()
                                onAmountSelect(3)
                            },
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = ShapeDefaults.Medium,
                enabled = !isRefreshing && amount != null && amount <= availableAmount && selectedUserId != null
                        && (role != 42 || (role == 42 && amount <= 50)),
                onClick = {
                    focusManager.clearFocus()
                    if (role == 41 || role == 42) {
                        onTransferCeoClick()
                    } else {
                        onTransferClick()
                    }
                },
            ) {
                Text(
                    text = if (selectedUserId == null) {
                        stringResource(Res.string.user_not_selected)
                    } else if (amount == null) {
                        stringResource(Res.string.amount_not_selected)
                    } else if (amount > availableAmount) {
                        stringResource(Res.string.amount_is_more)
                    } else if (role == 42 && amount > 50) {
                        stringResource(Res.string.amount_limit)
                    } else {
                        stringResource(Res.string.transfer)
                    }
                )
            }
        }
    }
}
