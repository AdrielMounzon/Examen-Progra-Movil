package com.ucb.usecases

import com.ucb.data.TransactionRepository
import com.ucb.domain.Transaction

class GetBalance(
    private val transactionRepository: TransactionRepository
) {
    suspend fun invoke(): Double {
        return transactionRepository.getBalance()
    }
}
