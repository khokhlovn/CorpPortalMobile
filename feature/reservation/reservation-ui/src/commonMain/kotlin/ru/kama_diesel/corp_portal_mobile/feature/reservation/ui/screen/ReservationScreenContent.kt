package ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ovh.plrapps.mapcompose.api.addCallout
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.removeCallout
import ovh.plrapps.mapcompose.ui.MapUI
import ovh.plrapps.mapcompose.ui.state.MapState
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TopWorkerItem
import ru.kama_diesel.corp_portal_mobile.resources.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ReservationScreenContent(
    mapState: MapState,
    office: Office,
    fromDate: Long?,
    toDate: Long?,
    total: Int,
    free: Int,
    reserved: Int,
    unavailable: Int,
    selectedPlace: String?,
    onOfficeChange: (Office) -> Unit,
    onDateChange: (Long?, Long?) -> Unit,
    onSelectedPlaceChange: (String?) -> Unit,
    onReserveClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.inverseSurface),
    ) {

        mapState.addMarker(
            id = "Л-120",
            x = 0.4975,
            y = 0.47,
        ) {
            IconToggleButton(
                checked = selectedPlace == "Л-120",
                onCheckedChange = { isSelected ->
                    onSelectedPlaceChange(
                        if (isSelected) {
                            "Л-120"
                        } else {
                            null
                        }
                    )
                }
            ) {
                Icon(
                    modifier = Modifier.background(color = Color.White, shape = CircleShape)
                        .size(28.dp),
                    painter = painterResource(
                        if (selectedPlace == "Л-120") {
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
                onClick = {
                    mapState.addCallout(
                        id = "callout",
                        x = 0.4395,
                        y = 0.43,
                        autoDismiss = false,
                    ) {
                        Card(
                            modifier = Modifier.width(200.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dp,
                            ),
                            shape = RoundedCornerShape(size = 12.dp),
                            colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.inverseSurface)
                        ) {
                            Box {
                                IconButton(
                                    modifier = Modifier.align(alignment = Alignment.TopEnd),
                                    onClick = { mapState.removeCallout("callout") },
                                ) {
                                    Icon(
                                        painter = painterResource(Res.drawable.close_24px),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        contentDescription = null,
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .padding(all = 12.dp)
                                        .padding(top = 12.dp)
                                ) {
                                    Text(
                                        text = stringResource(Res.string.fio),
                                        fontWeight = FontWeight.Medium,
                                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.outline,
                                    )
                                    Text(
                                        text = "Иванов Иван Иванович",
                                        fontSize = 14.sp,
                                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                                        color = MaterialTheme.colorScheme.scrim,
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = stringResource(Res.string.position),
                                        fontWeight = FontWeight.Medium,
                                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.outline,
                                    )
                                    Text(
                                        text = "Главный специалист по пожарной безопасности, делам гражданской обороны и чрезвычайным ситуациям",
                                        fontSize = 14.sp,
                                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                                        color = MaterialTheme.colorScheme.scrim,
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Button(
                                        modifier = Modifier.padding(horizontal = 12.dp).align(alignment = Alignment.CenterHorizontally),
                                        shape = ShapeDefaults.Small,
                                        onClick = {
                                        },
                                    ) {
                                        Text(text = stringResource(Res.string.more), fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
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

        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) { data ->
                    Snackbar(
                        snackbarData = data,
                        contentColor = MaterialTheme.colorScheme.scrim,
                        actionColor = MaterialTheme.colorScheme.inverseOnSurface,
                    )
                }
            },
            topBar = {
                Column {
                    ReservationFilterPanel(
                        office = office,
                        fromDate = fromDate,
                        toDate = toDate,
                        onOfficeChange = onOfficeChange,
                        onDateChange = onDateChange,
                    )
                    Spacer(
                        modifier = Modifier
                            .height(4.dp)
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.15f),
                                        Color.Transparent,
                                    )
                                )
                            )
                    )
                }
            },
            bottomBar = {
                Column {
                    Spacer(
                        modifier = Modifier
                            .height(4.dp)
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.15f),
                                    )
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.inverseSurface)
                            .fillMaxWidth()
                            .padding(all = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier
                                    .background(color = Color.White, shape = CircleShape)
                                    .size(28.dp),
                                painter = painterResource(Res.drawable.circle_circle_24px),
                                contentDescription = null,
                                tint = Color(0xFF009C2F),
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                modifier = Modifier.weight(1f),
                                text = stringResource(Res.string.free, free),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.scrim,
                            )
                            Icon(
                                modifier = Modifier.background(color = Color.White, shape = CircleShape)
                                    .size(28.dp),
                                painter = painterResource(Res.drawable.attribution_24px),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                modifier = Modifier.weight(1f),
                                text = stringResource(Res.string.reserved, reserved),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.scrim,
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier
                                    .background(color = Color.White, shape = CircleShape)
                                    .size(28.dp),
                                painter = painterResource(Res.drawable.circle_circle_24px),
                                contentDescription = null,
                                tint = Color.Gray,
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                modifier = Modifier.weight(1f),
                                text = stringResource(Res.string.unavailable, unavailable),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.scrim,
                            )
                            Icon(
                                modifier = Modifier.background(color = Color.White, shape = CircleShape)
                                    .size(32.dp),
                                painter = painterResource(Res.drawable.chair),
                                contentDescription = null,
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = stringResource(Res.string.total, total),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.scrim,
                            )
                        }
                    }
                }
            },
        ) { contentPadding ->
            MapUI(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.inverseSurface)
                    .fillMaxSize()
                    .padding(paddingValues = contentPadding),
                state = mapState,
            )
        }

        if (selectedPlace != null) {
            scope.launch {
                val result = snackbarHostState
                    .showSnackbar(
                        message = "Место: $selectedPlace",
                        actionLabel = "Забронировать",
                        duration = SnackbarDuration.Indefinite
                    )
                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        onReserveClick()
                    }
                    SnackbarResult.Dismissed -> {

                    }
                }
            }
        } else {
            snackbarHostState.currentSnackbarData?.dismiss()
        }
    }
}

@Serializable
enum class Office(val stringResourceId: StringResource, val shortStringResourceId: StringResource) {
    FirstLeft(stringResourceId = Res.string.first_left, shortStringResourceId = Res.string.first_left_short),
    SecondLeft(stringResourceId = Res.string.second_left, shortStringResourceId = Res.string.second_left_short),
    SecondRight(stringResourceId = Res.string.second_right, shortStringResourceId = Res.string.second_right_short),
    Olimp(stringResourceId = Res.string.olimp, shortStringResourceId = Res.string.olimp),
}
