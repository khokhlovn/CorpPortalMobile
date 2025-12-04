package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.domain.di.PhoneDirectoryListScope
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.domain.usecase.*
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.api.IPhoneDirectoryFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.model.EmployeeItemUIModel
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.model.PhoneDirectoryListDialog
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.model.PhoneDirectoryListViewState

@PhoneDirectoryListScope
@Inject
class PhoneDirectoryListViewModel(
    private val logoutUseCase: ILogoutUseCase,
    private val getPhoneDirectoryListUseCase: GetPhoneDirectoryListUseCase,
    private val getReservationListUseCase: GetReservationListUseCase,
    private val getPinnedEmployeeIdsUseCase: GetPinnedEmployeeIdsUseCase,
    private val savePinnedEmployeeIdUseCase: SavePinnedEmployeeIdUseCase,
    private val removeEmployeeIdFromPinnedUseCase: RemoveEmployeeIdFromPinnedUseCase,
    routerHolder: RouterHolder<IPhoneDirectoryFlowRouter>,
    private val initialState: PhoneDirectoryListViewState,
) : BaseStateViewModel<PhoneDirectoryListViewState>() {

    private val router by routerHolder

    init {
        getData()
    }

    fun getData() {
        setState {
            copy(
                isLoading = true,
            )
        }
        getPhoneDirectoryAndReservations()
    }

    fun onQueryChange(query: String) {
        if (query.isEmpty() || query.isNotBlank()) {
            setState {
                copy(
                    query = query,
                )
            }
            coroutineScope.launch {
                delay(300)
                filterAndSortItems()
            }
        }
    }

    fun onQueryClear() {
        setState {
            copy(
                query = "",
            )
        }
        coroutineScope.launch {
            filterAndSortItems()
        }
    }

    fun onPinChange(id: String, isPinned: Boolean) {
        setState {
            copy(
                employeeItems = employeeItems.map {
                    if (it.fullName != id) {
                        it
                    } else {
                        it.copy(
                            isPinned = isPinned,
                        )
                    }
                },
                filteredEmployeeItems = filteredEmployeeItems.map {
                    if (it.fullName != id) {
                        it
                    } else {
                        it.copy(
                            isPinned = isPinned,
                        )
                    }
                }
            )
        }
        if (isPinned) {
            savePinnedEmployeeIdUseCase(id = id)
        } else {
            removeEmployeeIdFromPinnedUseCase(id = id)
        }
        getData()
    }

    fun onSorterChange(sorter: Sorter) {
        setState {
            copy(
                selectedSorter = sorter,
            )
        }
        filterAndSortItems()
    }

    fun onDirectionChange(direction: Direction) {
        setState {
            copy(
                selectedDirection = direction,
            )
        }
        filterAndSortItems()
    }

    fun onCloseDialogClick() {
        setState {
            copy(
                dialog = PhoneDirectoryListDialog.No,
            )
        }
    }

    private fun getPhoneDirectoryAndReservations() {
        coroutineScope.launch {
            setState {
                copy(
                    isLoading = true,
                )
            }
            val reservationItems = getReservationListUseCase()
            val employeeItems = getPhoneDirectoryListUseCase()
            val pinnedEmployeeIds = getPinnedEmployeeIdsUseCase()
            setState {
                copy(
                    employeeItems = employeeItems.map { employeeItem ->
                        employeeItem to reservationItems.find { reservationItem -> reservationItem.fullName == employeeItem.fullName }
                    }.map { (employeeItem, reservationItem) ->
                        EmployeeItemUIModel(
                            fullName = employeeItem.fullName,
                            imagePath = reservationItem?.imagePath,
                            position = employeeItem.position,
                            department = employeeItem.department,
                            service = employeeItem.service,
                            mail = employeeItem.mail,
                            mobile = employeeItem.mobile,
                            workPlace = reservationItem?.name,
                            isPinned = pinnedEmployeeIds.contains(employeeItem.fullName),
                        )
                    },
                )
            }
            filterAndSortItems()
        }
    }

    private fun filterAndSortItems() {
        setState {
            copy(
                filteredEmployeeItems = with(query.trim()) {
                    employeeItems.filter { employeeItem ->
                        if (query.isBlank()) {
                            true
                        } else {
                            employeeItem.fullName.lowercase().contains(query.lowercase())
                                    || employeeItem.department.lowercase().contains(query.lowercase())
                                    || employeeItem.service.lowercase().contains(query.lowercase())
                                    || employeeItem.position.lowercase().contains(query.lowercase())
                                    || employeeItem.workPlace?.lowercase()?.contains(query.lowercase()) == true
                        }
                    }
                        .sortedWith(
                            when (selectedSorter) {
                                Sorter.Name if selectedDirection == Direction.Increasing -> compareBy { it.fullName }
                                Sorter.Name if selectedDirection == Direction.Decreasing -> compareByDescending { it.fullName }
                                Sorter.Department if selectedDirection == Direction.Increasing -> compareBy { it.department }
                                Sorter.Department if selectedDirection == Direction.Decreasing -> compareByDescending { it.department }
                                Sorter.Service if selectedDirection == Direction.Increasing -> compareBy { it.service }
                                Sorter.Service if selectedDirection == Direction.Decreasing -> compareByDescending { it.service }
                                Sorter.Position if selectedDirection == Direction.Increasing -> compareBy { it.position }
                                Sorter.Position if selectedDirection == Direction.Decreasing -> compareByDescending { it.position }
                                Sorter.Workplace if selectedDirection == Direction.Increasing -> compareBy { it.workPlace }
                                Sorter.Workplace if selectedDirection == Direction.Decreasing -> compareByDescending { it.workPlace }
                                else -> compareBy { true }
                            }
                        )
                        .sortedByDescending {
                            it.isPinned
                        }
                },
                isLoading = false,
            )
        }
    }

    override fun createInitialState() = initialState
}
