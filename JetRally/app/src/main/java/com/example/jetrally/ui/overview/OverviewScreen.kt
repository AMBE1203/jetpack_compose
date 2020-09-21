package com.example.jetrally.ui.overview

import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.jetrally.R
import com.example.jetrally.RallyScreen
import com.example.jetrally.data.UserData
import com.example.jetrally.ui.components.*
import com.example.jetrally.ui.theme.RallyTheme

private const val SHOW_ITEMS = 3
private val RallyDefaultPadding = 12.dp

@Preview(showBackground = true)
@Composable
fun previewOverviewScreen() {
    RallyTheme {
        OverviewBody()
    }
}

@Composable
fun OverviewBody(onScreenChange: (RallyScreen) -> Unit = {}) {
    ScrollableColumn(contentPadding = InnerPadding(16.dp)) {
        AlertCard()
        Spacer(modifier = Modifier.preferredHeight(RallyDefaultPadding))
        AccountCard(onScreenChange = onScreenChange)
        Spacer(modifier = Modifier.preferredHeight(RallyDefaultPadding))
        BillCard(onScreenChange = onScreenChange)


    }
}

@Composable
private fun AccountCard(onScreenChange: (RallyScreen) -> Unit) {
    val amount = UserData.accounts.map { account -> account.balance }.sum()
    OverviewScreenCard(
        title = stringResource(id = R.string.accounts),
        amount = amount,
        onClickSeeAll = {
            // todo change screen
            onScreenChange(RallyScreen.Accounts)
        },
        values = { it.balance },
        colors = { it.color },
        data = UserData.accounts
    ) { account ->
        AccountRow(
            name = account.name,
            number = account.number,
            amount = account.balance,
            color = account.color
        )

    }
}

@Composable
private fun BillCard(onScreenChange: (RallyScreen) -> Unit) {
    val amount = UserData.bills.map { bill -> bill.amount }.sum()
    OverviewScreenCard(
        title = stringResource(id = R.string.bills),
        amount = amount,
        onClickSeeAll = {
            // todo change screen
            onScreenChange(RallyScreen.Bills)

        },
        values = { it.amount },
        colors = { it.color },
        data = UserData.bills
    ) { bill ->
        BillRow(name = bill.name, due = bill.due, amount = bill.amount, color = bill.color)

    }
}

@Composable
private fun <T> OverviewScreenCard(
    title: String,
    amount: Float,
    onClickSeeAll: () -> Unit,
    values: (T) -> Float,
    colors: (T) -> Color,
    data: List<T>,
    row: @Composable (T) -> Unit
) {
    Card {
        Column {
            Column(Modifier.padding(RallyDefaultPadding)) {
                Text(text = title, style = MaterialTheme.typography.subtitle2)
                val amountText = "$" + formatAmount(amount = amount)
                Text(text = amountText, style = MaterialTheme.typography.h2)
            }

            OverViewDriver(data = data, values = values, colors = colors)
            Column(Modifier.padding(start = 16.dp, top = 4.dp, end = 8.dp)) {
                data.take(SHOW_ITEMS).forEach { row(it) }
                SeeAllButton(onClick = onClickSeeAll)

            }

        }
    }
}

@Composable
private fun <T> OverViewDriver(data: List<T>, values: (T) -> Float, colors: (T) -> Color) {
    Row(Modifier.fillMaxWidth()) {
        data.forEach { item: T ->
            Spacer(
                modifier = Modifier.weight(values(item)).preferredHeight(1.dp)
                    .background(color = colors(item))
            )
        }
    }
}

@Composable
private fun SeeAllButton(onClick: () -> Unit) {
    TextButton(onClick = onClick, modifier = Modifier.preferredHeight(44.dp).fillMaxWidth()) {
        Text(text = stringResource(id = R.string.see_all))
    }
}

@Composable
private fun AlertCard() {
    var showDialog by remember { mutableStateOf(false) }
    val alertMessage = "Heads up, you've used up 90% of your Shopping budget for this month."
    if (showDialog) {
        RallyAlertDialog(onDismiss = {
            showDialog = false
        }, bodyText = alertMessage, buttonText = "Dismiss".toUpperCase())
    }
    Card {
        Column {
            AlertHeader {
                showDialog = true
            }

            RallyDivider(
                modifier = Modifier.padding(
                    start = RallyDefaultPadding,
                    end = RallyDefaultPadding
                )
            )
            AlertItem(message = alertMessage)
        }
    }
}

@Composable
private fun AlertHeader(onClickSeeAll: () -> Unit) {
    Row(
        modifier = Modifier.padding(RallyDefaultPadding).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ProvideEmphasis(emphasis = EmphasisAmbient.current.high) {
            Text(
                text = "Alerts",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.gravity(Alignment.CenterVertically)
            )
        }

        TextButton(
            onClick = onClickSeeAll,
            contentPadding = InnerPadding(0.dp),
            modifier = Modifier.gravity(Alignment.CenterVertically)
        ) {
            Text(
                text = "SEE ALL",
                style = MaterialTheme.typography.button,
            )
        }

    }
}

@Composable
private fun AlertItem(message: String) {
    Row(
        modifier = Modifier.padding(RallyDefaultPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ProvideEmphasis(emphasis = EmphasisAmbient.current.high) {
            Text(
                text = message,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {}, modifier = Modifier.gravity(Alignment.Top)) {
                Icon(Icons.Filled.Sort)
            }
        }
    }
}