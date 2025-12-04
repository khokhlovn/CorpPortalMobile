package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IPhoneDirectoryRepository
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component.api.IPhoneDirectoryComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.data.repository.PhoneDirectoryRepository
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.api.IPhoneDirectoryFlowRouter

@Component
abstract class PhoneDirectoryFlowDIComponent(
    @Component val dependencies: IPhoneDirectoryComponentDependencies
) : InstanceKeeper.Instance {

    private val routerHolder = RouterHolder<IPhoneDirectoryFlowRouter>()

    @Provides
    fun getRouterHolder(): RouterHolder<IPhoneDirectoryFlowRouter> = routerHolder

    val PhoneDirectoryRepository.bind: IPhoneDirectoryRepository
        @Provides get() = this
}
