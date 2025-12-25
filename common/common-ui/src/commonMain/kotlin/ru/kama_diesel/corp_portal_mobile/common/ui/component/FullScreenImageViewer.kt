package ru.kama_diesel.corp_portal_mobile.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import ru.kama_diesel.corp_portal_mobile.resources.*

@Composable
fun FullScreenImageViewer(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    imagePaths: List<String>,
    onClose: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = selectedIndex,
        pageCount = { imagePaths.size },
    )
    val scale = remember { mutableFloatStateOf(1f) }
    val offset = remember { mutableStateOf(Offset.Zero) }
    var possibleOffsetX by remember { mutableFloatStateOf(0f) }
    var possibleOffsetY by remember { mutableFloatStateOf(0f) }
    var boxSize by remember { mutableStateOf(IntSize(0, 0)) }
    val maxScale = 5f
    val minScale = 1f

    val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale.floatValue = (scale.floatValue * zoomChange).coerceIn(minScale, maxScale)
        offset.value += offsetChange
        possibleOffsetX = (boxSize.width * scale.floatValue - boxSize.width) / 2
        possibleOffsetY = (boxSize.height * scale.floatValue - boxSize.height) / 2
    }

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onClose,
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .zIndex(10f)
                .background(Color.DarkGray.copy(alpha = 0.5f))
                .onGloballyPositioned { coordinates ->
                    boxSize = coordinates.size
                },
            contentAlignment = Alignment.Center,
        ) {
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .zIndex(7f)
                    .height(40.dp)
                    .width(40.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = (-5).dp, y = 5.dp)
                    .clip(CircleShape),
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(Res.drawable.close_24px),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null,
                )
            }

            HorizontalPager(
                state = pagerState,
            ) { index ->
                CoilImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .transformable(state = transformState)
                        .graphicsLayer(
                            scaleX = scale.floatValue,
                            scaleY = scale.floatValue,
                            translationX =
                                if (offset.value.x > possibleOffsetX) {
                                    possibleOffsetX
                                } else if (offset.value.x < -(possibleOffsetX)) {
                                    -possibleOffsetX
                                } else {
                                    offset.value.x
                                },
                            translationY =
                                if (offset.value.y > possibleOffsetY) {
                                    possibleOffsetY
                                } else if (offset.value.y < -(possibleOffsetY)) {
                                    -possibleOffsetY
                                } else {
                                    offset.value.y
                                }
                        ),
                    imageModel = { imagePaths[index] },
                    component = rememberImageComponent {
                        +CrossfadePlugin(
                            duration = 550
                        )
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

            if (pagerState.currentPage > 0) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .zIndex(7f),
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        modifier = Modifier
                            .height(48.dp)
                            .width(48.dp)
                            .background(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                            .clip(CircleShape),
                        onClick = {
                            scope.launch {
                                offset.value = Offset.Zero
                                scale.value = 1f
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                    ) {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(Res.drawable.arrow_left_24px),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = null,
                        )
                    }
                }
            }

            if (pagerState.currentPage < pagerState.pageCount - 1) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .zIndex(7f),
                ) {
                    IconButton(
                        modifier = Modifier
                            .height(48.dp)
                            .width(48.dp)
                            .background(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                            .clip(CircleShape),
                        onClick = {
                            scope.launch {
                                offset.value = Offset.Zero
                                scale.value = 1f
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                    ) {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(Res.drawable.arrow_right_24px),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = null,
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}