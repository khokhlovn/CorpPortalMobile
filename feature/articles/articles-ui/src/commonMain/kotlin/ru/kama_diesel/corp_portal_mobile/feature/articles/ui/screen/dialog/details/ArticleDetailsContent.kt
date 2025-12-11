package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.dialog.details

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
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
import ru.kama_diesel.corp_portal_mobile.common.domain.model.CommentItem
import ru.kama_diesel.corp_portal_mobile.common.ui.component.FullScreenImageViewer
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticleDetailsUIModel
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.CommentUIModel
import ru.kama_diesel.corp_portal_mobile.resources.*

@Composable
internal fun ArticleDetailsContent(
    modifier: Modifier = Modifier,
    imagePaths: List<String>?,
    tags: List<String>?,
    creationDate: String,
    isLiked: Boolean,
    likesAmount: Int,
    myUserId: Int,
    articleDetailsItem: ArticleDetailsUIModel,
    onLikeClick: () -> Unit,
    onChangeRepliesVisibility: (Int) -> Unit,
    onReplyClick: (Int) -> Unit,
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
                        painter = painterResource(Res.drawable.favorite_filled_24px),
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
                        painter = painterResource(Res.drawable.favorite_24px),
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
                text = if (articleDetailsItem.comments.isEmpty()) {
                    stringResource(Res.string.comments_empty)
                } else {
                    stringResource(Res.string.comments, articleDetailsItem.comments.size)
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.scrim,
            )
        }

        articleDetailsItem.comments.forEach { commentWithReplies ->
            item {
                CommentListItem(
                    commentUIModel = commentWithReplies.key,
                    myUserId = myUserId,
                    hasReplies = commentWithReplies.value.isNotEmpty(),
                    onChangeRepliesVisibility = onChangeRepliesVisibility,
                    onReplyClick = onReplyClick,
                )
            }
            if (commentWithReplies.key.isExpanded) {
                item {
                    val localDensity = LocalDensity.current

                    var columnHeightDp by remember {
                        mutableStateOf(0.dp)
                    }
                    var lastItemHeightDp by remember {
                        mutableStateOf(0.dp)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Canvas(
                            modifier = Modifier
                                .height(columnHeightDp - lastItemHeightDp)
                                .padding(start = 56.dp),
                        ) {
                            val canvasHeight = size.height

                            drawLine(
                                start = Offset(x = 0f, y = 0f),
                                end = Offset(x = 0f, y = canvasHeight),
                                color = Color.DarkGray,
                                strokeWidth = 1f,
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    columnHeightDp = with(localDensity) { coordinates.size.height.toDp() }
                                },
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            commentWithReplies.value.forEachIndexed { index, comment ->
                                SubcommentListItem(
                                    modifier = Modifier.then(
                                        if (index == commentWithReplies.value.size - 1) {
                                            Modifier.onGloballyPositioned { coordinates ->
                                                lastItemHeightDp = with(localDensity) { coordinates.size.height.toDp() }
                                            }
                                        } else {
                                            Modifier
                                        }
                                    ),
                                    commentItem = comment,
                                    myUserId = myUserId,
                                    onReplyClick = onReplyClick,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CommentListItem(
    commentUIModel: CommentUIModel,
    myUserId: Int,
    hasReplies: Boolean,
    onChangeRepliesVisibility: (Int) -> Unit,
    onReplyClick: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        AsyncImage(
            modifier = Modifier.clip(shape = CircleShape).size(48.dp).background(Color.Black),
            model = commentUIModel.imagePath,
            placeholder = painterResource(Res.drawable.person_placeholder),
            error = painterResource(Res.drawable.person_placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.fillMaxHeight().weight(1f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = commentUIModel.fullName,
                color = MaterialTheme.colorScheme.scrim,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 16.sp,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = commentUIModel.position,
                color = MaterialTheme.colorScheme.primary,
                style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 14.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = commentUIModel.text,
                color = MaterialTheme.colorScheme.scrim,
                style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                lineHeight = 14.sp,
                fontSize = 12.sp,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                val interactionSource = remember { MutableInteractionSource() }
                if (commentUIModel.userId != myUserId) {
                    Text(
                        modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                            ) {
                                onReplyClick(commentUIModel.commentId)
                            },
                        text = stringResource(Res.string.reply),
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        fontSize = 12.sp,
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
                Text(
                    modifier = if (commentUIModel.userId == myUserId) {
                        Modifier.fillMaxWidth()
                    } else {
                        Modifier
                    },
                    text = commentUIModel.creationDate,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = TextAlign.End,
                    fontSize = 12.sp,
                )
            }
            if (hasReplies) {
                Spacer(modifier = Modifier.width(4.dp))
                val interactionSource = remember { MutableInteractionSource() }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                        ) {
                            onChangeRepliesVisibility(commentUIModel.commentId)
                        },
                    text = stringResource(
                        if (commentUIModel.isExpanded) {
                            Res.string.hide_replies
                        } else {
                            Res.string.show_replies
                        }

                    ),
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Composable
private fun SubcommentListItem(
    modifier: Modifier = Modifier,
    commentItem: CommentItem,
    myUserId: Int,
    onReplyClick: (Int) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Canvas(
            modifier = Modifier.width(28.dp),
        ) {
            val path = Path().apply {
                moveTo(0f, 0f)

                cubicTo(
                    x1 = 0f,
                    y1 = 56f,
                    x2 = 2f,
                    y2 = 56f,
                    x3 = 48f,
                    y3 = 56f,
                )
            }

            drawPath(
                path = path,
                color = Color.Gray,
                style = Stroke(width = 2f)
            )
        }
        AsyncImage(
            modifier = Modifier.clip(shape = CircleShape).size(40.dp).background(Color.Black),
            model = commentItem.imagePath,
            placeholder = painterResource(Res.drawable.person_placeholder),
            error = painterResource(Res.drawable.person_placeholder),
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
                fontSize = 12.sp,
                lineHeight = 14.sp,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = commentItem.position,
                color = MaterialTheme.colorScheme.primary,
                style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                lineHeight = 12.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = commentItem.text,
                color = MaterialTheme.colorScheme.scrim,
                style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                lineHeight = 14.sp,
                fontSize = 12.sp,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                val interactionSource = remember { MutableInteractionSource() }
                if (commentItem.userId != myUserId) {
                    Text(
                        modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                            ) {
                                onReplyClick(commentItem.commentId)
                            },
                        text = stringResource(Res.string.reply),
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        fontSize = 10.sp,
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
                Text(
                    modifier = if (commentItem.userId == myUserId) {
                        Modifier.fillMaxWidth()
                    } else {
                        Modifier
                    },
                    text = commentItem.creationDate,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = TextAlign.End,
                    fontSize = 10.sp,
                )
            }
        }
    }
}
