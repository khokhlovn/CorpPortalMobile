package ru.kama_diesel.corp_portal_mobile.feature.top.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.CorpPortalApi
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ITopRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TopWorkerItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.WallOfFameItem

@Inject
class TopRepository(
    private val corpPortalApi: CorpPortalApi,
) : ITopRepository {

    override suspend fun getTopWorkers(): List<WallOfFameItem> {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getTopWorkers().wallOfFame?.map {
                WallOfFameItem(
                    year = it.year,
                    topWorkerItems = it.workersInfo?.map {
                        TopWorkerItem(
                            fullName = it.fullName,
                            position = it.position,
                            link = it.link,
                            imagePath = it.imagePath,
                        )
                    } ?: listOf(),
                )
            } ?: listOf()
        }
    }
}
