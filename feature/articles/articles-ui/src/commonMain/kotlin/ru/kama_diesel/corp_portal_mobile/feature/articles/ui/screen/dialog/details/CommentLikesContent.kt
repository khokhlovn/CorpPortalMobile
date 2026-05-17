package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.dialog.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.CommentLikeUIModel
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.close_24px
import ru.kama_diesel.corp_portal_mobile.resources.liked
import ru.kama_diesel.corp_portal_mobile.resources.person_placeholder

@Composable
internal fun CommentLikesContent(
    modifier: Modifier = Modifier,
    commentLikeUIModels: List<CommentLikeUIModel>,
    onCloseClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.inverseSurface),
        horizontalArrangement = Arrangement.End,
    ) {
        Text(
            modifier = Modifier.weight(1f).padding(start = 12.dp, end = 12.dp, top = 12.dp),
            text = stringResource(Res.string.liked),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
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

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(items = commentLikeUIModels) {
            LikeListItem(
                fullName = it.fullName,
                position = it.position,
                imagePath = it.imagePath,
            )
        }
    }
}

@Composable
private fun LikeListItem(
    fullName: String,
    position: String,
    imagePath: String?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (imagePath.isNullOrEmpty()) {
            Image(
                modifier = Modifier.clip(shape = CircleShape).size(48.dp),
                painter = painterResource(Res.drawable.person_placeholder),
                contentDescription = null,
            )
        } else {
            CoilImage(
                modifier = Modifier.clip(shape = CircleShape).size(48.dp),
                imageModel = { imagePath },
                imageOptions = ImageOptions(
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.Crop,
                    requestSize = IntSize(300, 300),
                ),
                loading = {
                    Box(modifier = Modifier.matchParentSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                failure = {
                    painterResource(resource = Res.drawable.person_placeholder)
                },
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.fillMaxHeight().weight(1f),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = fullName,
                color = MaterialTheme.colorScheme.scrim,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 16.sp,
            )
            if (position.isNotBlank()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = position,
                    color = MaterialTheme.colorScheme.primary,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    lineHeight = 14.sp,
                )
            }
        }
    }
}
