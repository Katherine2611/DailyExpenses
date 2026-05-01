package com.example.dailyexpenses.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyexpenses.model.Expense
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ExpenseViewModel : ViewModel() {

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses

    val totalAmount: StateFlow<Double> =
        _expenses
            .map { list -> list.sumOf { it.amount } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)

    private val _showDialogFor = MutableStateFlow<Int?>(null)
    val showDialogFor: StateFlow<Int?> = _showDialogFor

    private var nextId = 0

    fun addExpense(name: String, amount: Double) {
        if (name.isBlank() || amount <= 0) return
        viewModelScope.launch {
            _expenses.value = _expenses.value + Expense(nextId++, name, amount)
        }
    }

    fun requestDelete(id: Int) {
        _showDialogFor.value = id
    }

    fun confirmDelete() {
        val id = _showDialogFor.value ?: return
        viewModelScope.launch {
            _expenses.value = _expenses.value.filterNot { it.id == id }
            _showDialogFor.value = null
        }
    }

    fun dismissDialog() {
        _showDialogFor.value = null
    }
}