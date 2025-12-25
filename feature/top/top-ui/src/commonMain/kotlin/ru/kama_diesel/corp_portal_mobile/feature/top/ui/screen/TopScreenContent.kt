package ru.kama_diesel.corp_portal_mobile.feature.top.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TopWorkerItem
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.more
import ru.kama_diesel.corp_portal_mobile.resources.person_placeholder

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TopScreenContent(
    topWorkers: List<TopWorkerItem>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
) {
    val uriHandler = LocalUriHandler.current
    val state = rememberPullToRefreshState()
    val percentCacheWindow = LazyLayoutCacheWindow(
        aheadFraction = 1f,
        behindFraction = 0.5f,
    )
    val gridState = rememberLazyGridState(cacheWindow = percentCacheWindow)

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
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                state = gridState,
                columns = GridCells.Fixed(count = 2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
            ) {
                items(items = topWorkers) { topWorker ->
                    TopWorkerItemContent(
                        item = topWorker,
                        onLinkClick = {
                            uriHandler.openUri(topWorker.link)
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun TopWorkerItemContent(
    item: TopWorkerItem,
    onLinkClick: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .height(280.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        shape = RoundedCornerShape(size = 12.dp),
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.inverseSurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            if (item.imagePath.isNullOrEmpty()) {
                Image(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(100.dp),
                    painter = painterResource(Res.drawable.person_placeholder),
                    contentDescription = null,
                )
            } else {
                CoilImage(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(100.dp),
                    imageModel = { item.imagePath },
                    imageOptions = ImageOptions(
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.Crop,
                        requestSize = IntSize(600, 600),
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
            }
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
                        .padding(horizontal = 8.dp)
                        .weight(1f),
                    text = item.position,
                    fontSize = 12.sp,
                    lineHeight = 14.sp,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .clickable {
                            onLinkClick()
                        },
                    text = stringResource(Res.string.more),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
            }
        }
    }
}
