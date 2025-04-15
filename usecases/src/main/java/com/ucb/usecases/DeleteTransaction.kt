package com.ucb.usecases

import com.ucb.data.TransactionRepository

class DeleteTransaction(
    private val transactionRepository: TransactionRepository
) {
    suspend fun invoke(transactionId: String) {
        transactionRepository.deleteTransaction(transactionId)
    }
}