package com.example.dailyexpenses.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyexpenses.model.Expense

fun getExpenseIcon(name: String): String {
    return when (name.lowercase()) {
        "кава" -> "☕"
        "проїзд" -> "🚌"
        "обід" -> "🍕"
        "продукти" -> "🛒"
        else -> "💸"
    }
}

@Composable
fun ExpenseCard(
    expense: Expense,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // ✅ ДОБАВЛЕНА ИКОНКА
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = Color(0xFFF3F2F7),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = getExpenseIcon(expense.name),
                    fontSize = 24.sp
                )
            }

            // ❗ ТВОЙ ОРИГИНАЛЬНЫЙ КОД НЕ УДАЛЯЛСЯ
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = Color(0xFFF3F2F7),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = expense.name,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Box(
                modifier = Modifier
                    .width(100.dp)
                    .background(
                        color = Color(0xFFF3F2F7),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = expense.amount.toInt().toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}