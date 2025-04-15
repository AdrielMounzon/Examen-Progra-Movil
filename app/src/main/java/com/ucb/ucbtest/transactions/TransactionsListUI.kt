package com.ucb.ucbtest.transactions

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ucb.domain.Transaction
import com.ucb.ucbtest.R
import java.util.Date

@Composable
fun TransactionsListUI(onRegisterClick: () -> Unit, viewModel: TransactionViewModel = hiltViewModel()) {
    val transactionState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAllTransactions()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("All Transactions", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = transactionState) {
            is TransactionViewModel.TransactionState.Success -> {
                // Filtramos las transacciones de ingresos y gastos
                val incomes = state.transactions.filter { it is Transaction.Income }
                val expenses = state.transactions.filter { it is Transaction.Expense }

                // Creamos un Row con dos columnas: una para ingresos y otra para gastos
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Columna de Ingresos
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text("Incomes", style = MaterialTheme.typography.titleMedium)

                        incomes.forEach { transaction ->
                            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                Text("Name: ${transaction.nombre}")
                                Text("Price: ${transaction.precio}")
                                Text("Description: ${transaction.descripcion}")
                                Text("Date: ${transaction.fecha}")
                                Button(onClick = {
                                    viewModel.deleteTransaction(transaction.id.toString())
                                }) {
                                    Text("Delete")
                                }
                            }
                        }
                    }

                    // Columna de Gastos
                    Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                        Text("Expenses", style = MaterialTheme.typography.titleMedium)

                        expenses.forEach { transaction ->
                            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                Text("Name: ${transaction.nombre}")
                                Text("Price: ${transaction.precio}")
                                Text("Description: ${transaction.descripcion}")
                                Text("Date: ${transaction.fecha}")
                                Button(onClick = {
                                    viewModel.deleteTransaction(transaction.id.toString())
                                }) {
                                    Text("Delete")
                                }
                            }
                        }
                    }
                }
            }
            is TransactionViewModel.TransactionState.Error -> {
                Text("Error: ${state.message}")
            }
            is TransactionViewModel.TransactionState.Init -> {
                Text("No transactions available")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onRegisterClick) {
            Text("Registrar nueva transacción")
        }
    }
}
