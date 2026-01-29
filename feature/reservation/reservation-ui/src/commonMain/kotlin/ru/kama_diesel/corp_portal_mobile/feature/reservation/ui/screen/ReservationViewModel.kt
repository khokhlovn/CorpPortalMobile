package ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen

import kotlinx.coroutines.launch
import kotlinx.io.Buffer
import me.tatarka.inject.annotations.Inject
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.layout.Forced
import ovh.plrapps.mapcompose.ui.state.MapState
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.feature.reservation.domain.di.ReservationScope
import ru.kama_diesel.corp_portal_mobile.feature.reservation.domain.usecase.GetReservationsUseCase
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.model.ReservationDialog
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.model.ReservationViewState
import ru.kama_diesel.corp_portal_mobile.resources.Res

@ReservationScope
@Inject
class ReservationViewModel(
    private val getReservationsUseCase: GetReservationsUseCase,
    private val initialState: ReservationViewState,
) : BaseStateViewModel<ReservationViewState>() {

    lateinit var mapState: MapState

    init {
        getData()
        updateMapState()
    }

    fun getData() {
        getReservations()
    }

    fun onOfficeChange(office: Office) {
        setState {
            copy(
                office = office,
            )
        }
        updateMapState()
        getData()
    }

    fun onDateChange(fromDate: Long?, toDate: Long?) {
        setState {
            copy(
                fromDate = fromDate,
                toDate = toDate,
            )
        }
        getData()
    }

    fun onSelectedPlaceChange(selectedPlace: String?) {
        setState {
            copy(
                selectedPlace = selectedPlace,
            )
        }
    }

    fun onReserveClick() {
        currentState.selectedPlace?.let {
            setState {
                copy(
                    dialog = ReservationDialog.Confirmation(selectedPlace = it),
                )
            }
        }
    }

    fun onConfirmReservationClick() {
        getData()
    }

    fun onCloseConfirmationClick() {
        currentState.selectedPlace?.let {
            setState {
                copy(
                    dialog = ReservationDialog.No,
                    selectedPlace = null,
                )
            }
        }
    }

    private fun getReservations() {
        coroutineScope.launch {
            setState {
                copy(
                    dialog = ReservationDialog.Loading,
                )
            }
            val prefix = when (currentState.office) {
                Office.FirstLeft -> "Л-1"
                Office.SecondLeft -> "Л-2"
                Office.SecondRight -> "П-2"
                Office.Olimp -> "Олимп-"
            }
            val reservationItems = getReservationsUseCase(
                fromDate = currentState.fromDate,
                toDate = currentState.toDate,
            )
            val places = reservationItems.filter { it.name.startsWith(prefix) }
            setState {
                copy(
                    reservationItems = reservationItems,
                    total = places.size,
                    reserved = places.filter { it.userId != 0 }.size,
                    free = places.filter { it.isAvailable }.size,
                    unavailable = places.filter { it.userId == 0 && !it.isAvailable }.size,
                    selectedPlace = null,
                    dialog = ReservationDialog.No,
                )
            }
        }
    }

    private fun updateMapState() {
        val tileStreamProvider = TileStreamProvider { row, col, zoomLvl ->
            val buffer = Buffer()
            val folderName = when (currentState.office) {
                Office.FirstLeft -> "l1"
                Office.SecondLeft -> "l2"
                Office.SecondRight -> "p2"
                Office.Olimp -> "olimp"
            }
            buffer.write(source = Res.readBytes("files/$folderName/${zoomLvl}/${col}/${row}.webp"))
            buffer
        }
        val size = if (currentState.office == Office.Olimp) {
            2048
        } else {
            4096
        }
        val minScale = if (currentState.office == Office.Olimp) {
            0.7
        } else {
            0.5
        }
        val maxScale = if (currentState.office == Office.Olimp) {
            1.2
        } else {
            0.7
        }
        mapState = MapState(
            levelCount = if (currentState.office == Office.Olimp) {
                4
            } else {
                5
            },
            fullWidth = size,
            fullHeight = size,
            initialValuesBuilder = {
                minimumScaleMode(minimumScaleMode = Forced(scale = minScale))
                maxScale(maxScale = maxScale)
                scale(scale = minScale)
                preloadingPadding(padding = 256)
            }
        ).apply {
            addLayer(tileStreamProvider)
        }
    }

    override fun createInitialState() = initialState
}
