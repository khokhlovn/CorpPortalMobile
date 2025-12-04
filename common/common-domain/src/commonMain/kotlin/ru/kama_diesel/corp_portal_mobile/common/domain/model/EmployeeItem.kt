package ru.kama_diesel.corp_portal_mobile.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class EmployeeItem(
    val fullName: String,
    val position: String,
    val department: String,
    val service: String,
    val mail: String,
    val mobile: String? = null,
)
