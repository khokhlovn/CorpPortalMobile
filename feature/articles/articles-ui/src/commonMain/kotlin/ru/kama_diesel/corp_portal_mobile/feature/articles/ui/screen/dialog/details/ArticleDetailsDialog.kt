package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.dialog.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleDetailsItem
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.CommentSendingState
import ru.kama_diesel.corp_portal_mobile.resources.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsDialog(
    title: String,
    imagePaths: List<String>?,
    articleDetailsItem: ArticleDetailsItem,
    tags: List<String>?,
    creationDate: String,
    isLiked: Boolean,
    likesAmount: Int,
    comment: String,
    commentSendingState: CommentSendingState,
    onCloseClick: () -> Unit,
    onCommentChange: (String) -> Unit,
    onSendComment: () -> Unit,
    onHideSnackbar: () -> Unit,
    onLikeClick: () -> Unit,
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
                    CommentTextField(
                        comment = comment,
                        commentSendingState = commentSendingState,
                        onCommentChange = onCommentChange,
                        onSendComment = onSendComment,
                    )
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                },
                containerColor = MaterialTheme.colorScheme.inverseSurface,
            ) { paddingValues ->
                ArticleDetailsContent(
                    modifier = Modifier.padding(paddingValues = paddingValues),
                    imagePaths = imagePaths,
                    articleDetailsItem = articleDetailsItem,
                    tags = tags,
                    isLiked = isLiked,
                    likesAmount = likesAmount,
                    creationDate = creationDate,
                    onLikeClick = onLikeClick,
                )
            }
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
        modifier = Modifier.fillMaxWidth().padding(all = 12.dp),
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
            keyboardType = KeyboardType.Password,
        ),
    )
}
