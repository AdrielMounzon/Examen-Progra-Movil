package com.ucb.framework.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ITransactionDAO {

    @Insert
    suspend fun saveIncome(incomeEntity: TransactionEntity)

    @Insert
    suspend fun saveExpense(expenseEntity: TransactionEntity)

    @Query("SELECT * FROM transactions ORDER BY fecha DESC")
    suspend fun getAllTransactions(): List<TransactionEntity>

    @Query("DELETE FROM transactions WHERE id = :transactionId")
    suspend fun deleteTransactionById(transactionId: String)

    @Query("SELECT (SELECT SUM(precio) FROM transactions WHERE tipo = 'Income') - (SELECT SUM(precio) FROM transactions WHERE tipo = 'Expense') AS balance")
    suspend fun getBalance(): Double
}
