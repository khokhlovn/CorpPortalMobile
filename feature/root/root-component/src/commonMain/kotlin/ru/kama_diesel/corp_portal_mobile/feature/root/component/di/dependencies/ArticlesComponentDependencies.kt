package ru.kama_diesel.corp_portal_mobile.feature.root.component.di.dependencies

import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.feature.articles.component.api.IArticlesComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.api.ILogoutUseCase

@Inject
internal class ArticlesComponentDependencies(
    lazyLogoutUseCase: Lazy<ILogoutUseCase>,
) : IArticlesComponentDependencies {
    override val logoutUseCase by lazyLogoutUseCase
}
