package ru.kama_diesel.corp_portal_mobile.feature.main.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.feature.main.ui.screen.menu.MenuComponent
import ru.kama_diesel.corp_portal_mobile.resources.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    drawerState: DrawerState,
    selectedIndex: Int,
    tab: @Composable () -> Unit,
    onArticlesClick: () -> Unit,
    onReservationClick: () -> Unit,
    onShopClick: () -> Unit,
    onTopClick: () -> Unit,
    onPhoneDirectoryClick: () -> Unit,
    onProfileClick: () -> Unit,
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
                onReservationClick = {
                    onReservationClick()
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
                onTopClick = {
                    onTopClick()
                    scope.launch {
                        drawerState.apply {
                            close()
                        }
                    }
                },
                onPhoneDirectoryClick = {
                    onPhoneDirectoryClick()
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
        if (selectedIndex != 2 && selectedIndex != 999) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text =
                                    when (selectedIndex) {
                                        0 -> stringResource(Res.string.news)
                                        1 -> stringResource(Res.string.reservation)
                                        2 -> stringResource(Res.string.shop)
                                        3 -> stringResource(Res.string.top_workers)
                                        4 -> stringResource(Res.string.phone_directory)
                                        else -> ""
                                    },
                                autoSize = TextAutoSize.StepBased(maxFontSize = 20.sp),
                                maxLines = 1,
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
                                    painter = painterResource(Res.drawable.menu_24px),
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    contentDescription = null,
                                )
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = onProfileClick,
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.account_circle_24px),
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
