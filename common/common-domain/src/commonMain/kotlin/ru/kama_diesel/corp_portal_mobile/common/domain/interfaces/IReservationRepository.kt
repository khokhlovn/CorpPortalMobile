package ru.kama_diesel.corp_portal_mobile.common.domain.interfaces

import ru.kama_diesel.corp_portal_mobile.common.domain.model.TopWorkerItem

interface IReservationRepository {
    suspend fun getMap(row: Int, col: Int, zoomLvl: Int): ByteArray
}
