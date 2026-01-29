package ru.kama_diesel.corp_portal_mobile.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.crossfade.CrossfadePlugin
import com.skydoves.landscapist.zoomable.ZoomablePlugin
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import ru.kama_diesel.corp_portal_mobile.resources.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenImageViewer(
    selectedIndex: Int,
    imagePaths: List<String>,
    onClose: () -> Unit = {},
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val pagerState = rememberPagerState(
        initialPage = selectedIndex,
        pageCount = { imagePaths.size },
    )

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        dragHandle = null,
        shape = RectangleShape,
        onDismissRequest = onClose,
        scrimColor = MaterialTheme.colorScheme.scrim,
        contentColor = MaterialTheme.colorScheme.scrim,
        containerColor = Color.Transparent,
    ) {
        HorizontalPager(
            state = pagerState,
        ) { index ->
            CoilImage(
                modifier = Modifier
                    .fillMaxSize(),
                imageModel = { imagePaths[index] },
                component = rememberImageComponent {
                    +CrossfadePlugin(
                        duration = 550
                    )
                    +ZoomablePlugin()
                },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Fit,
                ),
                loading = {
                    Box(modifier = Modifier.matchParentSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                failure = {
                    painterResource(resource = Res.drawable.placeholder)
                }
            )
        }
        }
}