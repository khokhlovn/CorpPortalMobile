package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.dialog.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleDetailsItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.CommentItem
import ru.kama_diesel.corp_portal_mobile.resources.*

@Composable
internal fun ArticleDetailsContent(
    modifier: Modifier = Modifier,
    imagePaths: List<String>?,
    tags: List<String>?,
    creationDate: String,
    articleDetailsItem: ArticleDetailsItem,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .background(color = MaterialTheme.colorScheme.inverseSurface),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        imagePaths?.let {
            item {
                AsyncImage(
                    modifier = Modifier.clip(shape = RoundedCornerShape(12.dp)),
                    model = imagePaths.first(),
                    placeholder = painterResource(Res.drawable.placeholder),
                    error = painterResource(Res.drawable.placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = articleDetailsItem.text,
                fontSize = 16.sp,
                letterSpacing = TextUnit(0.01F, TextUnitType.Sp),
                color = MaterialTheme.colorScheme.scrim,
                style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
            )
        }
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = creationDate,
                textAlign = TextAlign.End,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.outline,
            )
        }

        tags?.let {
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.article_tags, tags.joinToString(", ")),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.outline,
                )
            }
        }

        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = if (articleDetailsItem.comments.isNullOrEmpty()) {
                    stringResource(Res.string.comments_empty)
                } else {
                    stringResource(Res.string.comments, articleDetailsItem.comments!!.size)
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.scrim,
            )
        }

        articleDetailsItem.comments?.let { comments ->
            if (comments.isNotEmpty()) {
                items(comments) { comment ->
                    CommentListItem(commentItem = comment)
                }
            }
        }
    }
}

@Composable
private fun CommentListItem(commentItem: CommentItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        AsyncImage(
            modifier = Modifier.clip(shape = CircleShape).size(48.dp).background(Color.Black),
            model = commentItem.imagePath,
            placeholder = painterResource(Res.drawable.placeholder),
            error = painterResource(Res.drawable.placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.fillMaxHeight().weight(1f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = commentItem.fullName,
                color = MaterialTheme.colorScheme.scrim,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 16.sp,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = commentItem.position,
                color = MaterialTheme.colorScheme.scrim,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = commentItem.text,
                color = MaterialTheme.colorScheme.primary,
                style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                fontWeight = FontWeight.Medium,
                lineHeight = 14.sp,
                fontSize = 12.sp,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = commentItem.creationDate,
                color = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
            )
        }
    }
}