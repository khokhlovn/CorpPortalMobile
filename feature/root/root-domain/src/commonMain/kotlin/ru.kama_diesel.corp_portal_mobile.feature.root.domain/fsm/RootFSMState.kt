package ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm

import kotlinx.serialization.Serializable
import ru.kontur.mobile.visualfsm.State


@Serializable
sealed class RootFSMState : State {

    @Serializable
    sealed class AsyncWorkState : RootFSMState() {
        @Serializable
        data object Initial : AsyncWorkState()
    }

    @Serializable
    data object ArticlesFlow : RootFSMState()

    @Serializable
    data object AuthFlow : RootFSMState()
}
