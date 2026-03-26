package ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase

import io.ktor.client.plugins.ClientRequestException
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IArticlesRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase

@Inject
class CommentLikeUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val articlesRepository: IArticlesRepository,
) {
    suspend operator fun invoke(commentId: String): Boolean {
        return try {
            articlesRepository.commentLike(commentId = commentId)
            true
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            false
        } catch (_: Exception) {
            false
        }
    }
}
