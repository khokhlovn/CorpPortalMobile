package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.dialog.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import ru.kama_diesel.corp_portal_mobile.common.ui.component.FullScreenImageViewer
import ru.kama_diesel.corp_portal_mobile.resources.*

@Composable
internal fun ArticleDetailsContent(
    modifier: Modifier = Modifier,
    imagePaths: List<String>?,
    tags: List<String>?,
    creationDate: String,
    isLiked: Boolean,
    likesAmount: Int,
    articleDetailsItem: ArticleDetailsItem,
    onLikeClick: () -> Unit,
) {
    var selectedImageIndex by remember { mutableIntStateOf(0) }
    var isImageOpened by remember { mutableStateOf(false) }

    if (imagePaths != null && isImageOpened) {
        FullScreenImageViewer(
            selectedIndex = selectedImageIndex,
            imagePaths = imagePaths,
            onClose = { isImageOpened = false },
        )
    }

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .aspectRatio(4f / 3f)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .clickable {
                            selectedImageIndex = 0
                            isImageOpened = true
                        },
                    model = imagePaths.first(),
                    placeholder = painterResource(Res.drawable.placeholder),
                    error = painterResource(Res.drawable.placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }

            item {
                if (imagePaths.size > 1) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .height(48.dp)
                                .width(72.dp)
                                .clip(shape = RoundedCornerShape(12.dp))
                                .clickable {
                                    selectedImageIndex = 1
                                    isImageOpened = true
                                },
                            model = imagePaths[1],
                            placeholder = painterResource(Res.drawable.placeholder),
                            error = painterResource(Res.drawable.placeholder),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                        if (imagePaths.size > 2) {
                            Spacer(modifier = Modifier.width(8.dp))
                            AsyncImage(
                                modifier = Modifier
                                    .height(48.dp)
                                    .width(72.dp)
                                    .clip(shape = RoundedCornerShape(12.dp))
                                    .clickable {
                                        selectedImageIndex = 2
                                        isImageOpened = true
                                    },
                                model = imagePaths[2],
                                placeholder = painterResource(Res.drawable.placeholder),
                                error = painterResource(Res.drawable.placeholder),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                            )
                        }
                        if (imagePaths.size > 3) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .height(48.dp)
                                    .width(72.dp)
                                    .background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))
                                    .clip(shape = RoundedCornerShape(12.dp))
                                    .clickable {
                                        selectedImageIndex = 0
                                        isImageOpened = true
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 10.sp,
                                    maxLines = 1,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.inverseOnSurface,
                                    text = stringResource(Res.string.all_photos),
                                )
                            }
                        }
                    }
                }
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val interactionSource = remember { MutableInteractionSource() }
                if (isLiked) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        tint = MaterialTheme.colorScheme.error,
                        contentDescription = null,
                    )
                } else {
                    Icon(
                        modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = onLikeClick,
                            ),
                        imageVector = Icons.Outlined.FavoriteBorder,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        contentDescription = null,
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = likesAmount.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
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
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = commentItem.position,
                color = MaterialTheme.colorScheme.scrim,
                style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 14.sp,
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