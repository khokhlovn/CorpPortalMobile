package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.layout.LazyLayoutCacheWindow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberConstraintsSizeResolver
import coil3.request.ImageRequest
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.model.EmployeeItemUIModel
import ru.kama_diesel.corp_portal_mobile.resources.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PhoneDirectoryListScreenContent(
    filteredEmployeeItems: List<EmployeeItemUIModel>,
    allEmployeeItemsCount: Int,
    selectedSorter: Sorter,
    selectedDirection: Direction,
    isRefreshing: Boolean,
    query: String,
    onRefresh: () -> Unit,
    onQueryChange: (String) -> Unit,
    onQueryClear: () -> Unit,
    onPinChange: (String, Boolean) -> Unit,
    onSorterChange: (Sorter) -> Unit,
    onDirectionChange: (Direction) -> Unit,
    onEmployeeItemClick: (EmployeeItemUIModel) -> Unit,
) {
    val state = rememberPullToRefreshState()
    val percentCacheWindow = LazyLayoutCacheWindow(
        aheadFraction = 1f,
        behindFraction = 0.5f,
    )
    val gridState = rememberLazyGridState(cacheWindow = percentCacheWindow)
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true },
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(color = Color(red = 243, green = 243, blue = 243))
        ) {
            TextField(
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
                colors = TextFieldDefaults.colors().copy(
                    focusedTextColor = MaterialTheme.colorScheme.scrim,
                    unfocusedTextColor = MaterialTheme.colorScheme.scrim,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.inverseSurface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.inverseSurface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.inverseSurface),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = {
                        onDirectionChange(
                            when (selectedDirection) {
                                Direction.Increasing -> Direction.Decreasing
                                Direction.Decreasing -> Direction.Increasing
                            }
                        )
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(
                            when (selectedDirection) {
                                Direction.Increasing -> Res.drawable.arrow_upward_alt_24px
                                Direction.Decreasing -> Res.drawable.arrow_downward_alt_24px
                            }
                        ),
                        tint = MaterialTheme.colorScheme.inverseOnSurface,
                        contentDescription = null,
                    )
                }
                PhoneDirectoryListFilterPanel(
                    modifier = Modifier.weight(1f),
                    selectedSorter = selectedSorter,
                    onSorterChange = onSorterChange,
                )
            }
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
                if (filteredEmployeeItems.isEmpty() && allEmployeeItemsCount > 0) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingValues = PaddingValues(vertical = 12.dp, horizontal = 16.dp)),
                        text = stringResource(Res.string.empty_result),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                } else {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
                        state = gridState,
                        columns = GridCells.Fixed(count = 2),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                    ) {
                        items(items = filteredEmployeeItems) { employeeItem ->
                            EmployeeItemContent(
                                item = employeeItem,
                                onEmployeeItemClick = {
                                    onEmployeeItemClick(employeeItem)
                                },
                                onPinChange = onPinChange,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmployeeItemContent(
    item: EmployeeItemUIModel,
    onEmployeeItemClick: () -> Unit,
    onPinChange: (String, Boolean) -> Unit,
) {
    val sizeResolver = rememberConstraintsSizeResolver()

    Card(
        modifier = Modifier.fillMaxWidth()
            .height(260.dp)
            .clickable {
                onEmployeeItemClick()
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        shape = RoundedCornerShape(size = 12.dp),
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.inverseSurface)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                AsyncImage(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(100.dp),
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(item.imagePath)
                        .size(sizeResolver)
                        .build(),
                    placeholder = painterResource(Res.drawable.person_placeholder),
                    error = painterResource(Res.drawable.person_placeholder),
                    contentDescription = null,
                    filterQuality = FilterQuality.None,
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    text = item.fullName,
                    fontSize = 16.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Medium,
                    autoSize = TextAutoSize.StepBased(maxFontSize = 16.sp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.scrim,
                    maxLines = 2,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        text = item.department,
                        fontSize = 12.sp,
                        lineHeight = 14.sp,
                        fontWeight = FontWeight.Medium,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        text = item.service,
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.scrim,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        text = item.position,
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = if (item.workPlace.isNullOrBlank()) {
                            2
                        } else {
                            1
                        },
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                if (!item.workPlace.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            text = stringResource(Res.string.work_place),
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.scrim,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = item.workPlace ?: "",
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            if (item.isPinned) {
                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = {
                        onPinChange(item.fullName, false)
                    }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.kid_star_filled_24px),
                        tint = Color(red = 255, green = 207, blue = 64),
                        contentDescription = null,
                    )
                }
            } else {
                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = {
                        onPinChange(item.fullName, true)
                    }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.kid_star_24px),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Serializable
enum class Sorter(val stringResourceId: StringResource) {
    Name(stringResourceId = Res.string.fio),
    Department(stringResourceId = Res.string.department),
    Service(stringResourceId = Res.string.service),
    Position(stringResourceId = Res.string.position),
    Workplace(stringResourceId = Res.string.work_place_sorter),
}

@Serializable
enum class Direction {
    Increasing,
    Decreasing,
}
