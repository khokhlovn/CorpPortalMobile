package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.github.ahmad_hamwi.compose.pagination.PaginatedLazyColumn
import io.github.ahmad_hamwi.compose.pagination.PaginationState
import org.jetbrains.compose.resources.painterResource
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.placeholder

@Composable
fun ArticlesListScreenContent(
    paginationState: PaginationState<Int, ArticleItem>,
    isRefreshing: Boolean,
    onArticleClick: (String, String, List<String>?, List<String>?, String, Boolean, Int) -> Unit,
) {
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
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
        onRefresh = { paginationState.refresh(initialPageKey = 1) },
    ) {
        PaginatedLazyColumn(
            modifier = Modifier.fillMaxSize(),
            paginationState = paginationState,
            newPageProgressIndicator = {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    trackColor = MaterialTheme.colorScheme.inverseSurface,
                )
            },
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 20.dp)
        ) {
            items(items = paginationState.allItems!!) { articleItem ->
                ArticleItemContent(
                    item = articleItem,
                    onArticleClick = onArticleClick,
                )
            }
        }
    }
}

@Composable
fun ArticleItemContent(
    item: ArticleItem,
    onArticleClick: (String, String, List<String>?, List<String>?, String, Boolean, Int) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(160.dp)
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .clickable {
                onArticleClick(
                    item.id,
                    item.title,
                    item.imagePaths,
                    item.tags,
                    item.creationDate,
                    item.isLiked,
                    item.likesAmount
                )
            },
        contentAlignment = Alignment.BottomStart,
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = item.imagePaths?.firstOrNull(),
            placeholder = painterResource(Res.drawable.placeholder),
            error = painterResource(Res.drawable.placeholder),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f)
                        ),
                    ),
                )
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = item.title,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.text,
                fontSize = 12.sp,
                lineHeight = 1.5.em,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
