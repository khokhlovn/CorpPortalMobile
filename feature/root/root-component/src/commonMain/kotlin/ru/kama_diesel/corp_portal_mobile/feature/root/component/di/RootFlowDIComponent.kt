package ru.kama_diesel.corp_portal_mobile.feature.root.component.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.common.data.repository.AuthorizedUserRepository
import ru.kama_diesel.corp_portal_mobile.common.data.repository.ReservationListRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IAuthorizedUserRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IReservationListRepository
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.auth.component.api.IAuthComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.interfaces.IAuthCompletionUseCase
import ru.kama_diesel.corp_portal_mobile.feature.main.component.api.IMainComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.root.component.api.IRootComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.root.component.di.dependencies.AuthComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.root.component.di.dependencies.MainComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.di.RootFlowScope
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.RootFSMState
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.RootFeature
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.usecase.AuthCompletionUseCase
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.usecase.LogoutUseCase
import ru.kama_diesel.corp_portal_mobile.feature.root.ui.RootViewModel
import ru.kama_diesel.corp_portal_mobile.feature.root.ui.api.IRootFlowRouter

@RootFlowScope
@Component
internal abstract class RootFlowDIComponent(
    private val initialState: RootFSMState,
    @Component val dependencies: IRootComponentDependencies
) : InstanceKeeper.Instance {

    private val routerHolder = RouterHolder<IRootFlowRouter>()

    abstract val viewModel: RootViewModel

    abstract val rootFeature: RootFeature

    abstract val authComponentDependencies: IAuthComponentDependencies

    abstract val mainComponentDependencies: IMainComponentDependencies

    @Provides
    fun getRouterHolder(): RouterHolder<IRootFlowRouter> = routerHolder

    @Provides
    protected fun getInitialState(): RootFSMState = initialState

    @Provides
    protected fun bind(it: AuthorizedUserRepository): IAuthorizedUserRepository = it

    @Provides
    protected fun bind(it: ReservationListRepository): IReservationListRepository = it

    @Provides
    protected fun bind(it: AuthCompletionUseCase): IAuthCompletionUseCase = it

    @Provides
    protected fun bind(it: LogoutUseCase): ILogoutUseCase = it

    @Provides
    protected fun bind(it: AuthComponentDependencies): IAuthComponentDependencies = it

    @Provides
    protected fun bind(it: MainComponentDependencies): IMainComponentDependencies = it


    override fun onDestroy() {
        viewModel.onDestroy()
    }
}