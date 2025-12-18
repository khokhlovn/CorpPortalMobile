package ru.kama_diesel.corp_portal_mobile.feature.profile.component.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IProfileRepository
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.api.IProfileComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.profile.data.repository.ProfileRepository
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.api.IProfileFlowRouter

@Component
abstract class ProfileFlowDIComponent(
    @Component val dependencies: IProfileComponentDependencies
) : InstanceKeeper.Instance {

    private val routerHolder = RouterHolder<IProfileFlowRouter>()

    @Provides
    fun getRouterHolder(): RouterHolder<IProfileFlowRouter> = routerHolder

    val ProfileRepository.bind: IProfileRepository
        @Provides get() = this
}
