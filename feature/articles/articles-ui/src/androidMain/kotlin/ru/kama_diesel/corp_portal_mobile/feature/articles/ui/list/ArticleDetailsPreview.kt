package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.kama_diesel.corp_portal_mobile.common.domain.model.CommentItem
import ru.kama_diesel.corp_portal_mobile.common.ui.theme.AppTheme
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.dialog.details.ArticleDetailsDialog
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticleDetailsUIModel
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.CommentSendingState

@Preview
@Composable
private fun ArticleDetailsPreview() {
    AppTheme {
        ArticleDetailsDialog(
            title = "Делегация ОАО “БЕЛАЗ”",
            imagePaths = null,
            articleDetailsItem = ArticleDetailsUIModel(
                text = "АО «КАМА ДИЗЕЛЬ» в рамках делового визита на ПАО “КАМАЗ” посетила делегация ОАО «БЕЛАЗ». В числе гостей – начальник бюро общих компоновок управления главного конструктора Егор Мацуков и начальник бюро силовых установок Максим Борейко. Цель визита на автогигант белорусской делегации – поиск альтернативных поставщиков двигателей и различных узлов для карьерной и шахтной техники. Сегодня в Белоруссии растут объёмы производства таких машин.",
                originalComments = listOf(
                    CommentItem(
                        commentId = 0,
                        userId = 0,
                        replyTo = 0,
                        text = "Комментарий",
                        creationDate = "12.12.12 12:12",
                        fullName = "Иванов Иван Иванович",
                        position = "Старший мастер",
                        department = "Цех обработки блока цилиндров",
                        imagePath = null,
                        likesAmount = 0,
                        isLiked = false
                    )
                ),
                comments = mapOf(),
            ),
            tags = null,
            creationDate = "12.04.2024",
            isLiked = true,
            likesAmount = 1234,
            comment = "",
            replyTo = 0,
            myUserId = 0,
            commentSendingState = CommentSendingState.No,
            onCloseClick = {},
            onCommentChange = {},
            onSendComment = {},
            onHideSnackbar = {},
            onLikeClick = {},
            onChangeRepliesVisibility = {},
            onReplyClick = {},
            onCancelClick = {},
            onCommentLikeClick = {},
        )
    }
}
