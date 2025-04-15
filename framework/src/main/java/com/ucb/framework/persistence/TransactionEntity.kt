package com.ucb.framework.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipo: String,
    val nombre: String,
    val precio: Double,
    val descripcion: String,
    val fecha: Date
)
