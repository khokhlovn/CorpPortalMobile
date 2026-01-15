package ru.kama_diesel.corp_portal_mobile.feature.main.ui.screen.menu

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.resources.*

@Composable
internal fun MenuComponent(
    selectedIndex: Int,
    onArticlesClick: () -> Unit,
    onReservationClick: () -> Unit,
    onShopClick: () -> Unit,
    onTopClick: () -> Unit,
    onPhoneDirectoryClick: () -> Unit,
) {
    ModalDrawerSheet(
        modifier = Modifier.requiredWidth(200.dp),
        drawerShape = RectangleShape,
        drawerContainerColor = MaterialTheme.colorScheme.primary,
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(Res.string.menu),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

        NavigationDrawerItem(
            label = {
                Text(text = stringResource(Res.string.news))
            },
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.newspaper_24px),
                    contentDescription = null,
                )
            },
            shape = RectangleShape,
            selected = selectedIndex == 0,
            onClick = onArticlesClick,
        )

        NavigationDrawerItem(
            label = {
                Text(text = stringResource(Res.string.reservation))
            },
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.table_restaurant_24px),
                    contentDescription = null,
                )
            },
            shape = RectangleShape,
            selected = selectedIndex == 1,
            onClick = onReservationClick,
        )

        NavigationDrawerItem(
            label = {
                Text(text = stringResource(Res.string.shop))
            },
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.storefront_24px),
                    contentDescription = null,
                )
            },
            shape = RectangleShape,
            selected = selectedIndex == 2,
            onClick = onShopClick,
        )

        NavigationDrawerItem(
            label = {
                Text(text = stringResource(Res.string.top_workers))
            },
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.rewarded_ads_24px),
                    contentDescription = null,
                )
            },
            shape = RectangleShape,
            selected = selectedIndex == 3,
            onClick = onTopClick,
        )

        NavigationDrawerItem(
            label = {
                Text(text = stringResource(Res.string.phone_directory))
            },
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.call_log_24px),
                    contentDescription = null
                )
            },
            shape = RectangleShape,
            selected = selectedIndex == 4,
            onClick = onPhoneDirectoryClick,
        )
    }
}