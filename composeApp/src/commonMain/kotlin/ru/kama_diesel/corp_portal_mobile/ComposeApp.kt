package ru.kama_diesel.corp_portal_mobile

import androidx.compose.runtime.Composable
import ru.kama_diesel.corp_portal_mobile.common.ui.theme.AppTheme
import ru.kama_diesel.corp_portal_mobile.feature.root.component.RootFlowComponent
import ru.kama_diesel.corp_portal_mobile.feature.root.component.RootFlowScreenComponent

@Composable
internal fun ComposeApp(rootFlowComponent: RootFlowComponent) {
    AppTheme {
        RootFlowScreenComponent(rootFlowComponent)
    }
}
