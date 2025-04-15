package com.ucb.framework.transactions

import android.content.Context
import com.ucb.data.transactions.ITransactionLocalDataSource
import com.ucb.domain.Transaction
import com.ucb.domain.Transaction.Expense
import com.ucb.domain.Transaction.Income
import com.ucb.framework.persistence.TransactionRoomDatabase
import com.ucb.framework.persistence.ITransactionDAO
import com.ucb.framework.persistence.TransactionEntity
import com.ucb.framework.mappers.toEntity
import com.ucb.framework.mappers.toModel

class TransactionLocalDataSource(val context: Context) : ITransactionLocalDataSource {

    private val transactionDAO: ITransactionDAO = TransactionRoomDatabase.getDatabase(context).transactionDAO()

    override suspend fun registerIncome(income: Income) {
        transactionDAO.saveIncome(income.toEntity())
    }

    override suspend fun registerExpense(expense: Expense) {
        transactionDAO.saveExpense(expense.toEntity())
    }

    override suspend fun getAllTransactions(): List<Transaction> {
        return transactionDAO.getAllTransactions().map { it.toModel() }
    }

    override suspend fun deleteTransaction(transactionId: String) {
        transactionDAO.deleteTransactionById(transactionId)
    }

    override suspend fun getBalance(): Double {
        return transactionDAO.getBalance()
    }
}
