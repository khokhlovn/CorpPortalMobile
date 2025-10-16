package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.ui.icons.Icons
import ru.kama_diesel.corp_portal_mobile.common.ui.icons.Logout
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticlesListViewState
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.logout
import ru.kama_diesel.corp_portal_mobile.resources.news

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesListScreen(
    viewState: ArticlesListViewState,
    onLogoutClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(Res.string.news))
                },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(imageVector = Icons.Logout, contentDescription = stringResource(Res.string.logout))
                    }
                }
            )
        },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            ArticlesListScreenContent(
                articleItems = viewState.articleItems,
            )
        }
    }
}
