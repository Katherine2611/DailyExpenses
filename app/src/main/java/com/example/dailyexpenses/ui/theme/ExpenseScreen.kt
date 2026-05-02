package com.example.dailyexpenses.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dailyexpenses.viewmodel.ExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(viewModel: ExpenseViewModel = viewModel()) {

    val expenses by viewModel.expenses.collectAsState()
    val showDialogFor by viewModel.showDialogFor.collectAsState()

    var name by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }

    val total by viewModel.totalAmount.collectAsState()

    val isAddEnabled =
        name.isNotBlank() && amountText.toDoubleOrNull()?.let { it > 0 } == true

    Scaffold(
        containerColor = Color(0xFFF8F8F8)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountBalanceWallet,
                            contentDescription = null,
                            tint = Color(0xFF8BC34A)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Витрати за день",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row {
                        Text(
                            text = "Разом: ",
                            fontSize = 22.sp,
                            color = Color.Gray
                        )

                        Text(
                            text = "₴ ${total.toInt()}",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Назва витрати") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it },
                label = { Text("Сума") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    val amount = amountText.toDoubleOrNull() ?: 0.0
                    viewModel.addExpense(name, amount)
                    name = ""
                    amountText = ""
                },
                enabled = isAddEnabled,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Додати")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(expenses) { expense ->
                    ExpenseCard(expense) {
                        viewModel.requestDelete(expense.id)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "довгий тап → видалити",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }

    if (showDialogFor != null) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDialog() },
            title = { Text("Видалити витрату?") },
            confirmButton = {
                TextButton(
                    onClick = { viewModel.confirmDelete() }
                ) {
                    Text("Так")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { viewModel.dismissDialog() }
                ) {
                    Text("Ні")
                }
            }
        )
    }
}
