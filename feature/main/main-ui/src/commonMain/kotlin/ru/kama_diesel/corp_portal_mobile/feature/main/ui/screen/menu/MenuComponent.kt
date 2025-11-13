package ru.kama_diesel.corp_portal_mobile.feature.main.ui.screen.menu

import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.news
import ru.kama_diesel.corp_portal_mobile.resources.shop

@Composable
internal fun MenuComponent(
    selectedIndex: Int,
    onArticlesClick: () -> Unit,
    onShopClick: () -> Unit,
) {
    ModalDrawerSheet(
        modifier = Modifier.requiredWidth(200.dp),
        drawerShape = RectangleShape,
        drawerContainerColor = MaterialTheme.colorScheme.primary
    ) {

        NavigationDrawerItem(
            label = {
                Text(text = stringResource(Res.string.news))
            },
            icon = {
                Icon(imageVector = Icons.Default.Newspaper, contentDescription = null)
            },
            shape = RectangleShape,
            selected = selectedIndex == 0,
            onClick = onArticlesClick,
        )
        NavigationDrawerItem(
            label = {
                Text(text = stringResource(Res.string.shop))
            },
            icon = {
                Icon(imageVector = Icons.Default.Storefront, contentDescription = null)
            },
            shape = RectangleShape,
            selected = selectedIndex == 1,
            onClick = onShopClick,
        )
    }
}