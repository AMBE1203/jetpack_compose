package com.example.jetrally.ui.components

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.EmphasisAmbient
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.jetrally.R
import com.example.jetrally.ui.theme.JetRallyTheme
import com.example.jetrally.ui.theme.RallyTheme
import java.text.DecimalFormat

@Preview(showBackground = true)
@Composable
fun previewDemo() {
    RallyTheme {
        AccountRow(
            name = "aaaaaaaaa", number = 1203, amount = 4900f, color = Color.Blue
        )
    }
}

@Composable
fun AccountRow(name: String, number: Int, amount: Float, color: Color) {
    BaseRow(
        color = color,
        title = name,
        subTitle = stringResource(id = R.string.account_redacted) + AccountDecimalFormat.format(
            number
        ),
        amount = amount,
        negative = false
    )
}

@Composable
fun BillRow(name: String, due: String, amount: Float, color: Color) {
    BaseRow(color = color, title = name, subTitle = "Due $due", amount = amount, negative = true)
}

@Composable
private fun BaseRow(
    color: Color,
    title: String,
    subTitle: String,
    amount: Float,
    negative: Boolean
) {
    Row(verticalGravity = Alignment.CenterVertically
    ) {
        val typography = MaterialTheme.typography
        AccountIndicator(color = color, modifier = Modifier)
        Spacer(modifier = Modifier.preferredWidth(12.dp))
        Column(Modifier) {
            ProvideEmphasis(emphasis = EmphasisAmbient.current.high) {
                Text(text = title, style = typography.body1)
            }

            ProvideEmphasis(emphasis = EmphasisAmbient.current.medium) {
                Text(text = subTitle, style = typography.subtitle1)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = if (negative) "-$ " else "$ ",
                style = typography.h6,
                modifier = Modifier.gravity(Alignment.CenterVertically)
            )

            Text(
                text = formatAmount(amount),
                style = typography.h6,
                modifier = Modifier.gravity(Alignment.CenterVertically)
            )
        }
        Spacer(modifier = Modifier.preferredWidth(16.dp))

        ProvideEmphasis(emphasis = EmphasisAmbient.current.medium) {
            Icon(
                asset = Icons.Filled.ChevronRight,
                modifier = Modifier.padding(end = 12.dp).preferredSize(24.dp)
            )
        }


    }

    RallyDivider()
}


@Composable
private fun AccountIndicator(color: Color, modifier: Modifier = Modifier) {
    Spacer(modifier.preferredSize(4.dp, 36.dp).background(color = color))
}

@Composable
fun RallyDivider(modifier: Modifier = Modifier) {
    Divider(color = MaterialTheme.colors.background, thickness = 1.dp, modifier = modifier)
}

private val AccountDecimalFormat = DecimalFormat("####")
private val AmountDecimalFormat = DecimalFormat("#,###.##")

fun formatAmount(amount: Float): String = AmountDecimalFormat.format(amount)

fun <E> List<E>.extractProportions(selector: (E) -> Float): List<Float> {
    val total = this.sumByDouble { selector(it).toDouble() }
    return this.map { (selector(it) / total).toFloat() }
}
