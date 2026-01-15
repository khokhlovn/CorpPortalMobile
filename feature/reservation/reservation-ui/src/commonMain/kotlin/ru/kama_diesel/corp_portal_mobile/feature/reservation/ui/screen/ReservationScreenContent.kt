package ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.ui.MapUI
import ovh.plrapps.mapcompose.ui.state.MapState
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TopWorkerItem
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.attribution_24px
import ru.kama_diesel.corp_portal_mobile.resources.check_circle_24px
import ru.kama_diesel.corp_portal_mobile.resources.circle_circle_24px

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ReservationScreenContent(
    topWorkers: List<TopWorkerItem>,
    mapState: MapState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.inverseSurface),
    ) {
        val state = remember { mutableStateOf(false) }

        mapState.addMarker(
            id = "0",
            x = 0.4975,
            y = 0.47,
        ) {
            IconToggleButton(
                checked = state.value,
                onCheckedChange = { state.value = !state.value }
            ) {
                Icon(
                    modifier = Modifier.background(color = Color.White, shape = CircleShape)
                        .size(28.dp),
                    painter = painterResource(
                        if (state.value) {
                            Res.drawable.check_circle_24px
                        } else {
                            Res.drawable.circle_circle_24px
                        }
                    ),
                    contentDescription = null,
                    tint = Color(0xFF009C2F),
                )
            }
        }
        mapState.addMarker(
            id = "1",
            x = 0.4395,
            y = 0.47,
        ) {
            IconButton(
                onClick = {  }
            ) {
                Icon(
                    modifier = Modifier.background(color = Color.White, shape = CircleShape)
                        .size(28.dp),
                    painter = painterResource(Res.drawable.attribution_24px),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        }
        MapUI(
            modifier = Modifier.fillMaxSize(),
            state = mapState,
        )
    }
}
