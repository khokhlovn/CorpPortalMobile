package ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm

import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.AuthFSMState.AsyncWorkState
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.actions.AuthFSMAction
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.actions.HandleAuthResult
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.usecase.AuthorizeUseCase
import ru.kontur.mobile.visualfsm.AsyncWorker
import ru.kontur.mobile.visualfsm.AsyncWorkerTask

@Inject
class AuthAsyncWorker(
    private val authorize: AuthorizeUseCase,
) : AsyncWorker<AuthFSMState, AuthFSMAction>() {

    override fun onNextState(state: AuthFSMState): AsyncWorkerTask<AuthFSMState> {
        return when (state) {
            is AsyncWorkState.Authenticating -> {
                AsyncWorkerTask.ExecuteAndCancelExist(state) {
                    val result = authorize(state.name, state.password)
                    proceed(HandleAuthResult(result))
                }
            }

            else -> AsyncWorkerTask.Cancel()
        }
    }
}