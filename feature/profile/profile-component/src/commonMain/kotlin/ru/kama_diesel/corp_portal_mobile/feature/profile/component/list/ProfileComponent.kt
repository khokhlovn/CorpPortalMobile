package ru.kama_diesel.corp_portal_mobile.feature.profile.component.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.common.component.registerAndGetSavedState
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ProfileItem
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.di.ProfileFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.list.di.ProfileDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.list.di.create
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.model.ProfileViewState

class ProfileComponent(
    componentContext: ComponentContext,
    profileFlowDIComponent: ProfileFlowDIComponent,
) : ComponentContext by componentContext {

    private val savedState: ProfileViewState = registerAndGetSavedState(
        key = PROFILE_SAVED_STATE,
        initialValue = ProfileViewState(
            profileItem = ProfileItem(
                username = "",
                fullName = "",
                position = "",
                department = "",
                mail = "",
                mobile = "",
                chief = "",
                personalMobile = "",
                personalMail = ""
            ),
            imagePath = null,
            balance = 0,
            cartItemsCount = 0,
            isFirstLoading = true,
            isLoading = true,
        ),
        deserialization = ProfileViewState.serializer(),
        serialization = ProfileViewState.serializer()
    ) {
        viewModel.currentState
    }

    private val diComponent = instanceKeeper.getOrCreate {
        ProfileDIComponent::class.create(profileFlowDIComponent, savedState)
    }

    val viewModel = diComponent.viewModel

    companion object Companion {
        private const val PROFILE_SAVED_STATE = "PROFILE_SAVED_STATE"
    }
}
