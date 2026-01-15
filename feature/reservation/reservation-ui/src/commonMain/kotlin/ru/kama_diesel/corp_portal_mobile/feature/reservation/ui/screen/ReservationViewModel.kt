package ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen

import kotlinx.coroutines.launch
import kotlinx.io.Buffer
import me.tatarka.inject.annotations.Inject
import org.jetbrains.compose.resources.readResourceBytes
import ovh.plrapps.mapcompose.api.addCallout
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.addLazyLoader
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.setPreloadingPadding
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.layout.Fill
import ovh.plrapps.mapcompose.ui.layout.Forced
import ovh.plrapps.mapcompose.ui.state.MapState
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.feature.reservation.domain.di.ReservationScope
import ru.kama_diesel.corp_portal_mobile.feature.reservation.domain.usecase.GetMapTileUseCase
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.model.ReservationViewState
import ru.kama_diesel.corp_portal_mobile.resources.Res

@ReservationScope
@Inject
class ReservationViewModel(
    private val getMapTileUseCase: GetMapTileUseCase,
    private val initialState: ReservationViewState,
) : BaseStateViewModel<ReservationViewState>() {

    private val tileStreamProvider = TileStreamProvider { row, col, zoomLvl ->
        val buffer = Buffer()
        buffer.write(source = Res.readBytes("files/map/${zoomLvl}/${col}/${row}.png"))
        buffer
    }

    val mapState = MapState(
        levelCount = 5,
        fullWidth = 4096,
        fullHeight = 4096,
        initialValuesBuilder = {
            minimumScaleMode(minimumScaleMode = Forced(scale = 0.5))
            maxScale(maxScale = 0.7)
            scale(scale = 0.5)
            preloadingPadding(padding = 256)
        }
    ).apply {
        addLayer(tileStreamProvider)
    }

    init {
        getData()
    }

    fun getData() {
        getProfileInfo()
    }

    private fun getProfileInfo() {
        coroutineScope.launch {
            setState {
                copy(
                    isLoading = true,
                )
            }
            setState {
                copy(
                    topWorkers = listOf(),
                    isLoading = false,
                )
            }
        }
    }

    override fun createInitialState() = initialState
}
