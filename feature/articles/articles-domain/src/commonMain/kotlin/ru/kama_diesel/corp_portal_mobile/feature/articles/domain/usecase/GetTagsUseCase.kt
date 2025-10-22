package ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase

import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IArticlesRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TagItem

@Inject
class GetTagsUseCase(
    private val articlesRepository: IArticlesRepository,
) {
    suspend operator fun invoke(): List<TagItem> {
        return try {
            articlesRepository.getTagsList()
        } catch (_: Exception) {
            listOf()
        }
    }
}
