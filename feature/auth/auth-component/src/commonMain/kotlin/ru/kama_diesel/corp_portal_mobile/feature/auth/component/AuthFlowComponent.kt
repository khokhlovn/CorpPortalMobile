package ru.kama_diesel.corp_portal_mobile.feature.auth.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.common.component.registerAndGetSavedState
import ru.kama_diesel.corp_portal_mobile.feature.auth.component.api.IAuthComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.auth.component.di.AuthDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.auth.component.di.create
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.AuthFSMState

class AuthFlowComponent(
    componentContext: ComponentContext,
    dependencies: IAuthComponentDependencies,
) : ComponentContext by componentContext {

    private val savedState: AuthFSMState = registerAndGetSavedState(
        key = AUTH_FSM_SAVED_STATE,
        initialValue = AuthFSMState.Login("", ""),
        deserialization = AuthFSMState.serializer(),
        serialization = AuthFSMState.serializer()
    ) {
        diComponent.authFeature.getCurrentState()
    }

    private val diComponent = instanceKeeper.getOrCreate {
        AuthDIComponent::class.create(savedState, dependencies)
    }

    @Suppress("UNUSED")
    val viewModel = diComponent.viewModel

    companion object {
        private const val AUTH_FSM_SAVED_STATE = "AUTH_FSM_SAVED_STATE"
    }
}