package ru.kama_diesel.corp_portal_mobile.feature.top.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TopWorkerItem
import ru.kama_diesel.corp_portal_mobile.common.ui.theme.AppTheme
import ru.kama_diesel.corp_portal_mobile.feature.top.ui.screen.TopScreen
import ru.kama_diesel.corp_portal_mobile.feature.top.ui.screen.model.TopViewState

@Preview
@Composable
private fun ProfileScreenPreview() {
    AppTheme {
        TopScreen(
            viewState = TopViewState(
                topWorkers = listOf(
                    TopWorkerItem(
                        fullName = "Иванов Иван Иванович",
                        position = "Старший мастер",
                        imagePath = "",
                        link = "",
                    )
                ),
                isLoading = false,
            ),
            onRefresh = {},
        )
    }
}
