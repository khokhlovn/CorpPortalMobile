package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.TagItemUIModel

@Composable
fun FiltersContent(
    tagItems: List<TagItemUIModel>,
    onCheckedChange: (String, Boolean) -> Unit,
    expanded: Boolean,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.inverseSurface)
            .animateContentSize()
            .then(
                if (expanded) {
                    Modifier.fillMaxWidth()
                } else {
                    Modifier.width(0.dp)
                }
            )
            .fillMaxHeight()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(end = 12.dp, top = 12.dp, bottom = 12.dp),
        ) {
            items(items = tagItems) { tagItem ->
                TagItemContent(
                    item = tagItem,
                    onCheckedChange = onCheckedChange,
                )
            }
        }
    }
}

@Composable
private fun TagItemContent(
    item: TagItemUIModel,
    onCheckedChange: (String, Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable { onCheckedChange(item.tagItem.id, !item.isChecked) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val backgroundColor = item.tagItem.backgroundColor?.toColor() ?: MaterialTheme.colorScheme.primaryContainer
        val textColor = item.tagItem.textColor?.toColor() ?: MaterialTheme.colorScheme.onPrimaryContainer

        Checkbox(
            checked = item.isChecked,
            colors = CheckboxDefaults.colors().copy(
                checkedBoxColor = backgroundColor,
                checkedBorderColor = backgroundColor,
                checkedCheckmarkColor = textColor,
            ),
            onCheckedChange = { isChecked ->
                onCheckedChange(item.tagItem.id, isChecked)
            },
        )

        Text(
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp),
            text = item.tagItem.name,
            color = textColor,
            fontSize = 12.sp,
            maxLines = 1,
        )
    }
}

private fun String.toColor(): Color {
    val clean = this.removePrefix("#").uppercase()

    return when (clean.length) {
        3 -> {
            // RGB -> RRGGBB
            val r = clean[0].digitToInt(16) * 17
            val g = clean[1].digitToInt(16) * 17
            val b = clean[2].digitToInt(16) * 17
            Color(red = r, green = g, blue = b, alpha = 255)
        }

        4 -> {
            // ARGB
            val a = clean[0].digitToInt(radix = 16) * 17
            val r = clean[1].digitToInt(16) * 17
            val g = clean[2].digitToInt(16) * 17
            val b = clean[3].digitToInt(16) * 17
            Color(alpha = a, red = r, green = g, blue = b)
        }

        6 -> {
            // RRGGBB
            val r = clean.substring(0, 2).toInt(16)
            val g = clean.substring(2, 4).toInt(16)
            val b = clean.substring(4, 6).toInt(16)
            Color(red = r, green = g, blue = b, alpha = 255)
        }

        8 -> {
            // AARRGGBB
            val a = clean.substring(0, 2).toInt(16)
            val r = clean.substring(2, 4).toInt(16)
            val g = clean.substring(4, 6).toInt(16)
            val b = clean.substring(6, 8).toInt(16)
            Color(alpha = a, red = r, green = g, blue = b)
        }

        else -> throw IllegalArgumentException("Invalid Hex color: $this")
    }
}
