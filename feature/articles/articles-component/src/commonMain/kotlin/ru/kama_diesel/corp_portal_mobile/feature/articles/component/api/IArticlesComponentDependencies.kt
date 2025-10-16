package ru.kama_diesel.corp_portal_mobile.feature.articles.component.api

import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.api.ILogoutUseCase

interface IArticlesComponentDependencies {
    val logoutUseCase: ILogoutUseCase
}
