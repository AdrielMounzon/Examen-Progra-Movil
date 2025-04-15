package com.ucb.ucbtest.transactions

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.domain.Transaction
import com.ucb.usecases.DeleteTransaction
import com.ucb.usecases.ListTransactions
import com.ucb.usecases.RegisterExpense
import com.ucb.usecases.RegisterIncome
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val registerIncome: RegisterIncome,
    private val registerExpense: RegisterExpense,
    private val getAllTransactions: ListTransactions,
    private val deleteTransaction: DeleteTransaction,
    @ApplicationContext private val context: Context
) : ViewModel() {

    sealed class TransactionState {
        object Init : TransactionState()
        data class Success(val transactions: List<Transaction>) : TransactionState()
        data class Error(val message: String) : TransactionState()
    }

    private val _state = MutableStateFlow<TransactionState>(TransactionState.Init)
    val state: StateFlow<TransactionState> = _state

    fun registerIncomeTransaction(income: Transaction.Income) {
        viewModelScope.launch {
            try {
                registerIncome.invoke(income)
                loadTransactions()  // Refresh the list
            } catch (e: Exception) {
                _state.value = TransactionState.Error("Error registering income: ${e.message}")
            }
        }
    }

    fun registerExpenseTransaction(expense: Transaction.Expense) {
        viewModelScope.launch {
            try {
                val transactions = withContext(Dispatchers.IO) { getAllTransactions.invoke() }

                val totalIngresos = transactions
                    .filterIsInstance<Transaction.Income>()
                    .sumOf { it.precio }

                val totalEgresos = transactions
                    .filterIsInstance<Transaction.Expense>()
                    .sumOf { it.precio }

                val balance = totalIngresos - totalEgresos

                if (expense.precio > balance) {
                    com.ucb.ucbtest.service.Util.sendNotificatión(context)
                }

                registerExpense.invoke(expense)
                loadTransactions()

            } catch (e: Exception) {
                _state.value = TransactionState.Error("Error registering expense: ${e.message}")
            }
        }
    }

    fun deleteTransaction(transactionId: String) {
        viewModelScope.launch {
            try {
                deleteTransaction.invoke(transactionId)
                loadTransactions()  // Refresh the list
            } catch (e: Exception) {
                _state.value = TransactionState.Error("Error deleting transaction: ${e.message}")
            }
        }
    }

    fun loadTransactions() {
        viewModelScope.launch {
            try {
                val transactions = withContext(Dispatchers.IO) { getAllTransactions.invoke() }
                _state.value = TransactionState.Success(transactions)
            } catch (e: Exception) {
                _state.value = TransactionState.Error("Error loading transactions: ${e.message}")
            }
        }
    }

    fun fetchAllTransactions() {
        loadTransactions()
    }
}
