package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component.api.IPhoneDirectoryComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component.di.PhoneDirectoryFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component.di.create

class PhoneDirectoryFlowComponent(
    componentContext: ComponentContext,
    componentDependencies: IPhoneDirectoryComponentDependencies
) : ComponentContext by componentContext {

    private val diComponent = instanceKeeper.getOrCreate {
        PhoneDirectoryFlowDIComponent::class.create(componentDependencies)
    }

    val router = PhoneDirectoryFlowRouter(componentContext, diComponent)

    init {
        diComponent.getRouterHolder().updateInstance(router)
    }
}
