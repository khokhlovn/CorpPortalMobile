package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleDetailsItem
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.dialog.details.ArticleDetailsDialog
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.CommentSendingState

@Preview(showBackground = true)
@Composable
private fun ArticleDetailsPreview() {
    ArticleDetailsDialog(
        title = "Делегация ОАО “БЕЛАЗ”",
        imagePaths = null,
        articleDetailsItem = ArticleDetailsItem(
            text = "АО «КАМА ДИЗЕЛЬ» в рамках делового визита на ПАО “КАМАЗ” посетила делегация ОАО «БЕЛАЗ». В числе гостей – начальник бюро общих компоновок управления главного конструктора Егор Мацуков и начальник бюро силовых установок Максим Борейко. Цель визита на автогигант белорусской делегации – поиск альтернативных поставщиков двигателей и различных узлов для карьерной и шахтной техники. Сегодня в Белоруссии растут объёмы производства таких машин.",
            comments = null,
        ),
        tags = null,
        creationDate = "12.04.2024",
        isLiked = true,
        likesAmount = 1234,
        comment = "",
        commentSendingState = CommentSendingState.No,
        onCloseClick = {},
        onCommentChange = {},
        onSendComment = {},
        onHideSnackbar = {},
        onLikeClick = {},
    )
}
