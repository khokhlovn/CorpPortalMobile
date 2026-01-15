package ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.state.MapState
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.model.ReservationViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationScreen(
    viewState: ReservationViewState,
    mapState: MapState,
    onRefresh: () -> Unit,
) {
    ReservationScreenContent(
        topWorkers = viewState.topWorkers,
        isRefreshing = viewState.isLoading,
        mapState = mapState,
        onRefresh = onRefresh,
    )
}
