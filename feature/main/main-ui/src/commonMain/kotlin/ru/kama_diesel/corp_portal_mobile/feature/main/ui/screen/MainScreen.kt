package ru.kama_diesel.corp_portal_mobile.feature.main.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.feature.main.ui.screen.menu.MenuComponent
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.news
import ru.kama_diesel.corp_portal_mobile.resources.shop

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    drawerState: DrawerState,
    selectedIndex: Int,
    tab: @Composable () -> Unit,
    onArticlesClick: () -> Unit,
    onShopClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MenuComponent(
                selectedIndex = selectedIndex,
                onArticlesClick = {
                    onArticlesClick()
                    scope.launch {
                        drawerState.apply {
                            close()
                        }
                    }
                },
                onShopClick = {
                    onShopClick()
                    scope.launch {
                        drawerState.apply {
                            close()
                        }
                    }
                },
            )
        },
        scrimColor = Color.Transparent,
    ) {
        if (selectedIndex != 1) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text =
                                    when (selectedIndex) {
                                        0 -> stringResource(Res.string.news)
                                        1 -> stringResource(Res.string.shop)
                                        else -> ""
                                    }
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    contentDescription = null,
                                )
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = onLogoutClick,
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Logout,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    contentDescription = null,
                                )
                            }
                        }
                    )
                },
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = paddingValues)
                ) {
                    tab()
                }
            }
        } else {
            tab()
        }
    }
}
