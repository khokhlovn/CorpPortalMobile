package ru.kama_diesel.corp_portal_mobile.common.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.CorpPortalApi
import ru.kama_diesel.corp_portal_mobile.common.data.network.mapper.DateTimeMapper
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IReservationListRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ReservationItem

@Inject
class ReservationListRepository(
    private val corpPortalApi: CorpPortalApi,
) : IReservationListRepository {

    override suspend fun getReservationList(start: Long?, finish: Long?): List<ReservationItem> {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getReservationList(
                start = start,
                finish = finish,
            ).reservationList?.map {
                ReservationItem(
                    placeId = it.placeId,
                    name = it.name,
                    isAvailable = it.isAvailable,
                    userId = it.userId,
                    start = it.start?.let { start ->
                        DateTimeMapper.getFormattedDate(
                            millis = start,
                            format = "dd.MM.yyyy HH:mm",
                        )
                    },
                    finish = it.finish?.let { finish ->
                        DateTimeMapper.getFormattedDate(
                            millis = finish,
                            format = "dd.MM.yyyy HH:mm",
                        )
                    },
                    fullName = it.fullName,
                    position = it.position,
                    department = it.department,
                    mail = it.mail,
                    mobile = it.mobile,
                    imagePath = it.imagePath,
                )
            } ?: listOf()
        }
    }
}
