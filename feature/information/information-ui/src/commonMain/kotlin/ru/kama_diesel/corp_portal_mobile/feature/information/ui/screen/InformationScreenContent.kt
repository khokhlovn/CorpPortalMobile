package ru.kama_diesel.corp_portal_mobile.feature.information.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.resources.*

val inlineContent = mapOf(
    Pair(
        "profile",
        InlineTextContent(
            Placeholder(
                width = 20.sp,
                height = 20.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
            )
        ) {
            Icon(
                painter = painterResource(Res.drawable.account_circle_24px),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }
    ),
    Pair(
        "balance",
        InlineTextContent(
            Placeholder(
                width = 20.sp,
                height = 20.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
            )
        ) {
            Icon(
                painter = painterResource(Res.drawable.universal_currency_alt_24px),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }
    ),
    Pair(
        "info",
        InlineTextContent(
            Placeholder(
                width = 20.sp,
                height = 20.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
            )
        ) {
            Icon(
                painter = painterResource(Res.drawable.info_i_24px),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }
    ),
    Pair(
        "shop",
        InlineTextContent(
            Placeholder(
                width = 20.sp,
                height = 20.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
            )
        ) {
            Icon(
                painter = painterResource(Res.drawable.storefront_24px),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }
    ),
    Pair(
        "cart",
        InlineTextContent(
            Placeholder(
                width = 20.sp,
                height = 20.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
            )
        ) {
            Icon(
                painter = painterResource(Res.drawable.shopping_cart_24px),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }
    ),
    Pair(
        "orders",
        InlineTextContent(
            Placeholder(
                width = 20.sp,
                height = 20.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
            )
        ) {
            Icon(
                painter = painterResource(Res.drawable.list_alt_24px),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }
    ),
    Pair(
        "back",
        InlineTextContent(
            Placeholder(
                width = 20.sp,
                height = 20.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
            )
        ) {
            Icon(
                painter = painterResource(Res.drawable.arrow_back_24px),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }
    ),
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun InformationScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(red = 243, green = 243, blue = 243))
            .padding(bottom = 12.dp)
    ) {
        val faqItems = listOf(
            FaqItem(
                title = "Как просмотреть свой баланс рахматиков",
                icon = painterResource(Res.drawable.icon_currency),
                text = buildAnnotatedString {
                    append("Перейти на страницу профиля ")
                    appendInlineContent("profile")
                    append(".\n\n")
                    append("Нажать на значение поля «Баланс» ")
                    appendInlineContent("balance")
                    append(".\n\nВ строке «Баланс» отображается общая сумма рахматиков.\n\nВ строке «Еженедельное начисление» отображается количество рахматиков, начисляемых раз в неделю со сроком сгорания – 1 неделя (7 к.д.). Для предотвращения их сгорания необходимо нажать кнопку «Перевести на баланс».\n\nВ строке «Доступно для передачи коллегам» отображается количество рахматиков, доступных для дарения со сроком сгорания – 1 неделя (7 к.д.).\n\nВ строке «Доступно для покупок» отображается количество рахматиков, за которые возможно оформить заказ в интернет-магазине.")
                }
            ),
            FaqItem(
                title = "Как просмотреть историю начисления и списания рахматиков",
                icon = painterResource(Res.drawable.icon_thx_history),
                text = buildAnnotatedString {
                    append("Перейти на страницу профиля ")
                    appendInlineContent("profile")
                    append(".\n\n")
                    append("Нажать на значение поля «Баланс» ")
                    appendInlineContent("balance")
                    append(".\n\nПод строкой «Доступно для покупок» отображается история начислений и списаний рахматиков.\n\nВсе события доступны для поиска и сортировки по дате и по сумме.\n\nПри получении или передаче в дар рахматиков отображается ФИО адресата и ФИО отправителя рахматиков.")
                }
            ),
            FaqItem(
                title = "Как подарить рахматики коллегам",
                icon = painterResource(Res.drawable.event_25),
                text = buildAnnotatedString {
                    append("Перейти на страницу профиля ")
                    appendInlineContent("profile")
                    append(".\n\n")
                    append("Нажать на значение поля «Баланс» ")
                    appendInlineContent("balance")
                    append(".\n\nВ строке «Доступно для передачи коллегам» нажать кнопку «Подарить».\n\nИз выпадающего списка выбрать или найти в поисковой строке необходимого адресата.\n\nВыбрать желаемое количество рахматиков.\n\nНажать кнопку «Подарить».\n\nДождаться появления сообщения «Рахматики успешно подарены».")
                }
            ),
            FaqItem(
                title = "Как узнать, за что будут начислены рахматики",
                icon = painterResource(Res.drawable.event_18),
                text = buildAnnotatedString {
                    append("Перейти на страницу профиля ")
                    appendInlineContent("profile")
                    append(".\n\n")
                    append("Нажать на значение поля «Баланс» ")
                    appendInlineContent("balance")
                    append(".\n\n")
                    append("Нажать на иконку «Информации» ")
                    appendInlineContent("info")
                    append("— отобразится перечень событий, за которые начисляются рахматики.")
                }
            ),
            FaqItem(
                title = "Как посмотреть каталог товаров и информацию о товаре",
                icon = painterResource(Res.drawable.storefront_24px),
                withTint = true,
                text = buildAnnotatedString {
                    append("Перейти на страницу магазина ")
                    appendInlineContent("shop")
                    append(".\n\nОтображается каталог товаров корпоративного интернет-магазина.\n\nНажать на изображение товара – отображаются подробная информация о товаре и характеристики.\n\nИз карточки товара можно добавить товар в корзину, нажав кнопку «Заказать».\n\nДля выхода из карточки товара в правом верхнем углу нажать символ закрытия модального окна Х.")
                }
            ),
            FaqItem(
                title = "Как оформить заказ в интернет-магазине",
                icon = painterResource(Res.drawable.shopping_cart_24px),
                withTint = true,
                text = buildAnnotatedString {
                    append("Перейти на страницу магазина ")
                    appendInlineContent("shop")
                    append(".\n\nВыбрать необходимый товар, нажать кнопку «Заказать».\n\nВыбрать необходимое количество товара символом «+» или убрать символом «-».\n\nНажать иконку «Корзины» ")
                    appendInlineContent("cart")
                    append(".\n\nНа странице «Корзина» нажать кнопку «Оформить заказ».\n\nДождаться появления сообщения «Заказ успешно оформлен».")
                }
            ),
            FaqItem(
                title = "Как просмотреть мои заказы",
                icon = painterResource(Res.drawable.list_alt_24px),
                withTint = true,
                text = buildAnnotatedString {
                    append("Перейти на страницу магазина ")
                    appendInlineContent("shop")
                    append(".\n\nНажать иконку «Заказы» ")
                    appendInlineContent("orders")
                    append(".\n\nДля возврата на страницу «Каталога» нажать иконку «Назад» ")
                    appendInlineContent("back")
                    append(".")
                }
            ),
        )

        var query by remember { mutableStateOf("") }
        val scope = rememberCoroutineScope()
        val focusManager = LocalFocusManager.current

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = query,
            maxLines = 1,
            placeholder = {
                Text(text = stringResource(Res.string.search))
            },
            onValueChange = {
                scope.launch {
                    query = it
                }
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.search_24px),
                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                    contentDescription = null,
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                query = ""
                            }
                            focusManager.clearFocus()
                        }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.close_24px),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            contentDescription = null,
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                },
            ),
            colors = TextFieldDefaults.colors().copy(
                focusedTextColor = MaterialTheme.colorScheme.scrim,
                unfocusedTextColor = MaterialTheme.colorScheme.scrim,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedContainerColor = MaterialTheme.colorScheme.inverseSurface,
                unfocusedContainerColor = MaterialTheme.colorScheme.inverseSurface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
        )
        Spacer(
            modifier = Modifier
                .height(4.dp)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.15f),
                            Color.Transparent,
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            faqItems.filter { it.title.contains(other = query, ignoreCase = true) }.forEachIndexed { index, faqItem ->
                FaqCard(
                    faqItem = faqItem,
                )
                if (index < faqItems.size - 1) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun FaqCard(faqItem: FaqItem) {
    var expandedState by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    expandedState = !expandedState
                }
            )
            .animateContentSize(
                animationSpec = TweenSpec(
                    durationMillis = 100,
                    easing = LinearOutSlowInEasing,
                )
            ),
        shape = RoundedCornerShape(size = 12.dp),
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.inverseSurface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = faqItem.icon,
                    contentDescription = null,
                    colorFilter = if (faqItem.withTint) {
                        ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
                    } else {
                        null
                    }
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = faqItem.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.scrim,
                )
                Icon(
                    modifier = Modifier.rotate(if (expandedState) 180f else 0f),
                    painter = painterResource(Res.drawable.arrow_drop_down_24px),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                )
            }
            if (expandedState) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = faqItem.text,
                    inlineContent = inlineContent,
                    fontSize = 12.sp,
                    style = TextStyle.Default.copy(
                        lineBreak = LineBreak.Paragraph,
                    ),
                    color = MaterialTheme.colorScheme.scrim,
                )
            }
        }
    }
}

data class FaqItem(
    val title: String,
    val icon: Painter,
    val withTint: Boolean = false,
    val text: AnnotatedString,
)
