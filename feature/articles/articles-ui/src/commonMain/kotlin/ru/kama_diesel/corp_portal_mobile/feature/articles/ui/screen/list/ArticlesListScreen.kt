package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
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
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        ArticlesListScreenContent(
            articleItems = viewState.articleItems,
            isRefreshing = viewState.isLoading,
            scrollEnabled = !expanded,
            onRefresh = onRefresh,
            onArticleClick = onArticleClick,
        )
        Row(modifier = Modifier.fillMaxHeight().align(alignment = Alignment.CenterEnd)) {
            if (!viewState.isLoading) {
                Button(
                    modifier = Modifier
                        .offset(x = 40.dp)
                        .rotate(-90f)
                        .focusable(true)
                        .align(alignment = Alignment.CenterVertically),
                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 20.dp),
                    onClick = {
                        expanded = !expanded
                    },
                ) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(Res.string.filters),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
                if (expanded) {
                    VerticalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
                }
                FiltersContent(
                    expanded = expanded,
                    tagItems = viewState.tagItems,
                    fromDate = viewState.fromDate,
                    toDate = viewState.toDate,
                    onCheckedChange = onCheckedChange,
                    onDateChange = onDateChange,
                    onResetFilters = onResetFilters,
                    onApplyFilters = onRefresh,
                    onHideFilters = { expanded = false },
                )
            }
        }

        when (val dialog = viewState.dialog) {
            is ArticlesListDialog.Details -> ArticleDetailsDialog(
                title = dialog.title,
                imagePaths = dialog.imagePaths,
                tags = dialog.tags,
                creationDate = dialog.creationDate,
                isLiked = dialog.isLiked,
                likesAmount = dialog.likesAmount,
                articleDetailsItem = dialog.articleDetailsItem,
                comment = dialog.comment,
                commentSendingState = dialog.commentSendingState,
                onCloseClick = onCloseDetailsClick,
                onCommentChange = onCommentChange,
                onSendComment = onSendComment,
                onHideSnackbar = onHideSnackbar,
                onLikeClick = onLikeClick,
            )

            ArticlesListDialog.Loading -> LoadingDialog()
            ArticlesListDialog.No -> Unit
        }
    }
}
