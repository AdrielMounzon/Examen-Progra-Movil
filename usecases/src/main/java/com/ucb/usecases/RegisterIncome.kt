package com.ucb.usecases

import com.ucb.data.TransactionRepository
import com.ucb.domain.Transaction.Income

class RegisterIncome(
    private val transactionRepository: TransactionRepository
) {
    suspend fun invoke(income: Income){
        return transactionRepository.registerIncome(income)
    }
}