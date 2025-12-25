package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.model.ProfileViewState
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.logout_24px
import ru.kama_diesel.corp_portal_mobile.resources.menu_24px
import ru.kama_diesel.corp_portal_mobile.resources.profile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    drawerState: DrawerState,
    viewState: ProfileViewState,
    onLogoutClick: () -> Unit,
    onRefresh: () -> Unit,
    onCartClick: () -> Unit,
    onOrdersHistoryClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(Res.string.profile))
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
                        onClick = onLogoutClick,
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.logout_24px),
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
                .padding(paddingValues = paddingValues),
        ) {
            ProfileScreenContent(
                profileItem = viewState.profileItem,
                imagePath = viewState.imagePath,
                balance = viewState.balance,
                cartItemsCount = viewState.cartItemsCount,
                ordersCount = viewState.ordersCount,
                isRefreshing = viewState.isLoading,
                isFirstLoading = viewState.isFirstLoading,
                onRefresh = onRefresh,
                onCartClick = onCartClick,
                onOrdersHistoryClick = onOrdersHistoryClick,
            )
        }
    }
}
