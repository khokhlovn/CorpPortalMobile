package ru.kama_diesel.corp_portal_mobile.feature.auth.component

import androidx.compose.runtime.Composable
import ru.kama_diesel.corp_portal_mobile.feature.auth.ui.AuthFlowScreenContainer


@Composable
fun AuthFlowScreenComponent(authFlowComponent: AuthFlowComponent) {
    AuthFlowScreenContainer(authFlowComponent.viewModel)
}