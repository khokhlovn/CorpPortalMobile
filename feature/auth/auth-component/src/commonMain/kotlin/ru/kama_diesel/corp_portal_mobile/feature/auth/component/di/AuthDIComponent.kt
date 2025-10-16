package ru.kama_diesel.corp_portal_mobile.feature.auth.component.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.feature.auth.component.api.IAuthComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.auth.data.repository.UserRepository
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.di.AuthFlowScope
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.AuthFSMState
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.AuthFeature
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.interfaces.IUserRepository
import ru.kama_diesel.corp_portal_mobile.feature.auth.ui.AuthViewModel

@AuthFlowScope
@Component
abstract class AuthDIComponent(
    private val initialState: AuthFSMState,
    @Component val dependencies: IAuthComponentDependencies
) : InstanceKeeper.Instance {
    abstract val viewModel: AuthViewModel

    abstract val authFeature: AuthFeature

    @Provides
    protected fun getInitialState(): AuthFSMState = initialState

    protected val UserRepository.bind: IUserRepository
        @Provides get() = this

    override fun onDestroy() {
        viewModel.onDestroy()
    }
}
