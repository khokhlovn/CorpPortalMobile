package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.common.ui.component.FullScreenImageViewer
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.model.EmployeeItemUIModel
import ru.kama_diesel.corp_portal_mobile.resources.*

@Composable
internal fun PhoneDirectoryItemDetailsContent(
    employeeItemUIModel: EmployeeItemUIModel,
    onCloseClick: () -> Unit,
) {
    var selectedImageIndex by remember { mutableIntStateOf(0) }
    var isImageOpened by remember { mutableStateOf(false) }

    if (employeeItemUIModel.imagePath != null && isImageOpened) {
        FullScreenImageViewer(
            selectedIndex = selectedImageIndex,
            imagePaths = listOf(employeeItemUIModel.imagePath),
            onClose = { isImageOpened = false },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.inverseSurface)
            .padding(all = 12.dp)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        val interactionSource = remember { MutableInteractionSource() }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(start = 4.dp, top = 8.dp)
                    .clip(shape = CircleShape)
                    .size(80.dp)
                    .background(Color.Black)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            selectedImageIndex = 0
                            isImageOpened = true
                        }
                    ),
                model = employeeItemUIModel.imagePath,
                placeholder = painterResource(Res.drawable.person_placeholder),
                error = painterResource(Res.drawable.person_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .heightIn(max = 80.dp),
                text = employeeItemUIModel.fullName,
                autoSize = TextAutoSize.StepBased(),
                fontWeight = FontWeight.Bold,
                style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                maxLines = 3,
                color = MaterialTheme.colorScheme.scrim,
            )
            IconButton(
                modifier = Modifier
                    .align(alignment = Alignment.Top)
                    .size(24.dp),
                onClick = onCloseClick,
            ) {
                Icon(
                    painter = painterResource(Res.drawable.close_24px),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = null,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (employeeItemUIModel.department.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        modifier = Modifier.widthIn(min = 100.dp),
                        text = stringResource(Res.string.department_details),
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.scrim,
                    )
                    Text(
                        text = employeeItemUIModel.department,
                        fontSize = 14.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
            }
            if (employeeItemUIModel.service.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        modifier = Modifier.widthIn(min = 100.dp),
                        text = stringResource(Res.string.service_details),
                        fontSize = 14.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        color = MaterialTheme.colorScheme.scrim,
                    )
                    Text(
                        text = employeeItemUIModel.service,
                        fontSize = 14.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
            }
            if (employeeItemUIModel.position.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        modifier = Modifier.widthIn(min = 100.dp),
                        text = stringResource(Res.string.position_details),
                        fontSize = 14.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        color = MaterialTheme.colorScheme.scrim,
                    )
                    Text(
                        text = employeeItemUIModel.position,
                        fontSize = 14.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
            }
            if (employeeItemUIModel.mail.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        modifier = Modifier.widthIn(min = 100.dp),
                        text = stringResource(Res.string.mail_details),
                        fontSize = 14.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        color = MaterialTheme.colorScheme.scrim,
                    )
                    Text(
                        text = employeeItemUIModel.mail,
                        fontSize = 14.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
            }
            if (!employeeItemUIModel.mobile.isNullOrEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        modifier = Modifier.widthIn(min = 100.dp),
                        text = stringResource(Res.string.mobile_details),
                        fontSize = 14.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        color = MaterialTheme.colorScheme.scrim,
                    )
                    Text(
                        text = "+7${employeeItemUIModel.mobile}",
                        fontSize = 14.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
            }
            if (!employeeItemUIModel.workPlace.isNullOrEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        modifier = Modifier.widthIn(min = 100.dp),
                        text = stringResource(Res.string.work_place),
                        fontSize = 14.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        color = MaterialTheme.colorScheme.scrim,
                    )
                    Text(
                        text = employeeItemUIModel.workPlace,
                        fontSize = 14.sp,
                        style = TextStyle.Default.copy(lineBreak = LineBreak.Paragraph),
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                    )
                }
            }
        }
    }
}
