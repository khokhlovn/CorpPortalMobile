package ru.kama_diesel.corp_portal_mobile.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingDialog() {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    ) {
        Card(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.inverseSurface,
                shape = RoundedCornerShape(12.dp)
            )
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(120.dp)
                    .background(color = MaterialTheme.colorScheme.inverseSurface)
                    .padding(all = 12.dp),
            )
        }
    }
}
