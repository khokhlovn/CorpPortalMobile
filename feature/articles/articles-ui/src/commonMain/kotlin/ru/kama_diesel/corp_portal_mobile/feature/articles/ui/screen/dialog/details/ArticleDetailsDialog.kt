package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.dialog.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticleDetailsUIModel
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.CommentSendingState
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.close_24px
import ru.kama_diesel.corp_portal_mobile.resources.comment
import ru.kama_diesel.corp_portal_mobile.resources.comment_sent
import ru.kama_diesel.corp_portal_mobile.resources.reply_24px
import ru.kama_diesel.corp_portal_mobile.resources.send_24px

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsDialog(
    myUserId: Int,
    title: String,
    imagePaths: List<String>?,
    articleDetailsItem: ArticleDetailsUIModel,
    tags: List<String>?,
    creationDate: String,
    isLiked: Boolean,
    likesAmount: Int,
    comment: String,
    replyTo: Int?,
    commentSendingState: CommentSendingState,
    onCloseClick: () -> Unit,
    onChangeRepliesVisibility: (Int) -> Unit,
    onCommentChange: (String) -> Unit,
    onSendComment: () -> Unit,
    onHideSnackbar: () -> Unit,
    onLikeClick: () -> Unit,
    onCommentLikeClick: (String) -> Unit,
    onReplyClick: (Int) -> Unit,
    onCancelClick: () -> Unit,
) {
    Dialog(onDismissRequest = { onCloseClick() }) {
        Card(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.inverseSurface, shape = RoundedCornerShape(12.dp))
                .fillMaxSize(),
        ) {
            val snackbarHostState = remember { SnackbarHostState() }
            val snackbarText = stringResource(Res.string.comment_sent)
            val isSnackBarShowing by mutableStateOf(commentSendingState == CommentSendingState.Success)
            if (isSnackBarShowing) {
                LaunchedEffect(isSnackBarShowing) {
                    snackbarHostState.showSnackbar(snackbarText)
                    onHideSnackbar()
                }
            }

            Scaffold(
                topBar = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        Text(
                            modifier = Modifier.weight(1f).padding(all = 12.dp),
                            text = title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.scrim,
                        )
                        IconButton(
                            onClick = onCloseClick,
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.close_24px),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                contentDescription = null,
                            )
                        }
                    }
                },
                bottomBar = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (replyTo != null) {
                            val originalComment = articleDetailsItem.originalComments.find { it.commentId == replyTo }
                            ReplyToBar(
                                name = originalComment?.fullName ?: "",
                                text = originalComment?.text ?: "",
                                onCancelClick = onCancelClick,
                            )
                        }
                        CommentTextField(
                            comment = comment,
                            commentSendingState = commentSendingState,
                            onCommentChange = onCommentChange,
                            onSendComment = onSendComment,
                        )
                    }
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                },
                containerColor = MaterialTheme.colorScheme.inverseSurface,
            ) { paddingValues ->
                ArticleDetailsContent(
                    modifier = Modifier.padding(paddingValues = paddingValues),
                    myUserId = myUserId,
                    imagePaths = imagePaths,
                    articleDetailsItem = articleDetailsItem,
                    tags = tags,
                    isLiked = isLiked,
                    likesAmount = likesAmount,
                    creationDate = creationDate,
                    onLikeClick = onLikeClick,
                    onChangeRepliesVisibility = onChangeRepliesVisibility,
                    onReplyClick = {
                        onReplyClick(it)
                    },
                    onCommentLikeClick = onCommentLikeClick,
                )
            }
        }
    }
}

@Composable
private fun ReplyToBar(
    name: String,
    text: String,
    onCancelClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(Res.drawable.reply_24px),
            tint = MaterialTheme.colorScheme.inverseOnSurface,
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                text = name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                lineHeight = 10.sp,
                color = MaterialTheme.colorScheme.scrim,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp,
                lineHeight = 12.sp,
                color = MaterialTheme.colorScheme.scrim,
            )
        }
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = onCancelClick,
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(Res.drawable.close_24px),
                tint = MaterialTheme.colorScheme.outline,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun CommentTextField(
    comment: String,
    commentSendingState: CommentSendingState,
    onCommentChange: (String) -> Unit,
    onSendComment: () -> Unit,
) {
    val maxChar = 3000
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = comment,
        onValueChange = { if (it.length <= maxChar) onCommentChange(it) },
        textStyle = TextStyle(fontSize = 14.sp),
        maxLines = 5,
        colors = OutlinedTextFieldDefaults.colors().copy(
            focusedTextColor = MaterialTheme.colorScheme.scrim,
            unfocusedTextColor = MaterialTheme.colorScheme.scrim,
        ),
        label = { Text(text = stringResource(Res.string.comment)) },
        supportingText = {
            Text(
                text = "${comment.length} / $maxChar",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.End,
            )
        },
        trailingIcon = {
            if (commentSendingState is CommentSendingState.Sending) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                )
            } else {
                IconButton(
                    onClick = {
                        focusManager.clearFocus()
                        onSendComment()
                    },
                    enabled = comment.isNotBlank(),
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.send_24px),
                        tint = if (comment.isNotBlank()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        contentDescription = null,
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
        ),
    )
}
