package ru.kama_diesel.corp_portal_mobile.feature.top.ui.screen

import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.feature.top.domain.di.TopScope
import ru.kama_diesel.corp_portal_mobile.feature.top.domain.usecase.GetTopWorkersUseCase
import ru.kama_diesel.corp_portal_mobile.feature.top.ui.screen.model.TopViewState

@TopScope
@Inject
class TopViewModel(
    private val getTopWorkersUseCase: GetTopWorkersUseCase,
    private val initialState: TopViewState,
) : BaseStateViewModel<TopViewState>() {

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
            val topWorkers = getTopWorkersUseCase()
            setState {
                copy(
                    topWorkers = topWorkers,
                    isLoading = false,
                )
            }
        }
    }

    override fun createInitialState() = initialState
}
