package ru.kama_diesel.corp_portal_mobile

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.ComponentContext
import platform.Foundation.NSUserDefaults
import platform.UIKit.UIViewController
import ru.kama_diesel.corp_portal_mobile.di.AppComponent
import ru.kama_diesel.corp_portal_mobile.di.IOsPlatformComponent
import ru.kama_diesel.corp_portal_mobile.di.PlatformComponent
import ru.kama_diesel.corp_portal_mobile.feature.root.component.RootFlowComponent

expect fun IOsPlatformComponent.Companion.createKmp(userDefaults: NSUserDefaults): IOsPlatformComponent

private val platformComponent: PlatformComponent = IOsPlatformComponent.createKmp(NSUserDefaults.standardUserDefaults)

expect fun AppComponent.Companion.createKmp(platformComponent: PlatformComponent): AppComponent

private val appComponent: AppComponent = AppComponent.createKmp(platformComponent)

fun MainViewController(context: ComponentContext): UIViewController {
    val root = RootFlowComponent(context, appComponent.rootComponentDependencies)

    return ComposeUIViewController {
        ComposeApp(root)
    }
}
