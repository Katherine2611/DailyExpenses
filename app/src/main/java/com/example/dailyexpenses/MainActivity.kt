package com.example.dailyexpenses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dailyexpenses.ui.theme.ExpenseScreen
import com.example.dailyexpenses.ui.theme.DailyExpensesTheme
import com.example.dailyexpenses.viewmodel.ExpenseViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyExpensesTheme {
                val expenseViewModel: ExpenseViewModel = viewModel()
                ExpenseScreen(viewModel = expenseViewModel)
            }
        }
    }
}