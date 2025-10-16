package ru.kama_diesel.corp_portal_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import ru.kama_diesel.corp_portal_mobile.feature.root.component.RootFlowComponent

class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val rootFlowComponent = RootFlowComponent(
            componentContext = defaultComponentContext(),
            dependencies = (application as CorpPortalMobileApplication).appComponent().rootComponentDependencies
        )

        setContent {
            ComposeApp(rootFlowComponent = rootFlowComponent)
        }
    }
}
