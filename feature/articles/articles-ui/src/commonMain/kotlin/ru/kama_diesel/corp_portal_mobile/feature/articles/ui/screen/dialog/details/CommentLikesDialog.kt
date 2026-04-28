package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.dialog.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.CommentLikeUIModel
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.close_24px
import ru.kama_diesel.corp_portal_mobile.resources.liked

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentLikesDialog(
    commentLikeUIModels: List<CommentLikeUIModel>,
    onCloseClick: () -> Unit,
) {
    Dialog(onDismissRequest = { onCloseClick() }) {
        Card(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.inverseSurface, shape = RoundedCornerShape(12.dp))
                .fillMaxWidth(),
        ) {
            CommentLikesContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.inverseSurface),
                commentLikeUIModels = commentLikeUIModels,
                onCloseClick = onCloseClick,
            )
        }
    }
}
