package com.ucb.usecases

import com.ucb.data.TransactionRepository
import com.ucb.domain.Transaction

class ListTransactions(
    private val transactionRepository: TransactionRepository
) {
    suspend fun invoke(): List<Transaction> {
        return transactionRepository.getAllTransactions()
            .sortedByDescending { it.fecha }
    }
}