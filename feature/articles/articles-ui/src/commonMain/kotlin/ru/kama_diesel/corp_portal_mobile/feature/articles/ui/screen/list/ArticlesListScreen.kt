package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.ui.icons.Icons
import ru.kama_diesel.corp_portal_mobile.common.ui.icons.Logout
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticlesListViewState
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.filters
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
        Box(modifier = Modifier.padding(paddingValues = paddingValues).fillMaxSize()) {
            ArticlesListScreenContent(
                articleItems = viewState.articleItems,
            )
            Button(
                modifier = Modifier
                    .offset(x = 40.dp)
                    .rotate(-90f)
                    .align(Alignment.CenterEnd),
                shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 20.dp),
                onClick = {},
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(Res.string.filters),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}
