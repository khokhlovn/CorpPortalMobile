package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.transfer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.transfer.model.TransferViewState
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.arrow_back_24px
import ru.kama_diesel.corp_portal_mobile.resources.comment_sent
import ru.kama_diesel.corp_portal_mobile.resources.transfer_success
import ru.kama_diesel.corp_portal_mobile.resources.transfer_thx

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(
    viewState: TransferViewState,
    onBackClick: () -> Unit,
    onRefresh: () -> Unit,
    onUserNameChange: (String) -> Unit,
    onUserSelect: (Int) -> Unit,
    onAmountSelect: (Int) -> Unit,
    onTransferClick: () -> Unit,
    onHideSnackbar: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarText = stringResource(Res.string.transfer_success)
    val isSnackBarShowing by mutableStateOf(viewState.showSuccessSnackbar)
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
                    Text(text = stringResource(Res.string.transfer_thx))
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
            TransferScreenContent(
                amount = viewState.amount,
                availableAmount = viewState.availableAmount,
                selectedUserId = viewState.selectedUserId,
                userName = viewState.userName,
                filteredUserIdsWithNames = viewState.filteredUserIdsWithNames,
                isRefreshing = viewState.isLoading,
                onRefresh = onRefresh,
                onUserNameChange = onUserNameChange,
                onUserSelect = onUserSelect,
                onAmountSelect = onAmountSelect,
                onTransferClick = onTransferClick,
            )
        }
    }
}
