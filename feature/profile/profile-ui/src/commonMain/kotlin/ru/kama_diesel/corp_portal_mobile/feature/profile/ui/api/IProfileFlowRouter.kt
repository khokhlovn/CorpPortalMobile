package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.api

import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.IRouter

interface IProfileFlowRouter : IRouter {
    fun toTransfer()
    fun back()
}
