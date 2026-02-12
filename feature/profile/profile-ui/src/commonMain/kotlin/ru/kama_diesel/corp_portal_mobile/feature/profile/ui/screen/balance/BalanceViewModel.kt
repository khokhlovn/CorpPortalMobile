package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.balance

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ThxHistoryItem
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.di.BalanceScope
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase.GetMyInfoUseCase
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase.GetProfileUseCase
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase.GetThxHistoryUseCase
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase.TakeAwardUseCase
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.api.IProfileFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.balance.model.BalanceViewState
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@BalanceScope
@Inject
class BalanceViewModel(
    private val initialState: BalanceViewState,
    private val getThxHistoryUseCase: GetThxHistoryUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val takeAwardUseCase: TakeAwardUseCase,
    routerHolder: RouterHolder<IProfileFlowRouter>,
) : BaseStateViewModel<BalanceViewState>() {

    private val router by routerHolder

    init {
        getData()
    }

    fun getData() {
        getBalanceAndHistory()
    }

    fun back() {
        router.back()
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

    fun onSorterChange(sorter: Sorter) {
        setState {
            copy(
                selectedSorter = sorter,
            )
        }
        filterAndSortItems()
    }

    fun onHideSnackbar() {
        setState {
            copy(
                showSuccessSnackbar = false,
                showErrorSnackbar = false,
            )
        }
    }

    fun onToTransferClick() {
        router.toTransfer()
    }

    fun onToBalanceClick() {
        coroutineScope.launch {
            setState {
                copy(
                    isLoading = true,
                )
            }

            val isTakeAwardSuccess = takeAwardUseCase() ?: return@launch

            setState {
                copy(
                    isLoading = false,
                    showSuccessSnackbar = isTakeAwardSuccess,
                    showErrorSnackbar = !isTakeAwardSuccess,
                )
            }
        }

        getData()
    }

    private fun filterAndSortItems() {
        setState {
            copy(
                filteredHistoryEvents = with(query.trim()) {
                    historyEvents.filter { historyEvent ->
                        if (query.isBlank()) {
                            true
                        } else {
                            historyEvent.description.contains(other = query, ignoreCase = true)
                        }
                    }
                        .sortedWith(
                            when (selectedSorter) {
                                Sorter.SumIncreasing -> compareBy { it.amount }
                                Sorter.SumDecreasing -> compareByDescending { it.amount }
                                Sorter.DateIncreasing -> compareBy<ThxHistoryItem> {
                                    val dateTimeFormatter = LocalDateTime.Format {
                                        @OptIn(FormatStringsInDatetimeFormats::class)
                                        byUnicodePattern("dd.MM.yyyy HH:mm")
                                    }
                                    LocalDateTime.parse(it.date, dateTimeFormatter)
                                }.thenBy {
                                    it.transactionId
                                }

                                Sorter.DateDecreasing -> compareByDescending<ThxHistoryItem> {
                                    val dateTimeFormatter = LocalDateTime.Format {
                                            @OptIn(FormatStringsInDatetimeFormats::class)
                                            byUnicodePattern("dd.MM.yyyy HH:mm")
                                        }
                                    LocalDateTime.parse(it.date, dateTimeFormatter)
                                }.thenByDescending {
                                    it.transactionId
                                }
                            }
                        )
                },
                isLoading = false,
            )
        }
    }

    @OptIn(ExperimentalTime::class, FormatStringsInDatetimeFormats::class)
    private fun getBalanceAndHistory() {
        coroutineScope.launch {
            setState {
                copy(
                    isLoading = true,
                )
            }
            val myInfo = getMyInfoUseCase() ?: return@launch
            val thxHistory = getThxHistoryUseCase()
            val profile = getProfileUseCase() ?: return@launch
            setState {
                copy(
                    balance = myInfo.balance,
                    giftBalance = myInfo.giftBalance,
                    weeklyAward = myInfo.weeklyAward,
                    endOfWeekDate = run {
                        val currentDate = Instant.fromEpochMilliseconds(epochMilliseconds = Clock.System.now().toEpochMilliseconds())
                            .toLocalDateTime(TimeZone.currentSystemDefault())
                            .date
                        val daysUntilEndOfWeek = DayOfWeek.SUNDAY.isoDayNumber - currentDate.dayOfWeek.isoDayNumber
                        val endOfWeekDate = currentDate.plus(value = daysUntilEndOfWeek, unit = DateTimeUnit.DAY)
                        endOfWeekDate.format(
                            format = LocalDate.Format {
                                byUnicodePattern("dd.MM.yyyy")
                            }
                        )
                    },
                    fullName = profile.fullName,
                    historyEvents = thxHistory,
                )
            }
            filterAndSortItems()
        }
    }

    override fun createInitialState() = initialState
}
