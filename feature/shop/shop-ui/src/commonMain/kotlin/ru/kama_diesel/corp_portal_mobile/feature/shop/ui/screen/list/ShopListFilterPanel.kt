package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.check_24px
import ru.kama_diesel.corp_portal_mobile.resources.reset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ShopListFilterPanel(
    selectedSorter: Sorter,
    selectedFilter: Filter,
    onSorterChange: (Sorter) -> Unit,
    onFilterChange: (Filter) -> Unit,
    onResetFilters: () -> Unit,
) {
    var sorterExpanded by remember { mutableStateOf(false) }
    var filterExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.inverseSurface)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val focusManager = LocalFocusManager.current
        ExposedDropdownMenuBox(
            modifier = Modifier.width(148.dp),
            expanded = sorterExpanded,
            onExpandedChange = {
                sorterExpanded = !sorterExpanded
                if (!sorterExpanded) {
                    focusManager.clearFocus()
                }
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true),
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(selectedSorter.stringResourceId),
                    fontSize = 10.sp,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.scrim,
                )
                TrailingIcon(expanded = sorterExpanded)
            }

            ExposedDropdownMenu(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.inverseSurface),
                expanded = sorterExpanded,
                matchAnchorWidth = false,
                onDismissRequest = {
                    sorterExpanded = false
                    focusManager.clearFocus()
                }
            ) {
                Sorter.entries.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(item.stringResourceId),
                                color = if (item == selectedSorter) {
                                    MaterialTheme.colorScheme.inverseOnSurface
                                } else {
                                    MaterialTheme.colorScheme.scrim
                                },
                                fontSize = 10.sp,
                                lineHeight = 12.sp,
                            )
                        },
                        trailingIcon = {
                            if (item == selectedSorter) {
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    painter = painterResource(Res.drawable.check_24px),
                                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                                    contentDescription = null,
                                )
                            }
                        },
                        onClick = {
                            sorterExpanded = false
                            focusManager.clearFocus()
                            onSorterChange(item)
                        },
                    )
                }
            }
        }

        ExposedDropdownMenuBox(
            modifier = Modifier.width(80.dp),
            expanded = filterExpanded,
            onExpandedChange = {
                filterExpanded = !filterExpanded
                if (!filterExpanded) {
                    focusManager.clearFocus()
                }
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true),
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(selectedFilter.stringResourceId),
                    fontSize = 10.sp,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.scrim,
                )
                TrailingIcon(expanded = filterExpanded)
            }

            ExposedDropdownMenu(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.inverseSurface),
                expanded = filterExpanded,
                matchAnchorWidth = false,
                onDismissRequest = {
                    filterExpanded = false
                    focusManager.clearFocus()
                }
            ) {
                Filter.entries.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(item.stringResourceId),
                                color = if (item == selectedFilter) {
                                    MaterialTheme.colorScheme.inverseOnSurface
                                } else {
                                    MaterialTheme.colorScheme.scrim
                                },
                                fontSize = 10.sp,
                                lineHeight = 12.sp,
                            )
                        },
                        trailingIcon = {
                            if (item == selectedFilter) {
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    painter = painterResource(Res.drawable.check_24px),
                                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                                    contentDescription = null,
                                )
                            }
                        },
                        onClick = {
                            filterExpanded = false
                            focusManager.clearFocus()
                            onFilterChange(item)
                        },
                    )
                }
            }
        }
        val interactionSource = remember { MutableInteractionSource() }
        Text(
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = selectedSorter != Sorter.PriceIncreasing || selectedFilter != Filter.All,
                    onClick = onResetFilters,
                )
                .padding(all = 4.dp)
                .weight(1f),
            text = stringResource(Res.string.reset),
            maxLines = 1,
            fontWeight = FontWeight.Medium,
            color = if (selectedSorter != Sorter.PriceIncreasing || selectedFilter != Filter.All) {
                MaterialTheme.colorScheme.inverseOnSurface
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            fontSize = 12.sp
        )
    }
}