package com.ucb.data.transactions

import com.ucb.domain.Transaction
import com.ucb.domain.Transaction.Income
import com.ucb.domain.Transaction.Expense

interface ITransactionLocalDataSource {
    suspend fun registerIncome(income: Income)
    suspend fun  registerExpense(expense: Expense)
    suspend fun  getAllTransactions(): List<Transaction>
    suspend fun  deleteTransaction(transactionId: String)
    suspend fun  getBalance(): Double
}