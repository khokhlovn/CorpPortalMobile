package ru.kama_diesel.corp_portal_mobile.common.domain.interfaces

import ru.kama_diesel.corp_portal_mobile.common.domain.model.ReservationItem

interface IReservationListRepository {
    suspend fun getReservationList(start: Long?, finish: Long?): List<ReservationItem>
}
