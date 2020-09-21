package com.example.jetrally.ui.components

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun <T> StatementBody(
    items: List<T>,
    colors: (T) -> Color,
    amounts: (T) -> Float,
    amountsTotal: Float,
    circleLabel: String,
    rows: @Composable (T) -> Unit
) {
    ScrollableColumn {
        Stack(Modifier.padding(16.dp)) {
            val accountsProportion = items.extractProportions { amounts(it) }
            val circleColors = items.map { colors(it) }
            AnimatedCircle(
                proportions = accountsProportion,
                colors = circleColors,
                modifier = Modifier.preferredHeight(300.dp).gravity(Alignment.Center).fillMaxWidth()
            )
            Column(modifier = Modifier.gravity(Alignment.Center)) {
                Text(
                    text = circleLabel,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.gravity(Alignment.CenterHorizontally)
                )

                Text(
                    text = formatAmount(amountsTotal),
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.gravity(Alignment.CenterHorizontally)
                )
            }
        }
        Spacer(modifier = Modifier.preferredHeight(10.dp))
        Card {
            Column(modifier = Modifier.padding(12.dp)) {
                items.forEach { item ->
                    rows(item)
                }
            }
        }
    }
}