package ru.kama_diesel.corp_portal_mobile.feature.top.ui.screen.model

import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TopWorkerItem

@Serializable
data class TopViewState(
    val topWorkers: List<TopWorkerItem>,
    val isLoading: Boolean,
)
