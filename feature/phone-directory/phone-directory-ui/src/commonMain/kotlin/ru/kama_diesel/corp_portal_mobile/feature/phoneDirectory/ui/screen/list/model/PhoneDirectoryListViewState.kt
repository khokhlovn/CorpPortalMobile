package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.model

import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.Direction
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.Sorter

@Serializable
data class PhoneDirectoryListViewState(
    val employeeItems: List<EmployeeItemUIModel>,
    val filteredEmployeeItems: List<EmployeeItemUIModel>,
    val selectedSorter: Sorter,
    val selectedDirection: Direction,
    val query: String,
    val dialog: PhoneDirectoryListDialog,
    val isLoading: Boolean,
)

@Serializable
data class EmployeeItemUIModel(
    val fullName: String,
    val imagePath: String?,
    val position: String,
    val department: String,
    val service: String,
    val mail: String,
    val mobile: String?,
    val workPlace: String?,
    val isPinned: Boolean,
)

@Serializable
sealed class PhoneDirectoryListDialog {
    @Serializable
    data object No : PhoneDirectoryListDialog()

    @Serializable
    data class Details(val employeeItemUIModel: EmployeeItemUIModel) : PhoneDirectoryListDialog()
}

@Serializable
data class ShopItemUIModel(
    val id: Int,
    val name: String,
    val description: String,
    val characteristics: Map<String, String>,
    val partNumber: String?,
    val price: Int?,
    val imagePaths: List<String>?,
    val isAvailable: Boolean,
    val isActive: Boolean,
    val cartAddingState: CartAddingState,
)

@Serializable
sealed class CartAddingState {
    @Serializable
    data object No : CartAddingState()

    @Serializable
    data object Adding : CartAddingState()
}
