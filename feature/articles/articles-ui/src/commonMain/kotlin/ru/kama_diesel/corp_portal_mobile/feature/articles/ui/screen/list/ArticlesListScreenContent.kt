package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem

@Composable
fun ArticlesListScreenContent(
    articleItems: List<ArticleItem>,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 20.dp)
    ) {
        items(items = articleItems) { articleItem ->
            ArticleItemContent(
                item = articleItem,
            )
        }
    }
}

@Composable
fun ArticleItemContent(
    item: ArticleItem,
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(160.dp)
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .clickable { },
        contentAlignment = Alignment.BottomStart,
    ) {
        AsyncImage(
            model = item.imagePath,
            contentDescription = null,
            contentScale = ContentScale.Crop,
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
