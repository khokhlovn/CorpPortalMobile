package ru.kama_diesel.corp_portal_mobile.feature.main.ui.api

import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.IRouter

interface IMainFlowRouter : IRouter {
    fun toArticles()
    fun toShop()
    fun toPhoneDirectory()
    fun toProfile()
}
