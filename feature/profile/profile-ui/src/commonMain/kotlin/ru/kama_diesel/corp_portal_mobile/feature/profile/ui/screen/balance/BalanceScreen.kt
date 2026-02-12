package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.balance

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.balance.model.BalanceViewState
import ru.kama_diesel.corp_portal_mobile.resources.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceScreen(
    viewState: BalanceViewState,
    onBackClick: () -> Unit,
    onRefresh: () -> Unit,
    onQueryChange: (String) -> Unit,
    onQueryClear: () -> Unit,
    onSorterChange: (Sorter) -> Unit,
    onHideSnackbar: () -> Unit,
    onToTransferClick: () -> Unit,
    onToBalanceClick: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarText = if (viewState.showSuccessSnackbar) {
        stringResource(Res.string.weekly_thx_success)
    } else if (viewState.showErrorSnackbar) {
        stringResource(Res.string.weekly_thx_error)
    } else {
        ""
    }
    val isSnackBarShowing by mutableStateOf(viewState.showSuccessSnackbar || viewState.showErrorSnackbar)
    if (isSnackBarShowing) {
        LaunchedEffect(isSnackBarShowing) {
            snackbarHostState.showSnackbar(snackbarText)
            onHideSnackbar()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(Res.string.balance))
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.arrow_back_24px),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
        ) {
            BalanceScreenContent(
                balance = viewState.balance,
                giftBalance = viewState.giftBalance,
                weeklyAward = viewState.weeklyAward,
                endOfWeekDate = viewState.endOfWeekDate,
                fullName = viewState.fullName,
                filteredHistoryEvents = viewState.filteredHistoryEvents,
                query = viewState.query,
                selectedSorter = viewState.selectedSorter,
                isRefreshing = viewState.isLoading,
                onRefresh = onRefresh,
                onQueryChange = onQueryChange,
                onQueryClear = onQueryClear,
                onSorterChange = onSorterChange,
                onToTransferClick = onToTransferClick,
                onToBalanceClick = onToBalanceClick,
            )
        }
    }
}
