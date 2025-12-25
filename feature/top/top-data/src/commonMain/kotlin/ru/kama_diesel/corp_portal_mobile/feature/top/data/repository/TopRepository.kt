package ru.kama_diesel.corp_portal_mobile.feature.top.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.CorpPortalApi
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ITopRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TopWorkerItem

@Inject
class TopRepository(
    private val corpPortalApi: CorpPortalApi,
) : ITopRepository {

    override suspend fun getTopWorkers(): List<TopWorkerItem> {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getTopWorkers().workersInfo?.map {
                TopWorkerItem(
                    fullName = it.fullName,
                    position = it.position,
                    link = it.link,
                    imagePath = it.imagePath,
                )
            } ?: listOf()
        }
    }
}
