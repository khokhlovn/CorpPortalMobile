package ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm

import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IAuthorizedUserRepository
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.action.Initialize
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.action.RootFSMAction
import ru.kontur.mobile.visualfsm.AsyncWorker
import ru.kontur.mobile.visualfsm.AsyncWorkerTask

@Inject
class RootAsyncWorker(
    private val authorizedUserRepository: IAuthorizedUserRepository,
) : AsyncWorker<RootFSMState, RootFSMAction>() {
    override fun onNextState(state: RootFSMState): AsyncWorkerTask<RootFSMState> {
        return when (state) {
            is RootFSMState.AsyncWorkState.Initial -> checkAuthorization(state)
            else -> AsyncWorkerTask.Cancel()
        }
    }

    private fun checkAuthorization(state: RootFSMState): AsyncWorkerTask<RootFSMState> {
        return AsyncWorkerTask.ExecuteIfNotExist(state) {
            proceed(Initialize(isUserAuthorized = authorizedUserRepository.isUserAuthorized()))
        }
    }
}
