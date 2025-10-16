package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem

@Composable
fun ArticlesListScreenContent(
    articleItems: List<ArticleItem>,
) {
    LazyColumn {
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
            .clickable { }
            .padding(all = 16.dp),
        contentAlignment = Alignment.BottomStart,
    ) {
        Column {
            Text(
                text = item.title,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.compressedText,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
            )
        }
        AsyncImage(
            model = item.imagePath,
            contentDescription = null,
        )
    }
}
