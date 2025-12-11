package ru.kama_diesel.corp_portal_mobile.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun PagerIndicator(pageCount: Int, currentPageIndex: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement
            .spacedBy(
                space = 2.dp,
                alignment = Alignment.CenterHorizontally
            ),
    ) {
        repeat(pageCount) { iteration ->
            val color =
                if (currentPageIndex == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
            Box(
                modifier = modifier
                    .clip(CircleShape)
                    .background(color)
                    .size(6.dp)
            )
        }
    }
}
