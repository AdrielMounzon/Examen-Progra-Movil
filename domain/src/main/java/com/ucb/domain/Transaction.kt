package com.ucb.domain
import java.util.Date

sealed class Transaction {
    abstract val id: Int
    abstract val nombre: String
    abstract val precio: Double
    abstract val descripcion: String
    abstract val fecha: Date

    data class Income(
        override val id: Int, override val nombre: String, override val precio: Double, override val descripcion: String, override val fecha: Date
    ) : Transaction()

    data class Expense(
        override val id: Int, override val nombre: String, override val precio: Double, override val descripcion: String, override val fecha: Date
    ) : Transaction()
}
