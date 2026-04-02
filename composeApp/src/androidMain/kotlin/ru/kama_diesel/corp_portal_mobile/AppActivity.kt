package ru.kama_diesel.corp_portal_mobile

import AutoUpdaterManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.arkivanov.decompose.defaultComponentContext
import com.example.autoupdater.UpdateFeatures
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kama_diesel.corp_portal_mobile.common.ui.typography.AppFont
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
            var update: UpdateFeatures? by remember {
                mutableStateOf(null)
            }
            var showProgressDialog by remember {
                mutableStateOf(false)
            }
            var progress by remember {
                mutableFloatStateOf(0f)
            }
            val autoUpdaterManager = AutoUpdaterManager(LocalContext.current)
            val coroutineScope = rememberCoroutineScope()
            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        update = autoUpdaterManager.checkForUpdate(
                            JSONfileURL = "https://github.com/khokhlovn/CorpPortalMobile/releases/download/latest/changelog.json"
                        )
                    }
                }
            }
            if (update == null) {
                ComposeApp(rootFlowComponent = rootFlowComponent)
            } else {
                if (showProgressDialog) {
                    Dialog(
                        onDismissRequest = {},
                        properties = DialogProperties(
                            dismissOnBackPress = false,
                            dismissOnClickOutside = false,
                        )
                    ) {
                        Card(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFFFFFFF),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = Color(0xFFFFFFFF),
                                    )
                                    .padding(vertical = 24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Загрузка",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = AppFont.Geologica,
                                )
                                Spacer(modifier = Modifier.height(32.dp))
                                LinearProgressIndicator(
                                    progress = { progress },
                                    color = Color(0xFF003055),
                                    gapSize = 0.dp,
                                    trackColor = Color(0xFF003055).copy(alpha = 0.5f),
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 40.dp),
                                    text = "${progress.toInt()}%",
                                    textAlign = TextAlign.End,
                                    fontSize = 14.sp,
                                    fontFamily = AppFont.Geologica,
                                )
                            }
                        }
                    }
                } else {
                    AlertDialog(
                        properties = DialogProperties(
                            dismissOnBackPress = false,
                            dismissOnClickOutside = false,
                        ),
                        icon = null,
                        shape = RoundedCornerShape(12.dp),
                        containerColor = Color(0xFFFFFFFF),
                        title = {
                            Text(text = "Доступно обновление", fontFamily = AppFont.Geologica)
                        },
                        text = {
                            Text(text = update!!.changelog, fontFamily = AppFont.Geologica)
                        },
                        onDismissRequest = {},
                        confirmButton = {
                            TextButton(
                                shape = RoundedCornerShape(12.dp),
                                onClick = {
                                    coroutineScope.launch {
                                        withContext(Dispatchers.IO) {
                                            showProgressDialog = true
                                            autoUpdaterManager.downloadapk(
                                                this@AppActivity,
                                                update!!.apk_url,
                                                update!!.latestversion,
                                            ) {
                                                progress = it.toFloat()
                                            }
                                        }
                                    }
                                },
                            ) {
                                Text(text = "Загрузить", color = Color(0xFF003055), fontFamily = AppFont.Geologica)
                            }
                        },
                        dismissButton = null,
                    )
                }
            }
        }
    }
}
