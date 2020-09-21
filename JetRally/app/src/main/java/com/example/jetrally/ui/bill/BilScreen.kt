package com.example.jetrally.ui.bill

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.jetrally.R
import com.example.jetrally.data.Bill
import com.example.jetrally.ui.components.BillRow
import com.example.jetrally.ui.components.StatementBody

@Composable
fun BillsBody(bills: List<Bill>) {
    StatementBody(
        items = bills,
        colors = { bill -> bill.color },
        amounts = { bill -> bill.amount },
        amountsTotal = bills.map { bill ->
            bill.amount
        }.sum(),
        circleLabel = stringResource(id = R.string.due),
        rows = { bill ->
            BillRow(
                name = bill.name,
                due = bill.due,
                amount = bill.amount,
                color = bill.color
            )
        }
    )
}