package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.model.EmployeeItemUIModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneDirectoryItemDetailsDialog(
    employeeItemUIModel: EmployeeItemUIModel,
    onCloseClick: () -> Unit,
) {
    Dialog(onDismissRequest = { onCloseClick() }) {
        Card(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.inverseSurface, shape = RoundedCornerShape(12.dp))
                .fillMaxWidth(),
        ) {
            PhoneDirectoryItemDetailsContent(
                employeeItemUIModel = employeeItemUIModel,
                onCloseClick = onCloseClick,
            )
        }
    }
}
