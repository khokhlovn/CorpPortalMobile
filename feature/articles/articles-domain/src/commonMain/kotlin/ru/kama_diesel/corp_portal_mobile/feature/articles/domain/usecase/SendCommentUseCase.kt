package ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase

import io.ktor.client.plugins.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IArticlesRepository
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.api.ILogoutUseCase

@Inject
class SendCommentUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val articlesRepository: IArticlesRepository,
) {
    suspend operator fun invoke(postId: String, comment: String): Boolean {
        return try {
            articlesRepository.sendComment(postId = postId, comment = comment)
            true
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            false
        } catch (_: Exception) {
            false
        }
    }
}
