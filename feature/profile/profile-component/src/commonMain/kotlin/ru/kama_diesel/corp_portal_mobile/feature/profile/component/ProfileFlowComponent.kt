package ru.kama_diesel.corp_portal_mobile.feature.profile.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.api.IProfileComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.di.ProfileFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.di.create

class ProfileFlowComponent(
    componentContext: ComponentContext,
    componentDependencies: IProfileComponentDependencies
) : ComponentContext by componentContext {

    private val diComponent = instanceKeeper.getOrCreate {
        ProfileFlowDIComponent::class.create(componentDependencies)
    }

    val router = ProfileFlowRouter(componentContext, diComponent)

    init {
        diComponent.getRouterHolder().updateInstance(router)
    }
}
