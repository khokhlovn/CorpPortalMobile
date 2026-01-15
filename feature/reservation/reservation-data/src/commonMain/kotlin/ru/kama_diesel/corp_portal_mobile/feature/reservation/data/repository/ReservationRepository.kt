package ru.kama_diesel.corp_portal_mobile.feature.reservation.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.CorpPortalApi
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IReservationRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ITopRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TopWorkerItem

@Inject
class ReservationRepository(
    private val corpPortalApi: CorpPortalApi,
) : IReservationRepository {

    override suspend fun getMap(row: Int, col: Int, zoomLvl: Int): ByteArray {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getMap(
                row = row,
                col = col,
                zoomLvl = zoomLvl,
            )
        }
    }
}
