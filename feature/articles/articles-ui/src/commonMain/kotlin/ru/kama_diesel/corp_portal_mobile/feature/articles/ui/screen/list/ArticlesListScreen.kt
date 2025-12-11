package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.ahmad_hamwi.compose.pagination.PaginationState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem
import ru.kama_diesel.corp_portal_mobile.common.ui.component.LoadingDialog
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.dialog.details.ArticleDetailsDialog
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticlesListDialog
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticlesListViewState
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.filters

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesListScreen(
    viewState: ArticlesListViewState,
    paginationState: PaginationState<Int, ArticleItem>,
    onCheckedChange: (String, Boolean) -> Unit,
    onDateChange: (Long?, Long?) -> Unit,
    onRefresh: () -> Unit,
    onArticleClick: (String, String, List<String>?, List<String>?, String, Boolean, Int) -> Unit,
    onResetFilters: () -> Unit,
    onCloseDetailsClick: () -> Unit,
    onCommentChange: (String) -> Unit,
    onSendComment: () -> Unit,
    onHideSnackbar: () -> Unit,
    onLikeClick: () -> Unit,
    onChangeRepliesVisibility: (Int) -> Unit,
    onReplyClick: (Int) -> Unit,
    onCancelReplyClick: () -> Unit,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                gesturesEnabled = !viewState.isLoading,
                drawerContent = {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .requiredWidth(260.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            if (!viewState.isLoading) {
                                Button(
                                    modifier = Modifier.rotate(-90f).offset(y = (-75).dp),
                                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
                                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 20.dp),
                                    onClick = {
                                        scope.launch {
                                            if (drawerState.isOpen) {
                                                drawerState.close()
                                            } else {
                                                drawerState.open()
                                            }
                                        }
                                    },
                                ) {
                                    Text(
                                        text = stringResource(Res.string.filters),
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                    )
                                }
                                FiltersContent(
                                    modifier = Modifier.fillMaxHeight().offset(x = (-56).dp),
                                    tagItems = viewState.tagItems,
                                    fromDate = viewState.fromDate,
                                    toDate = viewState.toDate,
                                    onCheckedChange = onCheckedChange,
                                    onDateChange = onDateChange,
                                    onResetFilters = onResetFilters,
                                    onApplyFilters = onRefresh,
                                    onHideFilters = {
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                )
                            }
                        }
                    }
                },
                content = {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            ArticlesListScreenContent(
                                paginationState = paginationState,
                                isRefreshing = viewState.isLoading,
                                onArticleClick = onArticleClick,
                            )
                        }
                    }
                },
            )
        }

        when (val dialog = viewState.dialog) {
            is ArticlesListDialog.Details -> ArticleDetailsDialog(
                myUserId = dialog.myUserId,
                title = dialog.title,
                imagePaths = dialog.imagePaths,
                tags = dialog.tags,
                creationDate = dialog.creationDate,
                isLiked = dialog.isLiked,
                likesAmount = dialog.likesAmount,
                articleDetailsItem = dialog.articleDetailsItem,
                comment = dialog.comment,
                replyTo = dialog.replyTo,
                commentSendingState = dialog.commentSendingState,
                onCloseClick = onCloseDetailsClick,
                onCommentChange = onCommentChange,
                onSendComment = onSendComment,
                onHideSnackbar = onHideSnackbar,
                onLikeClick = onLikeClick,
                onChangeRepliesVisibility = onChangeRepliesVisibility,
                onReplyClick = onReplyClick,
                onCancelClick = onCancelReplyClick,
            )

            ArticlesListDialog.Loading -> LoadingDialog()
            ArticlesListDialog.No -> Unit
        }
    }
}
