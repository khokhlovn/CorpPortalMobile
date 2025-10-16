package ru.kama_diesel.corp_portal_mobile.feature.root.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.kama_diesel.corp_portal_mobile.feature.articles.component.ArticlesFlowScreenComponent
import ru.kama_diesel.corp_portal_mobile.feature.auth.component.AuthFlowScreenComponent

@Composable
fun RootFlowScreenComponent(rootFlowComponent: RootFlowComponent) {
    val childSlot by rootFlowComponent.router.childSlot.subscribeAsState()

    when (val child = childSlot.child?.instance) {
        is RootFlowRouter.SlotChild.AuthFlow -> AuthFlowScreenComponent(child.component)
        is RootFlowRouter.SlotChild.ArticlesFlow -> ArticlesFlowScreenComponent(child.component)
        null -> Unit
    }
}
