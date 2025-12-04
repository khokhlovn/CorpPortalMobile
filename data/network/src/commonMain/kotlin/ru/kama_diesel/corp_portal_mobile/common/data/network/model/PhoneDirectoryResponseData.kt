package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhoneDirectoryResponseData(
    @SerialName("employees")
    val employees: List<Employee>? = null,
)

@Serializable
data class Employee(
    @SerialName("full_name")
    var fullName: String,
    @SerialName("position")
    var position: String,
    @SerialName("department")
    var department: String,
    @SerialName("service")
    var service: String,
    @SerialName("mail")
    var mail: String,
    @SerialName("mobile")
    var mobile: String? = null,
)
