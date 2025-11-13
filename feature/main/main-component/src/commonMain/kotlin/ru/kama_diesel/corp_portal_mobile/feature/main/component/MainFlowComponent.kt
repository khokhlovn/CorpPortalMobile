package ru.kama_diesel.corp_portal_mobile.feature.root.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.feature.main.component.MainFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.main.component.api.IMainComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.main.component.di.MainFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.main.component.di.create

class MainFlowComponent(
    componentContext: ComponentContext,
    dependencies: IMainComponentDependencies
) : ComponentContext by componentContext {

    private val diComponent = instanceKeeper.getOrCreate {
        MainFlowDIComponent::class.create(dependencies)
    }

    internal val router = MainFlowRouter(componentContext, diComponent)

    val viewModel = diComponent.viewModel

    init {
        diComponent.getRouterHolder().updateInstance(router)
    }
}
