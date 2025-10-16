package ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.action

import ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.RootFSMState
import ru.kontur.mobile.visualfsm.Action

sealed class RootFSMAction : Action<RootFSMState>()
