package com.example.jetrally.ui.account

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.jetrally.R
import com.example.jetrally.data.Account
import com.example.jetrally.ui.components.AccountRow
import com.example.jetrally.ui.components.StatementBody

@Composable
fun AccountsBody(accounts: List<Account>) {
    StatementBody(
        items = accounts,
        colors = { account -> account.color },
        amounts = { account -> account.balance },
        amountsTotal = accounts.map { account -> account.balance }.sum(),
        circleLabel = stringResource(id = R.string.total),
        rows = { account ->
            AccountRow(
                name = account.name,
                number = account.number,
                amount = account.balance,
                color = account.color
            )
        }
    )
}