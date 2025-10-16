package ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.actions

import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.AuthFSMState
import ru.kontur.mobile.visualfsm.Action

sealed class AuthFSMAction : Action<AuthFSMState>()