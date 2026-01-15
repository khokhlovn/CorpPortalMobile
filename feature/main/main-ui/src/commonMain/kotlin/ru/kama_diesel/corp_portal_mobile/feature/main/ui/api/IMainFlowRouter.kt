package ru.kama_diesel.corp_portal_mobile.feature.main.ui.api

import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.IRouter

interface IMainFlowRouter : IRouter {
    fun toArticles()
    fun toReservation()
    fun toShop()
    fun toCart()
    fun toOrdersHistory()
    fun toPhoneDirectory()
    fun toTop()
    fun toProfile()
}
