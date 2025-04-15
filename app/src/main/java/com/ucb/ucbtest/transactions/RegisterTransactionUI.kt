package com.ucb.ucbtest.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ucb.domain.Transaction
import java.util.Date

@Composable
fun RegisterTransactionUI( onBackToList: () -> Unit, viewModel: TransactionViewModel = hiltViewModel()) {
    var incomeName by remember { mutableStateOf("") }
    var incomePrice by remember { mutableStateOf("") }
    var incomeDescription by remember { mutableStateOf("") }
    var expenseName by remember { mutableStateOf("") }
    var expensePrice by remember { mutableStateOf("") }
    var expenseDescription by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Register Income or Expense", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Income")
        OutlinedTextField(value = incomeName, onValueChange = { incomeName = it }, label = { Text("Name") })
        OutlinedTextField(value = incomePrice, onValueChange = { incomePrice = it }, label = { Text("Price") })
        OutlinedTextField(value = incomeDescription, onValueChange = { incomeDescription = it }, label = { Text("Description") })
        Button(onClick = {
            viewModel.registerIncomeTransaction(Transaction.Income(0, incomeName, incomePrice.toDouble(), incomeDescription, Date()))
        }) {
            Text("Register Income")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Expense")
        OutlinedTextField(value = expenseName, onValueChange = { expenseName = it }, label = { Text("Name") })
        OutlinedTextField(value = expensePrice, onValueChange = { expensePrice = it }, label = { Text("Price") })
        OutlinedTextField(value = expenseDescription, onValueChange = { expenseDescription = it }, label = { Text("Description") })
        Button(onClick = {
            viewModel.registerExpenseTransaction(Transaction.Expense(0, expenseName, expensePrice.toDouble(), expenseDescription, Date()))
        }) {
            Text("Register Expense")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onBackToList) {
            Text("Volver a la lista")
        }
    }
}
