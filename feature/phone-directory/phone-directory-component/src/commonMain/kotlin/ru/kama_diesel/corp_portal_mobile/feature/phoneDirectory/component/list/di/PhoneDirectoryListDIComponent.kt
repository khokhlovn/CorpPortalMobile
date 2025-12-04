package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component.list.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component.di.PhoneDirectoryFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.domain.di.PhoneDirectoryListScope
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.PhoneDirectoryListViewModel
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.model.PhoneDirectoryListViewState

@PhoneDirectoryListScope
@Component
abstract class PhoneDirectoryListDIComponent(
    @Component val parent: PhoneDirectoryFlowDIComponent,
    private val initialState: PhoneDirectoryListViewState,
) : InstanceKeeper.Instance {

    abstract val viewModel: PhoneDirectoryListViewModel

    @Provides
    protected fun getInitialState(): PhoneDirectoryListViewState = initialState

    override fun onDestroy() {
        viewModel.onDestroy()
    }
}
