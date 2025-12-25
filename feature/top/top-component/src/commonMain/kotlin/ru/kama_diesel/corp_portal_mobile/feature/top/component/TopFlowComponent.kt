package ru.kama_diesel.corp_portal_mobile.feature.top.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.feature.top.component.api.ITopComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.top.component.di.TopFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.top.component.di.create

class TopFlowComponent(
    componentContext: ComponentContext,
    componentDependencies: ITopComponentDependencies
) : ComponentContext by componentContext {

    private val diComponent = instanceKeeper.getOrCreate {
        TopFlowDIComponent::class.create(componentDependencies)
    }

    val router = TopFlowRouter(componentContext, diComponent)

    init {
        diComponent.getRouterHolder().updateInstance(router)
    }
}
