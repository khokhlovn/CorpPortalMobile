package ru.kama_diesel.corp_portal_mobile.feature.top.component.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ITopRepository
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.top.component.api.ITopComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.top.data.repository.TopRepository
import ru.kama_diesel.corp_portal_mobile.feature.top.ui.api.ITopFlowRouter

@Component
abstract class TopFlowDIComponent(
    @Component val dependencies: ITopComponentDependencies
) : InstanceKeeper.Instance {

    private val routerHolder = RouterHolder<ITopFlowRouter>()

    @Provides
    fun getRouterHolder(): RouterHolder<ITopFlowRouter> = routerHolder

    val TopRepository.bind: ITopRepository
        @Provides get() = this
}
