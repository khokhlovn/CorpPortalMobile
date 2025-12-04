package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.check_24px

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrdersFilterPanel(
    selectedSorter: Sorter,
    onSorterChange: (Sorter) -> Unit,
) {
    var sorterExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.inverseSurface)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val focusManager = LocalFocusManager.current
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
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
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 10.dp),
                    text = stringResource(selectedSorter.stringResourceId),
                    fontSize = 12.sp,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.scrim,
                )
                TrailingIcon(expanded = sorterExpanded)
            }

            ExposedDropdownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.inverseSurface),
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
                                fontSize = 12.sp,
                                lineHeight = 14.sp,
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
    }
}
