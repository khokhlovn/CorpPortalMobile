package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
    onCheckedChange: (String, Boolean) -> Unit,
    onLogoutClick: () -> Unit,
    onRefresh: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(Res.string.news))
                },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            imageVector = Icons.Logout,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = stringResource(Res.string.logout)
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues).fillMaxSize()) {
            val focusRequester = remember { FocusRequester() }
            ArticlesListScreenContent(
                modifier = Modifier.focusRequester(focusRequester),
                articleItems = viewState.articleItems,
                isRefreshing = viewState.isLoading,
                onRefresh = onRefresh,
            )
            Row(modifier = Modifier.fillMaxHeight().align(alignment = Alignment.CenterEnd)) {
                Button(
                    modifier = Modifier
                        .offset(x = 40.dp)
                        .rotate(-90f)
                        .align(alignment = Alignment.CenterVertically),
                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 20.dp),
                    onClick = {
                        focusRequester.freeFocus()
                        expanded = !expanded
                    },
                ) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(Res.string.filters),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
                FiltersContent(
                    tagItems = viewState.tagItems,
                    expanded = expanded,
                    onCheckedChange = onCheckedChange,
                )
            }

        }
    }
}
