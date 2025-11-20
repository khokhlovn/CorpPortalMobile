package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.api

import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.IRouter

interface IShopFlowRouter : IRouter {

    fun toCart(shopItems: List<ShopItem>)
    fun back()
}
