package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.list

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ProfileItem
import ru.kama_diesel.corp_portal_mobile.common.ui.theme.AppTheme
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.profile.ProfileScreen
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.profile.model.ProfileViewState

@Preview
@Composable
private fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen(
            viewState = ProfileViewState(
                profileItem = ProfileItem(
                    username = "",
                    fullName = "Иванов Иван Иванович",
                    position = "Старший мастер",
                    department = "Цех обработки блока цилиндров",
                    mail = "ivanov.ivan.ivanovich@mail.ru",
                    mobile = "9211231234",
                    chief = "",
                    personalMobile = "",
                    personalMail = ""
                ),
                imagePath = null,
                balance = 116,
                cartItemsCount = 0,
                ordersCount = 0,
                isFirstLoading = false,
                isLoading = false,
            ),
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            onRefresh = {},
            onLogoutClick = {},
            onBalanceClick = {},
            onCartClick = {},
            onOrdersHistoryClick = {},
        )
    }
}
