package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.ui.component.FullScreenImageViewer
import ru.kama_diesel.corp_portal_mobile.common.ui.component.PagerIndicator
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopItemUIModel
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.characteristics
import ru.kama_diesel.corp_portal_mobile.resources.description
import ru.kama_diesel.corp_portal_mobile.resources.placeholder

@Composable
internal fun ShopItemDetailsContent(
    modifier: Modifier = Modifier,
    shopItem: ShopItemUIModel,
) {
    var selectedImageIndex by remember { mutableIntStateOf(0) }
    var isImageOpened by remember { mutableStateOf(false) }

    val imagePaths = remember { shopItem.imagePaths }
    if (imagePaths != null && isImageOpened) {
        FullScreenImageViewer(
            selectedIndex = selectedImageIndex,
            imagePaths = imagePaths,
            onClose = { isImageOpened = false },
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {
        val pagerState = rememberPagerState(pageCount = { shopItem.imagePaths?.size ?: 1 })
        val interactionSource = remember { MutableInteractionSource() }

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth(),
            state = pagerState,
        ) { page ->
            AsyncImage(
                modifier = Modifier
                    .height(240.dp)
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            selectedImageIndex = 0
                            isImageOpened = true
                        }
                    ),
                model = shopItem.imagePaths?.get(page),
                placeholder = painterResource(Res.drawable.placeholder),
                error = painterResource(Res.drawable.placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }

        if (!shopItem.imagePaths.isNullOrEmpty() && shopItem.imagePaths!!.size > 1) {
            Spacer(modifier = Modifier.height(12.dp))
            PagerIndicator(shopItem.imagePaths.size, pagerState.currentPage)
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.inverseSurface),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = stringResource(Res.string.description),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.scrim,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = shopItem.description,
                    fontSize = 14.sp,
                    //letterSpacing = TextUnit(0.01F, TextUnitType.Sp),
                    color = MaterialTheme.colorScheme.scrim,
                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                )
            }

            if (shopItem.characteristics.isNotEmpty()) {
                item {
                    Text(
                        text = stringResource(Res.string.characteristics),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.scrim,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        shopItem.characteristics.forEach {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Text(
                                    modifier = Modifier.widthIn(min = 120.dp),
                                    text = it.key + ": ",
                                    fontSize = 14.sp,
                                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                                    color = MaterialTheme.colorScheme.outline,
                                )
                                Text(
                                    text = it.value,
                                    fontSize = 14.sp,
                                    style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                                    color = MaterialTheme.colorScheme.outline,
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = shopItem.price.toString(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }
}
