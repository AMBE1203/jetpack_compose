package com.example.jetrally

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.VectorAsset
import com.example.jetrally.ui.account.AccountsBody
import com.example.jetrally.ui.bill.BillsBody
import com.example.jetrally.ui.overview.OverviewBody
import  com.example.jetrally.data.UserData

enum class RallyScreen(
    val icon: VectorAsset,
    private val body: @Composable ((RallyScreen) -> Unit) -> Unit
) {

    Overview(
        icon = Icons.Filled.PieChart,
        body = { onScreenChange -> OverviewBody(onScreenChange = onScreenChange) }),

    Accounts(icon = Icons.Filled.AttachMoney, body = {
        AccountsBody(
            accounts = UserData.accounts
        )
    }),
    Bills(icon = Icons.Filled.MoneyOff, body = { BillsBody(bills = UserData.bills) });

    @Composable
    fun content(onScreenChange: (RallyScreen) -> Unit) {
        body(onScreenChange)
    }

}