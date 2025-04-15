package com.ucb.usecases

import com.ucb.data.TransactionRepository
import com.ucb.domain.Transaction.Expense

class RegisterExpense(
    private val transactionRepository: TransactionRepository
) {
    suspend fun invoke(expense: Expense) {
        transactionRepository.registerExpense(expense)
    }
}