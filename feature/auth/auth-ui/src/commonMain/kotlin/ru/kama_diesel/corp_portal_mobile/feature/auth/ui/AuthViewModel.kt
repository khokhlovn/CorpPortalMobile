package ru.kama_diesel.corp_portal_mobile.feature.auth.ui

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseViewModel
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.di.AuthFlowScope
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.AuthFSMState
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.AuthFeature
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.interfaces.IAuthCompletionUseCase

@AuthFlowScope
@Inject
class AuthViewModel(
    private val authFeature: AuthFeature,
    private val authCompletion: IAuthCompletionUseCase
) : BaseViewModel() {

    val viewState = authFeature.observeState().map(ViewStateMapper::map)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = ViewStateMapper.map(authFeature.getCurrentState())
        )

    init {
        initObservers()
    }

    fun startAuthenticating() {
        authFeature.startAuthenticating()
    }


    fun handleChangeLoginData(name: String, password: String) {
        authFeature.handleChangeLoginData(name, password)
    }

    override fun onCleared() {
        authFeature.onDestroy()
    }

    private fun initObservers() {
        coroutineScope.launch {
            authFeature.observeState().collect { fsmState ->
                if (fsmState is AuthFSMState.UserAuthorized) {
                    authCompletion(fsmState.name)
                }
            }
        }
    }
}
